package br.zul.zwork5.query;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZUnexpectedException;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionLessThan implements ZQueryFilterCondition{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Object lessThan;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilterConditionLessThan(Object lessThan) {
        this.lessThan = lessThan;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean apply(Object obj, Object value){
        try {
            Double currentValue = ZConversionManager.getInstance().convert(value, Double.class);
            Double currentLessThan = ZConversionManager.getInstance().convert(value, Double.class);
            return currentValue < currentLessThan;
        } catch (ZConversionErrorException ex) {
            throw new ZUnexpectedException(ex);
        }
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getLessThan() {
        return lessThan;
    }
    
}
