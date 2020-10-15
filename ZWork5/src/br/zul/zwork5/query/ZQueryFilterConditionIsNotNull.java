package br.zul.zwork5.query;

/**
 *
 * @author luizh
 */
public class ZQueryFilterConditionIsNotNull implements ZQueryFilterCondition {

    @Override
    public boolean apply(Object obj, Object value) {
        return value != null;
    }
    
}
