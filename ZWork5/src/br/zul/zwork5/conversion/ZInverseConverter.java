package br.zul.zwork5.conversion;

import br.zul.zwork5.exception.ZConversionErrorException;

/**
 *
 * @author Luiz Henrique
 * @param <A>
 * @param <B>
 */
public class ZInverseConverter<A, B> implements ZConverter<A, B> {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZConverter<B, A> converter;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZInverseConverter(ZConverter<B, A> converter) {
        this.converter = converter;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public Class<A> getAClass() {
        return converter.getBClass();
    }

    @Override
    public Class<B> getBClass() {
        return converter.getAClass();
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, B value) throws ZConversionErrorException {
        return converter.convertToB(obj, value);
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, A value) throws ZConversionErrorException {
        return converter.convertToA(obj, value);
    }

    @Override
    public boolean validateInputA(ZConversionObj obj, A value) {
        return converter.validateInputB(obj, value);
    }

    @Override
    public boolean validateInputB(ZConversionObj obj, B value) {
        return converter.validateInputA(obj, value);
    }

    @Override
    public boolean validateOutputA(ZConversionObj obj, Class<? extends A> targetClass) {
        return converter.validateOutputB(obj, targetClass);
    }

    @Override
    public boolean validateOutputB(ZConversionObj obj, Class<? extends B> targetClass) {
        return converter.validateOutputA(obj, targetClass);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZConverter<B, A> getConverter() {
        return converter;
    }   

}
