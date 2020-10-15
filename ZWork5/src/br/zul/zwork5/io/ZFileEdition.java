package br.zul.zwork5.io;

import br.zul.zwork4.exception.ZIOException;
import java.io.OutputStream;

/**
 *
 * @author Luiz Henrique
 */
public interface ZFileEdition {
    
    public OutputStream getOutputStream();
    public void commit() throws ZIOException;
    public void rollback() throws ZIOException;
    
}
