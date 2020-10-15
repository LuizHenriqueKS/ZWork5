package br.zul.zwork5.query;

import br.zul.zwork5.reflection.ZClass;
import br.zul.zwork5.reflection.ZField;
import br.zul.zwork5.exception.ZQueryFilterExpressionIsInvalidException;
import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.str.ZStrList;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZPair;

/**
 *
 * @author luizh
 */
class ZQueryClassFieldPairLister {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Class<?> objClass;
    private final String expression;
    
    //==========================================================================
    //CONSTRUTOS
    //==========================================================================
    public ZQueryClassFieldPairLister(Class<?> objClass, String expression) {
        this.objClass = objClass;
        this.expression = expression;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<ZPair<ZClass,ZField>> list() {
        ZList<ZPair<ZClass, ZField>> result = new ZList<>();
        fillResultList(result);
        return result;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void fillResultList(ZList<ZPair<ZClass, ZField>> result) {
        ZStrList strList = new ZStr(expression).split(".");
        ZClass cls = new ZClass(objClass);
        for (ZStr fieldName:strList){
            ZField field = cls.getField(fieldName.toString());
            if (field==null) throw new ZQueryFilterExpressionIsInvalidException(cls, expression);
            addPairIntoList(result, cls, field);
            cls = new ZClass(field.getType());
        }
    }

    private void addPairIntoList(ZList<ZPair<ZClass, ZField>> result, ZClass cls, ZField field) {
        ZPair<ZClass, ZField> pair = new ZPair<>();
        pair.setA(cls);
        pair.setB(field);
        result.add(pair);
    }
    
}
