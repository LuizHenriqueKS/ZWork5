package br.zul.zwork5.conversion;

import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.util.ZList;

/**
 *
 * @author Luiz Henrique
 * @param <A>
 * @param <B>
 */
public class ZDynamicConverter<A, B> implements ZConverter<A, B> {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZPossibleConversion pc;
    private final ZList<ZConverter<?,?>> aList;
    private final ZList<ZConverter<?,?>> bList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDynamicConverter(ZPossibleConversion pc) {
        this.pc = pc;
        this.bList = initBList();
        this.aList = initAList();
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public Class<A> getAClass() {
        return (Class<A>)pc.getSourceClass();
    }

    @Override
    public Class<B> getBClass() {
        return (Class<B>)pc.getTargetClass();
    }

    @Override
    public ZConversionObj convertToA(ZConversionObj obj, B value) throws ZConversionErrorException {
        ZConversionObj _obj = obj;
        for (ZConverter<?, ?> converter: aList){
            _obj = converter.convertToA(_obj);
        }
        return _obj;
    }

    @Override
    public ZConversionObj convertToB(ZConversionObj obj, A value) throws ZConversionErrorException {
        ZConversionObj _obj = obj;
        for (ZConverter<?, ?> converter: bList){
            _obj = converter.convertToB(_obj);
        }
        return _obj;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZList<ZConverter<?, ?>> initAList() {
        return bList.reverse();
    }

    private ZList<ZConverter<?, ?>> initBList() {
        ZList<ZConverter<?, ?>> result = new ZList<>();
        ZPossibleConversion p = pc;
        while (p!=null){
            result.add(p.getConverter());
            p = p.getParent();
        }
        return result;
    }
    
}
