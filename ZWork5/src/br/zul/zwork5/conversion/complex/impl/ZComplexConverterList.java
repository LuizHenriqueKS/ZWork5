package br.zul.zwork5.conversion.complex.impl;

import br.zul.zwork5.conversion.ZConversionManager;
import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.annotation.ZListAnnotation;
import br.zul.zwork5.conversion.complex.ZComplexConvertedValueGetter;
import br.zul.zwork5.conversion.complex.ZComplexConverter;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZUnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author luizh
 */
public class ZComplexConverterList implements ZComplexConverter<List>{

    @Override
    public Class<List> getType() {
        return List.class;
    }

    @Override
    public Stream<Object> stream(List obj) {
        return obj.stream();
    }

    @Override
    public List newInstance() {
        return new ArrayList<>();
    }

    @Override
    public void add(List obj, Object item, ZComplexConvertedValueGetter getter) {
        try {
            obj.add(ZConversionManager.getInstance().convert(getConversionObj(item, getter)).getValue());
        } catch (ZConversionErrorException ex) {
            throw new ZUnexpectedException(ex);
        }
    }

    @Override
    public boolean isValidInput(ZComplexConvertedValueGetter getter) {
        return getType().equals(getter.getSourceClass())&&getter.hasAnnotation(ZListAnnotation.class);
    }

    @Override
    public boolean isValidOutput(ZComplexConvertedValueGetter getter) {
        return getType().equals(getter.getTargetClass())&&getter.hasAnnotation(ZListAnnotation.class);
    }

    private ZConversionObj getConversionObj(Object item, ZComplexConvertedValueGetter<?> getter) {
        ZConversionObj obj = new ZConversionObj(item, getter.getAnnotation(ZListAnnotation.class).itemType());
        obj.setAnnotationList(getter.getAnnotationList());
        return obj;
    }
    
}
