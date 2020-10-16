package br.zul.zwork5.io;

import br.zul.zwork5.exception.ZAlreadyStartedException;
import br.zul.zwork5.exception.ZCommandErrorException;
import br.zul.zwork5.exception.ZFileDeleteerException;
import br.zul.zwork5.exception.ZNoRunningException;
import br.zul.zwork5.exception.ZStartException;
import br.zul.zwork5.terminal.ZTerminal;
import java.io.Closeable;
import java.io.File;

/**
 *
 * @author luizh
 */
public class ZFileDeleteer implements Closeable {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZTerminal terminal;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZFileDeleteer() throws ZAlreadyStartedException, ZStartException{
        this.terminal = new ZTerminal();
        this.terminal.open();
    }
    
    //==========================================================================
    //METODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    protected void finalize() throws Throwable{
        close();
        super.finalize();
    }   
    
    @Override
    public void close(){
        try {
            terminal.close(false);
        } catch (InterruptedException ex) {}
    }
    
    //==========================================================================
    //METODOS PÚBLICOS
    //==========================================================================
    public void delete(File file) throws ZFileDeleteerException {
        try {
            if (file.isDirectory()){
                delDir(file);
            } else {
                delFile(file);
            }
        } catch (ZNoRunningException|ZCommandErrorException ex){
            throw new ZFileDeleteerException(ex);
        }
    }

    //==========================================================================
    //METODOS PRIVADOS
    //==========================================================================
    private void delDir(File file) throws ZNoRunningException, ZCommandErrorException {
        terminal.sendLine("del \""+file.getAbsolutePath()+"\"");
    }

    private void delFile(File file) throws ZNoRunningException, ZCommandErrorException {
        terminal.sendLine("rmdir \""+file.getAbsolutePath()+"\"");
    }
    
}
