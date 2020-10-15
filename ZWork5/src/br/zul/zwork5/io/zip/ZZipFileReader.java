package br.zul.zwork5.io.zip;

import br.zul.zwork5.io.ZFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Luiz Henrique
 */
public class ZZipFileReader {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZFile file;
    private final Charset charset;
    private InputStream is;
    private ZipInputStream zis;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZZipFileReader(ZFile file, Charset charset) {
        this.file = file;
        this.charset = charset;
        open();
    }

    public ZZipFileReader(ZFile file) {
        this.file = file;
        this.charset = Charset.defaultCharset();
        open();
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void open(){
        is = file.getInputStream();
        if (file.getExtension().equalsIgnoreCase("exe")){
            is = new ZWinZipInputStream(is);
        }
        zis = new ZipInputStream(is, charset);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZipEntry getNextEntry() throws IOException{
        try {
            return zis.getNextEntry();
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    
    public void closeEntry() throws IOException, ZipException{
        try {
            zis.closeEntry();
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
    
    public void close() throws IOException{
        try {
            zis.close();
        }catch(IOException e){
            throw new IOException(e);
        }
    }
    
}
