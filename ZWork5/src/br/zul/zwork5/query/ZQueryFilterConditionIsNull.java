
package br.zul.zwork5.query;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionIsNull implements ZQueryFilterCondition{
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private boolean isNullOrIsNotNull;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilterConditionIsNull() {
        this.isNullOrIsNotNull = true;
    }
    
    public ZQueryFilterConditionIsNull(boolean isNullOrIsNotNull) {
        this.isNullOrIsNotNull = isNullOrIsNotNull;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean apply(Object obj, Object value) {
        if (isNullOrIsNotNull){
            return value==null;
        } else {
            return value!=null;
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public boolean isIsNullOrIsNotNull() {
        return isNullOrIsNotNull;
    }
    
}
