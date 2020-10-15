package br.zul.zwork5.query;

import java.util.Objects;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionNotEquals implements ZQueryFilterCondition{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Object notEqualsTo;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilterConditionNotEquals(Object notEqualsTo) {
        this.notEqualsTo = notEqualsTo;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean apply(Object obj, Object value){
        return !Objects.equals(value, notEqualsTo);
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getNotEqualsTo() {
        return notEqualsTo;
    }
    
}
