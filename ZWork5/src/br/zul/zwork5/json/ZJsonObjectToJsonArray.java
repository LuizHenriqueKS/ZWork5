package br.zul.zwork5.json;

import br.zul.zwork4.util.ZStrUtils;

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
    public ZJsonArray convert() {
        ZJsonArray jsonArray = new ZJsonArray();
        addValuesWithNumericKeys(jsonArray);
        addValuesWithOthersKeys(jsonArray);
        return jsonArray;
    }

    //==========================================================================
    //MÉTODOS PRIVADFOS
    //==========================================================================
    private void addValuesWithNumericKeys(ZJsonArray jsonArray) {
        jsonObject.forEach((key, value)->{
           if (ZStrUtils.isInteger(key)){
               jsonArray.put(Integer.valueOf(key), value);
           } 
        });
    }

    private void addValuesWithOthersKeys(ZJsonArray jsonArray) {
        jsonObject.forEach((key, value)->{
           if (!ZStrUtils.isInteger(key)){
               jsonArray.add(value);
           } 
        });
    }
    
}
