package br.zul.zwork5.io.txt;

import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.exception.ZStreamClosedException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 * @author Luiz Henrique
 */
public class ZTxtFileFastReader {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZTxtFileReader reader;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZTxtFileFastReader(ZFile file, Charset charset) throws FileNotFoundException, IOException {
        this.reader = new ZTxtFileReader(file, charset);
    }

    public ZTxtFileFastReader(ZFile file) throws FileNotFoundException, IOException {
        this.reader = new ZTxtFileReader(file);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String readAll() throws IOException, ZStreamClosedException{
        try {
            char buffer[] = new char[4096];
            StringBuilder builder = new StringBuilder();
            int len;
            while ((len = reader.read(buffer))>0){
                builder.append(buffer, 0, len);
            }
            return builder.toString();
        } catch (IOException ex) {
            throw new IOException(ex);
        } finally {
            reader.close();
        }
    }
    
}
