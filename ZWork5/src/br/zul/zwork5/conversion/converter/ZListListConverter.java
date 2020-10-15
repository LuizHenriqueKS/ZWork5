package br.zul.zwork5.conversion.converter;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.ZConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import java.util.Collection;

/**
 *
 * @author Luiz Henrique
 */
public class ZListListConverter implements ZConverter<Collection, Collection>{

    @Override
    public Class<Collection> getAClass() {
        return Collection.class;
    }

    @Override
    public Class<Collection> getBClass() {
        return Collection.class;
    }

    @Override
    public boolean validateOutputB(ZConversionObj obj, Class<? extends Collection> targetClass) {
        return ZConverter.super.validateOutputB(obj, targetClass); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validateOutputA(ZConversionObj obj, Class<? extends Collection> targetClass) {
        return ZConverter.super.validateOutputA(obj, targetClass); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, Collection value) throws ZConversionErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, Collection value) throws ZConversionErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
