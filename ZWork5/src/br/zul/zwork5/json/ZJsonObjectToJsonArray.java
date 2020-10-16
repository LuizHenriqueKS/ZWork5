package br.zul.zwork5.json;

import br.zul.zwork5.exception.ZJsonException;
import br.zul.zwork5.util.ZStrUtils;
import br.zul.zwork5.value.ZValue;
import java.util.Map.Entry;

/**
 *
 * @author luizh
 */
class ZJsonObjectToJsonArray {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZJsonObject jsonObject;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZJsonObjectToJsonArray(ZJsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZJsonArray convert() throws ZJsonException {
        ZJsonArray jsonArray = new ZJsonArray();
        addValuesWithNumericKeys(jsonArray);
        addValuesWithOthersKeys(jsonArray);
        return jsonArray;
    }

    //==========================================================================
    //MÉTODOS PRIVADFOS
    //==========================================================================
    private void addValuesWithNumericKeys(ZJsonArray jsonArray) throws ZJsonException {
        for (Entry<String, ZValue> e : jsonObject.asMap().entrySet()) {
            if (ZStrUtils.isInteger(e.getKey())) {
                jsonArray.put(Integer.valueOf(e.getKey()), e.getValue());
            }
        }
    }

    private void addValuesWithOthersKeys(ZJsonArray jsonArray) throws ZJsonException {
        for (Entry<String, ZValue> e : jsonObject.asMap().entrySet()) {
            String key = e.getKey();
            ZValue value = e.getValue();
            if (!ZStrUtils.isInteger(key)) {
                jsonArray.add(value);
            }
        }
    }
    
}
