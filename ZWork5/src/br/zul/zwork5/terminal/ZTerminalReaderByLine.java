package br.zul.zwork5.terminal;

import br.zul.zwork5.exception.ZRuntimeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author luizh
 */
class ZTerminalReaderByLine implements Runnable {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZTerminal terminal;
    private final ZTerminalMsgType type;
    private final InputStream is;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZTerminalReaderByLine(ZTerminal terminal, ZTerminalMsgType type, InputStream is) {
        this.terminal = terminal;
        this.type = type;
        this.is = is;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine())!=null){
                ZTerminalMsg msg = new ZTerminalMsg();
                msg.message = line+"\r\n";
                msg.type = type;
                sendMsg(msg);
            }
        } catch (IOException ex) {
            treatException(ex);
        } finally {
            tryFinish();
        }
    }

    private void sendMsg(ZTerminalMsg msg) {
        synchronized (terminal.getListenerList()){
            terminal.getListenerList().forEach(l->l.onListen(msg));
        }
    }

    private void treatException(IOException ex) {
        if (!terminal.ignoreExceptions){
            throw new ZRuntimeException(ex);
        }
    }

    private void tryFinish() {
        try {
            terminal.finish();
        } catch (InterruptedException ex) {
            throw new ZRuntimeException(ex);
        }
    }
    
}
