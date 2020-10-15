package br.zul.zwork5.conversion;

import br.zul.zwork5.reflection.ZClass;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZListMap;

/**
 *
 * @author Luiz Henrique
 */
public class ZConverterList {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZList<ZConverter<?,?>> converterList;
    private final ZListMap<Class<?>, ZConverter<?,?>> converterListMap;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZConverterList() {
        this.converterList = new ZList<>();
        this.converterListMap = new ZListMap<>();
    }
 
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void addConverter(ZConverter<?, ?> converter){
        converterList.add(converter);
        converterListMap.addValue(converter.getAClass(), converter);
        converterListMap.addValue(converter.getBClass(), converter);
    }
    
    public void removeConverter(ZConverter<?, ?> converter){
        converterList.remove(converter);
        converterListMap.removeValue(converter.getAClass(), converter);
        converterListMap.removeValue(converter.getBClass(), converter);
    }
    
    public ZList<ZConverter<?, ?>> listConverters(){
        return converterList.copy();
    }

    public boolean hasConverterTo(Class<?> oneOfClass) {
        ZList<Class<?>> classList = new ZList<>();
        classList.add(oneOfClass);
        classList.addAll(new ZClass(oneOfClass).listAllParentClasses());
        return classList.stream().anyMatch(c->converterListMap.keySet().contains(c));
    }

    public ZList<ZConverter<?, ?>> listConvertersOf(ZConversionObj conversionObj) {
        ZList<ZConverter<?, ?>> result = new ZList<>();
        for (ZConverter<?, ?> converter:listConverters()){
            if (converter.validateInputA(conversionObj)){
                result.add(converter);
            } 
            if (converter.validateInputB(conversionObj)){
                result.add(new ZInverseConverter<>(converter));
            }
        }
        return result;
    }

    public ZList<ZConverter<?, ?>> listConvertersOf(ZConversionObj conversionObj, Class<?> sourceClass) {
        ZList<ZConverter<?, ?>> result = new ZList<>();
        ZConversionObj localCo = conversionObj.copy();
        localCo.setSourceClass(sourceClass);;
        for (ZConverter<?, ?> converter:listConverters()){
            if (converter.validateInputA(localCo)){
                result.add(converter);
            } 
            if (converter.validateInputB(localCo)){
                result.add(new ZInverseConverter<>(converter));
            }
        }
        return result;
    }
    
}
