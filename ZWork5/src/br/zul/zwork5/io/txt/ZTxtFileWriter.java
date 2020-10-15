package br.zul.zwork5.io.txt;

import br.zul.zwork4.exception.ZFileAlreadyOpenException;
import br.zul.zwork4.exception.ZIOException;
import br.zul.zwork4.exception.ZStreamClosedException;
import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.io.ZFileEdition;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZTxtFileWriter {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final ZFile file;
    private ZFileEdition edition;
    private OutputStream os;
    private OutputStreamWriter osw;
    private BufferedWriter bw;
    
    private boolean open;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZTxtFileWriter(ZFile file, boolean append, Charset charset) throws ZIOException, ZFileAlreadyOpenException{
        this.file = file;
        this.openFile(append, charset);
    }

    public ZTxtFileWriter(ZFile file, boolean append) throws ZIOException, ZFileAlreadyOpenException{
        this.file = file;
        this.openFile(append, Charset.defaultCharset());
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
 /*   public void createNewFile(Charset charset) throws ZIOException, ZFileAlreadyOpenException{
        openFile(false, charset);
    }
    
    public void createNewFile() throws ZIOException, ZFileAlreadyOpenException{
        createNewFile(Charset.defaultCharset());
    }
    
    public void editFile(Charset charset) throws ZIOException, ZFileAlreadyOpenException{
        openFile(true, charset);
    }
    
    public void editFile() throws ZIOException, ZFileAlreadyOpenException{
        editFile(Charset.defaultCharset());
    }*/
    
    public void write(String str) throws ZIOException, ZStreamClosedException{
        requireNonClosed();
        try {
            bw.write(str);
        } catch (IOException ex) {
            throw new ZIOException(ex);
        }
    }
    
    public void writeAll(String str) throws ZIOException, ZStreamClosedException{
        write(str);
        close();
    }
    
    public void writeLine(String line) throws ZIOException, ZStreamClosedException{
        write(line);
        write("\r\n");
    }
    
    public void writeLines(String... lines) throws ZStreamClosedException{
        writeLines(Arrays.asList(lines));
    }
    
    public void writeLines(List<String> lineList) throws ZStreamClosedException, ZIOException{
        lineList.forEach(this::writeLine);
    }
    
    public void flush() throws ZIOException, ZStreamClosedException{
        try {
            requireNonClosed();
            bw.flush();
        } catch (IOException ex) {
            throw new ZIOException(ex);
        }
    }
    
    public void close(){
        try {
            bw.close();
        }catch(Exception e){}
        try {
            osw.close();
        }catch(Exception e){}
        try {
            os.close();
        }catch(Exception e){}
        try {
            edition.commit();
        }catch(Exception e){}
        open = false;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS DE APOIO
    //==========================================================================
    private void openFile(boolean append, Charset charset) throws ZIOException {
        requireNonOpen();
        try {
            edition = file.editFile(append);
            os = edition.getOutputStream();
            osw = new OutputStreamWriter(os, charset);
            bw = new BufferedWriter(osw);
            open = true;
        }catch(Exception e){
            throw new ZIOException(e);
        }
    }
    
    private void requireNonClosed() throws ZStreamClosedException{
        if (!open){
            throw new ZStreamClosedException(file.getPath());
        }
    }
    
    private void requireNonOpen() throws ZFileAlreadyOpenException{
        if (open){
            throw new ZFileAlreadyOpenException(file.getPath());
        }
    }
    
}
