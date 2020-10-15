package br.zul.zwork5.exception;

import br.zul.zwork5.reflection.ZClass;

/**
 *
 * @author luiz.silva
 */
public class ZQueryFilterExpressionIsInvalidException extends ZRuntimeException {

    //==========================================================================
    //VARÁVEIS
    //==========================================================================
    private final ZClass cls;
    private final String expression;
    
    //==========================================================================
    //VARÁVEIS
    //==========================================================================
    public ZQueryFilterExpressionIsInvalidException(ZClass cls, String expression) {
        super("Não foi possível achar os campos referentes a classe ''{0}'': {1}", cls.getName(), expression);
        this.cls = cls;
        this.expression = expression;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZClass getCls() {
        return cls;
    }

    public String getExpression() {
        return expression;
    }
    
}
