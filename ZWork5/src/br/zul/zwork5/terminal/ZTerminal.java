package br.zul.zwork5.terminal;

import br.zul.zwork5.terminal.handler.ZWindowsTerminalHandler;
import br.zul.zwork5.exception.ZAlreadyStartedException;
import br.zul.zwork5.exception.ZCommandErrorException;
import br.zul.zwork5.exception.ZNoRunningException;
import br.zul.zwork5.exception.ZStartException;
import br.zul.zwork5.util.ZList;
import static br.zul.zwork5.util.ZOSEnum.WINDOWS;
import br.zul.zwork5.util.ZOSUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author luizh
 */
public class ZTerminal implements Closeable{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZTerminalOSHandler handler;
    private final ZList<ZTerminalListener> listenerList;
    private final Map<ZTerminalMsgType, Thread> readerMap;
    
    private Process process;
    private InputStream is;
    private InputStream es;
    private OutputStream os;
    
    boolean ignoreExceptions;
    private boolean readByLine;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZTerminal() {
        listenerList = new ZList<>();
        handler = initHandler();
        readerMap = new HashMap<>();
        readByLine = true;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZTerminalOSHandler initHandler() {
        switch (ZOSUtils.getOS()) {
            case WINDOWS:
                return new ZWindowsTerminalHandler();
            default:
                throw new UnsupportedOperationException();
        }
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void open() throws ZAlreadyStartedException, ZStartException {
        try {
            requireNonRunning();
            ProcessBuilder builder = new ProcessBuilder(handler.getCommandList());
            builder.redirectInput();
            builder.redirectOutput();
            builder.redirectError();
            ignoreExceptions = false;
            process = builder.start();
            is = process.getInputStream();
            es = process.getErrorStream();
            os = process.getOutputStream();
            read(ZTerminalMsgType.OUT, is);
            read(ZTerminalMsgType.ERROR, es);
        } catch (IOException ex) {
            throw new ZStartException(ex);
        }
    }
    
    @Override
    public void close() throws IOException{
        try {
            close(false);
        } catch (InterruptedException ex) {
            throw new IOException(ex);
        }
    }

    public void close(boolean wait) throws InterruptedException {
        if (isRunning()){
            process.destroyForcibly();
            if (isRunning()&&wait){
                process.waitFor();
            }
        }
    }

    public void send(String command) throws ZNoRunningException, ZCommandErrorException {
        try {
            requireRunning();
            os.write(command.getBytes());
            os.flush();
        } catch (IOException ex) {
            throw new ZCommandErrorException(ex);
        }
    }
    
    public void sendLine(String command) throws ZNoRunningException, ZCommandErrorException{
        send(command+"\r\n");
    }
    
    public boolean isRunning(){
        return process!=null&&process.isAlive();
    }
    
    public void waitToFinish() throws InterruptedException{
        while (isRunning()){
            Thread.sleep(100);
        }
    }

    //==========================================================================
    //MÉTODOS DE VALIDAÇÃO
    //==========================================================================
    private void requireNonRunning() throws ZAlreadyStartedException{
        if (isRunning()){
            throw new ZAlreadyStartedException();
        }
    }
    
    private void requireRunning() throws ZNoRunningException{
        if (!isRunning()){
            throw new ZNoRunningException();
        }
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    void finish() throws InterruptedException{
        ignoreExceptions = true;
        closeIs();
        closeEs();
        closeOs();
        close(true);
        process = null;
        readerMap.clear();
    }

    private void closeIs() {
        try {
            is.close();
        } catch (IOException|NullPointerException ex) {}
        is = null;
    }

    private void closeEs() {
        try {
            es.close();
        } catch (IOException|NullPointerException ex) {}
        es = null;
    }

    private void closeOs() {
        try {
            os.close();
        } catch (IOException|NullPointerException ex) {}
        os = null;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZList<ZTerminalListener> getListenerList() {
        return listenerList;
    }

    private void read(ZTerminalMsgType type, InputStream is) {
        Thread thread;
        if (readByLine){
            thread = new Thread(new ZTerminalReaderByLine(this, type, is));
        } else {
            throw new UnsupportedOperationException();
        }
        readerMap.put(type, thread);
        thread.start();
    }

    public boolean isReadByLine() {
        return readByLine;
    }
    public void setReadByLine(boolean readByLine) {
        this.readByLine = readByLine;
    }
    
}
