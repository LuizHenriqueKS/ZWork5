package br.zul.zwork5.reflection;

import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZStrUtils;
import br.zul.zwork5.util.ZUtil;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author luizh
 */
class ZVarHandlerMapper {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZObjHandler objHandler;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================    
    public ZVarHandlerMapper(ZObjHandler objHandler) {
        this.objHandler = objHandler;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================    
    public Map<String, ZVarHandler> map() {
        Map<String, ZVarHandler> result = new LinkedHashMap<>();
        fillWithVarsFromFields(result);
        fillWithVarsFromMethods(result);
        result.remove("class");
        return result;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================    
    private void fillWithVarsFromFields(Map<String, ZVarHandler> result) {
        for (ZField field: objHandler.getObjClass().listFields()){
            if (field.isStatic()) continue;
            String name = field.getName();
            if (ZUtil.hasContent(name)){
                ZVarHandler varHandler = createVarHandler(name);
                varHandler.field = field;
                result.put(name, varHandler);
            }
        }
    }

    private void fillWithVarsFromMethods(Map<String, ZVarHandler> result) {
        for (ZMethod method: objHandler.getObjClass().listMethods()){
            if (method.isStatic()) continue;
            if (method.getName().startsWith("get")&&method.listParamTypes().isEmpty()){
                addGetter(result, method);
            } else if (method.getName().startsWith("set"))   {
                addSetter(result, method);
            } 
        }
    }

    private void addGetter(Map<String, ZVarHandler> result, ZMethod method) {
        String varName = getVarName(method);
        if (ZUtil.hasContent(varName)){
            if (!result.containsKey(varName)){
                result.put(varName, createVarHandler(varName));
            }
            result.get(varName).getter = createMethodHandler(method);
        }
    }

    private void addSetter(Map<String, ZVarHandler> result, ZMethod method) {
        String varName = getVarName(method);
        if (ZUtil.hasContent(varName)) {
            if (!result.containsKey(varName)) {
                result.put(varName, createVarHandler(varName));
            }
            ZVarHandler varHandler = result.get(varName);
            if (varHandler.setterList == null) {
                varHandler.setterList = new ZList<>();
            }
            varHandler.setterList.add(createMethodHandler(method));
        }
    }
    

    private ZVarHandler createVarHandler(String varName) {
        ZVarHandler varHandler = new ZVarHandler(objHandler, varName);
        return varHandler;
    }

    private String getVarName(ZMethod method) {
        return ZStrUtils.firstCharToLowerCase(method.getName().substring(3));
    }

    private ZMethodHandler createMethodHandler(ZMethod method) {
        return new ZMethodHandler(objHandler, method);
    }
    
}
