package br.zul.zwork5.conversion;

import br.zul.zwork5.currency.ZCurrency;
import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.json.ZJson;
import br.zul.zwork5.json.ZJsonArray;
import br.zul.zwork5.json.ZJsonObject;
import br.zul.zwork5.timestamp.ZTimeStamp;
import br.zul.zwork5.value.ZValue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author luiz.silva
 */
public class ZConversionObjChecker {
    
    //==========================================================================
    //CONSTANTES
    //==========================================================================
    private static final List<Class<?>> BASE_CLASS_LIST;
    
    //==========================================================================
    //INICIALIZADOR
    //==========================================================================
    static {
        BASE_CLASS_LIST = new ArrayList<>();
        BASE_CLASS_LIST.add(Number.class);
        BASE_CLASS_LIST.add(String.class);
        BASE_CLASS_LIST.add(CharSequence.class);
        BASE_CLASS_LIST.add(Long.class);
        BASE_CLASS_LIST.add(Integer.class);
        BASE_CLASS_LIST.add(Double.class);
        BASE_CLASS_LIST.add(Float.class);
        BASE_CLASS_LIST.add(Boolean.class);
        BASE_CLASS_LIST.add(boolean.class);
        BASE_CLASS_LIST.add(long.class);
        BASE_CLASS_LIST.add(int.class);
        BASE_CLASS_LIST.add(float.class);
        BASE_CLASS_LIST.add(double.class);
        BASE_CLASS_LIST.add(Date.class);
        BASE_CLASS_LIST.add(ZTimeStamp.class);
        BASE_CLASS_LIST.add(BigDecimal.class);
        
        try {
            BASE_CLASS_LIST.add(ZJson.class);
            BASE_CLASS_LIST.add(ZJsonObject.class);
            BASE_CLASS_LIST.add(ZJsonArray.class);
            BASE_CLASS_LIST.add(JSONObject.class);
            BASE_CLASS_LIST.add(JSONArray.class);
        }catch(NoClassDefFoundError e){}
        
        BASE_CLASS_LIST.add(ZCurrency.class);
        BASE_CLASS_LIST.add(Map.class);
        BASE_CLASS_LIST.add(Collection.class);
        BASE_CLASS_LIST.add(Set.class);
        BASE_CLASS_LIST.add(ZEntity.class);
        BASE_CLASS_LIST.add(ZValue.class);
        BASE_CLASS_LIST.add(ZFile.class);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public static boolean isBaseClass(Class<?> cls){
        return BASE_CLASS_LIST.stream()
                              .anyMatch(base->base.isAssignableFrom(cls));
    }
    
}
