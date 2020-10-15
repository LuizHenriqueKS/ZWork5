package br.zul.zwork5.query;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionAnyLikeCaseInsensitive implements ZQueryFilterCondition{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final String[] values;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilterConditionAnyLikeCaseInsensitive(String[] values) {
        this.values = values;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean apply(Object obj, Object value) {
        for (String val:values) {
            boolean result = new ZQueryFilterConditionLike(val, false).apply(obj, value);
            if (result){
                return true;
            }
        }
        return false;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String[] getValues() {
        return values;
    }
    
}
