
package br.zul.zwork5.query;

import java.util.Objects;

/**
 *
 * @author luizh
 */
public class ZQueryOrder {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Integer index;
    private final String expression;
    private final boolean ascending;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryOrder(Integer index, String expression, boolean ascending) {
        this.index = index;
        this.expression = expression;
        this.ascending = ascending;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.index);
        hash = 97 * hash + Objects.hashCode(this.expression);
        hash = 97 * hash + (this.ascending ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZQueryOrder other = (ZQueryOrder) obj;
        if (this.ascending != other.ascending) {
            return false;
        }
        if (!Objects.equals(this.expression, other.expression)) {
            return false;
        }
        if (!Objects.equals(this.index, other.index)) {
            return false;
        }
        return true;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Integer getIndex() {
        return index;
    }

    public String getExpression() {
        return expression;
    }

    public boolean isAscending() {
        return ascending;
    }
    
}
