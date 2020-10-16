package br.zul.zwork5.io.txt;

import br.zul.zwork5.exception.ZFileAlreadyOpenException;
import br.zul.zwork5.exception.ZClosedException;
import br.zul.zwork5.exception.ZUneditableFileException;
import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.io.ZFileEdition;
import java.io.BufferedWriter;
import java.io.Closeable;
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
public class ZTxtFileWriter implements Closeable {

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
    public ZTxtFileWriter(ZFile file, boolean append, Charset charset) throws IOException, ZFileAlreadyOpenException{
        this.file = file;
        this.openFile(append, charset);
    }

    public ZTxtFileWriter(ZFile file, boolean append) throws IOException, ZFileAlreadyOpenException{
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
    
    public void write(String str) throws IOException, ZClosedException{
        requireNonClosed();
        try {
            bw.write(str);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    
    public void writeLine(String line) throws IOException, ZClosedException{
        write(line);
        write("\r\n");
    }
    
    public void writeLines(String... lines) throws ZClosedException, IOException{
        writeLines(Arrays.asList(lines));
    }
    
    public void writeLines(List<String> lineList) throws ZClosedException, IOException{
        for (String line: lineList){
            this.writeLine(line);
        }
    }
    
    public void flush() throws IOException, ZClosedException{
        try {
            requireNonClosed();
            bw.flush();
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    
    @Override
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
    private void openFile(boolean append, Charset charset) throws IOException, ZFileAlreadyOpenException {
        requireNonOpen();
        try {
            edition = file.editFile(append);
            os = edition.getOutputStream();
            osw = new OutputStreamWriter(os, charset);
            bw = new BufferedWriter(osw);
            open = true;
        }catch(ZUneditableFileException e){
            throw new IOException(e);
        }
    }
    
    private void requireNonClosed() throws ZClosedException{
        if (!open){
            throw new ZClosedException(file.getPath());
        }
    }
    
    private void requireNonOpen() throws ZFileAlreadyOpenException{
        if (open){
            throw new ZFileAlreadyOpenException(file.getPath());
        }
    }
    
}
