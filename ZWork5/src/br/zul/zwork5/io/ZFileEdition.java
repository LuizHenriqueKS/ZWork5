package br.zul.zwork5.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Luiz Henrique
 */
public interface ZFileEdition {
    
    public OutputStream getOutputStream();
    public void commit() throws IOException;
    public void rollback() throws IOException;
    
}
