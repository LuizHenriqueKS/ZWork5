package br.zul.zwork5.io.txt;

import br.zul.zwork5.exception.ZFileAlreadyOpenException;
import br.zul.zwork5.exception.ZClosedException;
import br.zul.zwork5.io.ZFile;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 * @author Luiz Henrique
 */
public class ZTxtFileFastWriter {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final ZTxtFileWriter writer;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZTxtFileFastWriter(ZFile file, boolean append, Charset charset) throws IOException, ZFileAlreadyOpenException {
        this.writer = new ZTxtFileWriter(file, append, charset);
    }

    public ZTxtFileFastWriter(ZFile file, boolean append) throws IOException, ZFileAlreadyOpenException {
        this.writer = new ZTxtFileWriter(file, append);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void writeAll(String str) throws IOException, ZClosedException {
        writer.write(str);
        writer.close();
    }

}
