package br.zul.zwork5.conversion;

import br.zul.zwork5.util.ZList;

/**
 *
 * @author Luiz Henrique
 */
class ZPossibleConversionTree {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZConverterList converterList;
    private final ZList<ZPossibleConversion> pcList;
    private int index;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZPossibleConversionTree(ZConverterList converterList) {
        this.converterList = converterList;
        this.pcList = new ZList<>();
        this.index = 0;
    }

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public boolean hasNext() {
        return pcList.size() > index;
    }

    public ZPossibleConversion next() {
        return pcList.get(index++);
    }

    public void addOf(ZConversionObj conversionObj) {
        for (ZConverter<?,?> converter: converterList.listConvertersOf(conversionObj)){
            ZPossibleConversion pc = new ZPossibleConversion(converter.getAClass(), converter);
            pcList.add(pc);
        }
    }

    public void addOf(ZPossibleConversion pc, ZConversionObj conversionObj, Class<?> sourceClass) {
        for (ZConverter<?,?> converter: converterList.listConvertersOf(conversionObj, sourceClass)){
            try {
                if (!isFamily(pc.getConverter(), converter)){
                    ZPossibleConversion p = new ZPossibleConversion(pc, converter);
                    pcList.add(p);
                }
            }catch(NoClassDefFoundError e){}
        }
    }

    private boolean isFamily(ZConverter<?, ?> a, ZConverter<?, ?> b) {
        return getOriginal(a).equals(getOriginal(b));
    }

    private ZConverter<?, ?> getOriginal(ZConverter<?, ?> converter) {
        if (converter instanceof ZInverseConverter){
            return getOriginal(((ZInverseConverter<Object, Object>)converter).getConverter());
        }
        return converter;
    }
    
}
