package br.zul.zwork5.exception;

import br.zul.zwork4.exception.*;
import br.zul.zwork5.reflection.ZMethod;

/**
 *
 * @author luizh
 */
public class ZMethodIsStaticException extends ZException {

    private final ZMethod method;
    
    public ZMethodIsStaticException(ZMethod method) {
        super("O método ''{0}'' da classe ''{1}'' é estático.", method.getName(), method.getObjClass().getName());
        this.method = method;
    }

    public ZMethod getMethod() {
        return method;
    }
    
}
