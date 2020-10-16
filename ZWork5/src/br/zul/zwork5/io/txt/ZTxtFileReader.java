package br.zul.zwork5.io.txt;

import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.exception.ZStreamClosedException;
import br.zul.zwork5.util.ZList;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 *
 * @author Luiz Henrique
 */
public class ZTxtFileReader implements Closeable {

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
    public ZTxtFileReader(ZFile file, Charset charset) throws FileNotFoundException, IOException {
        this.file = file;
        this.charset = charset;
        openFile();
    }

    public ZTxtFileReader(ZFile file) throws FileNotFoundException, IOException {
        this(file, Charset.defaultCharset());
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void openFile() throws FileNotFoundException, IOException{
        is = file.getInputStream();
        isr = new InputStreamReader(is, charset);
        br = new BufferedReader(isr);
        open = true;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public boolean ready() throws IOException, ZStreamClosedException{
        requireNonClosed();
        try {
            return br.ready();
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    
    public int read(char[] buffer, int offset, int length) throws IOException, ZStreamClosedException{
        requireNonClosed();
        return isr.read(buffer, offset, length);
    }
    
    public int read(char[] buffer) throws IOException, ZStreamClosedException{
        requireNonClosed();
        return isr.read(buffer);
    }
    
    public String readLine() throws IOException, ZStreamClosedException{
        requireNonClosed();
        try {
            return br.readLine();
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    
    public ZList<String> readLines() throws IOException, ZStreamClosedException{
        requireNonClosed();
        ZList<String> lineList = new ZList<>();
        String line;
        while ((line = readLine())!=null){
            lineList.add(line);
        }
        return lineList;
    }
    
    public String readLastLine() throws IOException, ZStreamClosedException {
        String line;
        String lastLine = null;
        while ((line=readLine())!=null){
            lastLine = line;
        }
        return lastLine;
    }
    
    public String readLastLine(int skip) throws IOException, IndexOutOfBoundsException, ZStreamClosedException{
        ZList<String> lineList = readLines();
        return lineList.last(skip);
    }
    
    public void close() throws IOException{
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
    private void requireNonClosed() throws ZStreamClosedException{
        if (!open){
            throw new ZStreamClosedException(file.getPath());
        }
    }
    
}
