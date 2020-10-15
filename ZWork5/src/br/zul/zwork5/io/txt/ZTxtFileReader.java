package br.zul.zwork5.io.txt;

import br.zul.zwork4.exception.ZFileNotFoundException;
import br.zul.zwork4.exception.ZIOException;
import br.zul.zwork4.exception.ZStreamClosedException;
import br.zul.zwork5.io.ZFile;
import br.zul.zwork4.util.ZList;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 *
 * @author Luiz Henrique
 */
public class ZTxtFileReader {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZFile file;
    private final Charset charset;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;
    
    private boolean open;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZTxtFileReader(ZFile file, Charset charset) {
        this.file = file;
        this.charset = charset;
        openFile();
    }

    public ZTxtFileReader(ZFile file) {
        this(file, Charset.defaultCharset());
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void openFile() throws ZFileNotFoundException{
        is = file.getInputStream();
        isr = new InputStreamReader(is, charset);
        br = new BufferedReader(isr);
        open = true;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public boolean ready() throws ZIOException, ZStreamClosedException{
        requireNonClosed();
        try {
            return br.ready();
        } catch (IOException ex) {
            throw new ZIOException(ex);
        }
    }
    
    public String readAll() throws ZIOException, ZStreamClosedException{
        requireNonClosed();
        try {
            char buffer[] = new char[4096];
            StringBuilder builder = new StringBuilder();
            int len;
            while ((len = isr.read(buffer))>0){
                builder.append(buffer, 0, len);
            }
            return builder.toString();
        } catch (IOException ex) {
            throw new ZIOException(ex);
        }
    }
    
    public String readLine() throws ZIOException, ZStreamClosedException{
        requireNonClosed();
        try {
            return br.readLine();
        } catch (IOException ex) {
            throw new ZIOException(ex);
        }
    }
    
    public ZList<String> readLines() throws ZIOException, ZStreamClosedException{
        requireNonClosed();
        ZList<String> lineList = new ZList<>();
        String line;
        while ((line = readLine())!=null){
            lineList.add(line);
        }
        return lineList;
    }
    
    public String readLastLine() throws ZIOException, ZStreamClosedException {
        String line;
        String lastLine = null;
        while ((line=readLine())!=null){
            lastLine = line;
        }
        return lastLine;
    }
    
    public String readLastLine(int skip) throws ZIOException, IndexOutOfBoundsException, ZStreamClosedException{
        ZList<String> lineList = readLines();
        return lineList.last(skip);
    }
    
    public void close() throws ZIOException{
        try {
            br.close();
        }catch(IOException e){}
        try {
            isr.close();
        }catch(IOException e){}
        try {
            is.close();
        }catch(IOException e){}
        open = false;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void requireNonClosed(){
        if (!open){
            throw new ZStreamClosedException(file.getPath());
        }
    }
    
}
