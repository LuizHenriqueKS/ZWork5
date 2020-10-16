package br.zul.zwork5.query;

import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.str.ZStrList;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionLike implements ZQueryFilterCondition{

    //==========================================================================
    //CLASSES PRIVADAS
    //==========================================================================
    private class FailedLikeException extends Exception{};
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final String likeValue;
    private final boolean caseSensitive;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    protected ZQueryFilterConditionLike(String likeValue, boolean caseSensitive) {
        this.likeValue = likeValue;
        this.caseSensitive = caseSensitive;
    }

    public ZQueryFilterConditionLike(String likeValue) {
        this(likeValue, true);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean apply(Object obj, Object value){
        if (likeValue.contains("%")){
            try {
                checkList((String)value);
                return true;
            } catch (FailedLikeException e){
                return false;
            }
        } else {
            return likeValue.equals(value);
        }
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void checkList(String value) throws FailedLikeException {
        ZStrList strList = new ZStr(likeValue).split("%");
        value = checkFirst(strList, value);
        value = checkMiddle(strList, value);
        checkLast(strList, value);
    }
    
    private String checkFirst(ZStrList strList, String value) throws FailedLikeException {
        if (!likeValue.startsWith("%")){
            ZStr pattern = strList.first().get(); 
            if (!pattern.startsWith(value)){
                throw new FailedLikeException();
            }
            strList.removeFirst();
            return value.substring(pattern.length());
        }
        return value;
    }

    private String checkMiddle(ZStrList strList, String value) throws FailedLikeException {
        for (ZStr str:strList){
            int index = value.indexOf(str.toString());
            if (index==-1){
                throw new FailedLikeException();
            }
            value = value.substring(index + str.length());
        }
        return value;
    }

    private void checkLast(ZStrList strList, String value) throws FailedLikeException {
        if (!likeValue.endsWith("%")){
            if (!value.isEmpty()){
                throw new FailedLikeException();
            }
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getLikeValue() {
        return likeValue;
    }
    
}
