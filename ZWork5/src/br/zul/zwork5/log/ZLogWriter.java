package br.zul.zwork5.log;

import java.io.IOException;

/**
 *
 * @author Luiz Henrique
 */
public interface ZLogWriter {
    
    public void print(ZLogMsg msg) throws IOException;
    
}
