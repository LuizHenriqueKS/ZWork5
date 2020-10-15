package br.zul.zwork5.exception;

import br.zul.zwork5.reflection.ZMethod;

/**
 *
 * @author luizh
 */
public class ZMethodIsNotStaticException extends ZException {

    private final ZMethod method;
    
    public ZMethodIsNotStaticException(ZMethod method) {
        super("O método ''{0}'' da classe ''{1}'' não é estático.", method.getName(), method.getObjClass().getName());
        this.method = method;
    }

    public ZMethod getMethod() {
        return method;
    }
    
}
