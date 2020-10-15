package br.zul.zwork5.query;

import java.util.Objects;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionEquals implements ZQueryFilterCondition{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Object equalsTo;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilterConditionEquals(Object equalsTo) {
        this.equalsTo = equalsTo;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean apply(Object obj, Object value){
        return Objects.equals(value, equalsTo);
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getEqualsTo() {
        return equalsTo;
    }
    
}
