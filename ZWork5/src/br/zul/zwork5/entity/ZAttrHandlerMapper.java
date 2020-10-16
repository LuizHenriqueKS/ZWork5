package br.zul.zwork5.entity;

import br.zul.zwork5.reflection.ZObjHandler;
import br.zul.zwork5.reflection.ZVarHandler;
import br.zul.zwork5.util.ZList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author luizh
 */
class ZAttrHandlerMapper {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZEntityHandler entityHandler;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZAttrHandlerMapper(ZEntityHandler entityHandler) {
        this.entityHandler = entityHandler;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Map<String, ZAttrHandler> map() {
        Map<String, ZAttrHandler> result = new LinkedHashMap<>();
        convertToAttrs(listVarHandlers()).forEach(a->result.put(a.getName(), a));
        return result;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZList<ZVarHandler> listVarHandlers(){
        ZObjHandler objHandler = new ZObjHandler(entityHandler.entity);
        return objHandler.listVars();
    }

    private ZList<ZAttrHandler> convertToAttrs(ZList<ZVarHandler> varHandlerList) {
        ZList<ZAttrHandler> attrList = new ZList<>();
        for (ZVarHandler varHandler:varHandlerList){
            if (varHandler.hasAnnotation(ZAttribute.class)){
                attrList.add(varToAttr(varHandler));
            }
        }
        return attrList;
    }
    
    private ZAttrHandler varToAttr(ZVarHandler varHandler){
        return new ZAttrHandler(entityHandler, varHandler);
    }
    
}
