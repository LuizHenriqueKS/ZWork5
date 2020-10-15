package br.zul.zwork5.query;

import br.zul.zwork5.reflection.ZField;
import br.zul.zwork5.reflection.ZObjHandler;
import br.zul.zwork5.exception.ZIntermediaryNullValueException;
import br.zul.zwork5.exception.ZUnexpectedException;
import br.zul.zwork5.exception.ZVarHandlerException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.value.ZValue;

/**
 *
 * @author luiz.silva
 */
class ZQueryFilterValuer {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZQueryFilter filter;
    private final Object obj;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilterValuer(ZQueryFilter filter, Object obj){
        this.filter = filter;
        this.obj = obj;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Object value(){
        ZList<ZField> fieldList = filter.listFields(obj.getClass());
        Object currentObj = obj;
        for (ZField field:fieldList){
            try {
                if (currentObj==null) throw new ZIntermediaryNullValueException();
                ZObjHandler objHandler = new ZObjHandler(currentObj);
                ZValue value = objHandler.getVar(field.getName()).getValue();
                currentObj = value==null?null:value.rawObj();
            } catch (ZVarHandlerException ex) {
                throw new ZUnexpectedException(ex);
            }
        }
        return currentObj;
    }
    
}
