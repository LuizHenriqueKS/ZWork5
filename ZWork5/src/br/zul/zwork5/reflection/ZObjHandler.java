package br.zul.zwork5.reflection;

import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author luizh
 */
public class ZObjHandler {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Object obj;
    private Map<String, ZVarHandler> varMap;
    private ZList<ZMethodHandler> methodList;
    private ZClass<?> _class;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZObjHandler(Object obj) {
        this.obj = obj;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<ZMethodHandler> listMethods(){
        return getMethodList().copy();
    }
    
    public ZList<ZVarHandler> listVars(){
        return new ZList<>(getVarMap().values());
    }
    
    public ZVarHandler getVar(String name){
        return getVarMap().get(name);
    }
    
    public ZClass<?> getObjClass(){
        if (_class==null) _class = new ZClass<>(obj.getClass());
        return _class;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void initVarMap() {
        varMap = new ZVarHandlerMapper(this).map();
    }

    private void initMethodList() {
        methodList = new ZMethodHandlerLister(this).list();
    }

    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public Map<String, ZVarHandler> getVarMap(){
        if (varMap==null) initVarMap();
        return varMap;
    }

    private ZList<ZMethodHandler> getMethodList() {
        if (methodList==null) initMethodList();
        return methodList;
    }
    
    public Map<String, String> getStringVarMap() throws ZVarHandlerException {
        Map<String, String> result = new LinkedHashMap<>();
        for (ZVarHandler var:listVars()){
            ZValue value = var.getValue();
            result.put(var.getName(), value.optString());
        }
        return result;
    }
    
    public Map<String, Object> getObjVarMap() throws ZVarHandlerException{
        Map<String, Object> result = new LinkedHashMap<>();
        for (Entry<String, ZVarHandler> e: getVarMap().entrySet()){
            String key = e.getKey();
            Object value = e.getValue().getValue().asObject();
            result.put(key, value);    
        }
        return result;
    }
    
    //==========================================================================
    //GETTERS E STTERS
    //==========================================================================
    public Object getObj() {
        return obj;
    }
    
}
