
package br.zul.zwork5.exception;

import br.zul.zwork4.exception.*;

/**
 *
 * @author Luiz Henrique
 */
public class ZStreamClosedException extends ZException {

    public ZStreamClosedException() {
    
    }

    public ZStreamClosedException(String filePath) {
        super("O arquivo não está aberto: {0}", filePath);
    }
    
}
