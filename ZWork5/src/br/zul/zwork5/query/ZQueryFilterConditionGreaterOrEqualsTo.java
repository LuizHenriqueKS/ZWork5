package br.zul.zwork5.query;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZUnexpectedException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionGreaterOrEqualsTo implements ZQueryFilterCondition{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Object greaterOrEqualsTo;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilterConditionGreaterOrEqualsTo(Object greaterOrEqualsTo) {
        this.greaterOrEqualsTo = greaterOrEqualsTo;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean apply(Object obj, Object value){
        try {
            Double currentValue = ZConversionManager.getInstance().convert(value, Double.class);
            Double currentGreaterOrEqualsTo = ZConversionManager.getInstance().convert(value, Double.class);
            return currentValue >= currentGreaterOrEqualsTo;
        } catch (ZConversionErrorException ex) {
            throw new ZUnexpectedException(ex);
        }
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getGreaterOrEqualsTo() {
        return greaterOrEqualsTo;
    }
    
}
