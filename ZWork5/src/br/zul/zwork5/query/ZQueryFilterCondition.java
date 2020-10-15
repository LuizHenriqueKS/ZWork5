package br.zul.zwork5.query;

/**
 *
 * @author luizh
 */
public interface ZQueryFilterCondition {
    
    boolean apply(Object obj, Object value);
    
}
