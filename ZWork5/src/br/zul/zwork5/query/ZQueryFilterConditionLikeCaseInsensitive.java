package br.zul.zwork5.query;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionLikeCaseInsensitive implements ZQueryFilterCondition{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZQueryFilterConditionLike conditionLike;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilterConditionLikeCaseInsensitive(String likeValue) {
        this.conditionLike = new ZQueryFilterConditionLike(likeValue, false);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean apply(Object obj, Object value) {
        return  conditionLike.apply(obj, value);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getLikeValue(){
        return conditionLike.getLikeValue();
    }
    
}
