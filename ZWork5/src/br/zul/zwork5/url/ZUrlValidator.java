package br.zul.zwork5.url;

import br.zul.zwork5.exception.ZRequiredContentException;
import br.zul.zwork5.exception.ZUrlIsInvalidException;
import br.zul.zwork5.util.ZValidations;

/**
 *
 * @author luizh
 */
class ZUrlValidator {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZUrl url;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUrlValidator(ZUrl url) {
        this.url = url;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void validate() throws ZUrlIsInvalidException {
        try {
            ZValidations.requireContent(url.getProtocol());
            ZValidations.requireContent(url.getHost());
        } catch(ZRequiredContentException e){
            throw new ZUrlIsInvalidException(e);
        }
    }
    
}
