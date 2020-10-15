package br.zul.zwork5.io;

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
    public ZFileDeleteer(){
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
        terminal.close(false);
    }
    
    //==========================================================================
    //METODOS PÚBLICOS
    //==========================================================================
    public void delete(File file){
        if (file.isDirectory()){
            delDir(file);
        } else {
            delFile(file);
        }
    }

    //==========================================================================
    //METODOS PRIVADOS
    //==========================================================================
    private void delDir(File file) {
        terminal.sendLine("del \""+file.getAbsolutePath()+"\"");
    }

    private void delFile(File file) {
        terminal.sendLine("rmdir \""+file.getAbsolutePath()+"\"");
    }
    
}
