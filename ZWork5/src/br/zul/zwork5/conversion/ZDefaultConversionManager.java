package br.zul.zwork5.conversion;

import br.zul.zwork5.conversion.converter.ZBigDecimalStringConverter;
import br.zul.zwork5.conversion.converter.ZBooleanStringConverter;
import br.zul.zwork5.conversion.converter.ZClassStringConverter;
import br.zul.zwork5.conversion.converter.ZCurrencyStringConverter;
import br.zul.zwork5.conversion.converter.ZDateAndDateConverter;
import br.zul.zwork5.conversion.converter.ZDateAndLongConverter;
import br.zul.zwork5.conversion.converter.ZDateStringConverter;
import br.zul.zwork5.conversion.converter.ZDateTimeAndIntegerConverter;
import br.zul.zwork5.conversion.converter.ZDateTimeAndLongConverter;
import br.zul.zwork5.conversion.converter.ZDateTimeStringConverter;
import br.zul.zwork5.conversion.converter.ZDoubleStringConverter;
import br.zul.zwork5.conversion.converter.ZEntityStringConverter;
import br.zul.zwork5.conversion.converter.ZEnumStringConverter;
import br.zul.zwork5.conversion.converter.ZFloatStringConverter;
import br.zul.zwork5.conversion.converter.ZIntegerStringConverter;
import br.zul.zwork5.conversion.converter.ZJSONArrayRawStringConverter;
import br.zul.zwork5.conversion.converter.ZJSONObjectRawStringConverter;
import br.zul.zwork5.conversion.converter.ZJsonAndObjConverter;
import br.zul.zwork5.conversion.converter.ZJsonArrayAndJSONArrayConverter;
import br.zul.zwork5.conversion.converter.ZJsonArrayStringConverter;
import br.zul.zwork5.conversion.converter.ZJsonObjectAndJSONObjectConverter;
import br.zul.zwork5.conversion.converter.ZJsonObjectAndObjConverter;
import br.zul.zwork5.conversion.converter.ZJsonObjectStringConverter;
import br.zul.zwork5.conversion.converter.ZJsonStringConverter;
import br.zul.zwork5.conversion.converter.ZLongStringConverter;
import br.zul.zwork5.conversion.converter.ZMapAndObjConverter;
import br.zul.zwork5.conversion.converter.ZStrStringConverter;
import br.zul.zwork5.conversion.converter.ZTimeStampAndDateTimeConverter;
import br.zul.zwork5.conversion.converter.ZTimeStringConverter;
import br.zul.zwork5.log.ZLog;

/**
 *
 * @author Luiz Henrique
 */
public class ZDefaultConversionManager extends ZConversionManager {

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDefaultConversionManager() {
        init();
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(){
        tryAddConverter(new ZIntegerStringConverter());
        tryAddConverter(new ZLongStringConverter());
        tryAddConverter(new ZBooleanStringConverter());
        tryAddConverter(new ZStrStringConverter());
        tryAddConverter(new ZDoubleStringConverter());
        tryAddConverter(new ZDateStringConverter());
        tryAddConverter(new ZDateAndLongConverter());
        tryAddConverter(new ZDateTimeStringConverter());
        tryAddConverter(new ZTimeStringConverter());
        tryAddConverter(new ZClassStringConverter());
        tryAddConverter(new ZJsonStringConverter());
        tryAddConverter(new ZJSONObjectRawStringConverter());
        tryAddConverter(new ZJSONArrayRawStringConverter());
        tryAddConverter(new ZFloatStringConverter());
        tryAddConverter(new ZBigDecimalStringConverter());
        tryAddConverter(new ZEntityStringConverter());
        tryAddConverter(new ZJsonObjectStringConverter());
        tryAddConverter(new ZJsonArrayStringConverter());
        tryAddConverter(new ZCurrencyStringConverter());
        tryAddConverter(new ZJsonAndObjConverter());
        tryAddConverter(new ZJsonObjectAndObjConverter());
        tryAddConverter(new ZMapAndObjConverter());
        tryAddConverter(new ZJsonObjectAndJSONObjectConverter());
        tryAddConverter(new ZJsonArrayAndJSONArrayConverter());
        tryAddConverter(new ZDateTimeAndLongConverter());
        tryAddConverter(new ZDateTimeAndIntegerConverter());
        tryAddConverter(new ZEnumStringConverter());
        tryAddConverter(new ZTimeStampAndDateTimeConverter());
        tryAddConverter(new ZDateAndDateConverter());
    }
    
    public void tryAddConverter(ZConverter<?, ?> converter){
        try {
            converter.getAClass();
            converter.getBClass();
            addConverter(converter);
        } catch (NoClassDefFoundError e){
            ZLog.w(this).println("Não possível adicionar o conversor: "+ converter.getClass().getName());
        }
    }
    
    
}
