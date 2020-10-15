package br.zul.zwork5.query;

import br.zul.zwork5.reflection.ZClass;
import br.zul.zwork5.reflection.ZField;
import br.zul.zwork5.exception.ZIntermediaryNullValueException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZPair;
import java.util.Objects;

/**
 *
 * @author luizh
 * @param <T>
 */
public class ZQueryFilter<T> {
 
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZQuery<?> query;
    private final String expression;
    private final ZList<ZQueryFilterCondition> conditionList;
    private final ZList<ZQueryOrder> orderList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryFilter(ZQuery<?> query, String expression){
        this.query = query;
        this.expression = expression;
        this.conditionList = new ZList<>();
        this.orderList = new ZList<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.query.getObjClass());
        hash = 41 * hash + Objects.hashCode(this.expression);
        hash = 41 * hash + Objects.hashCode(this.conditionList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZQueryFilter<?> other = (ZQueryFilter<?>) obj;
        if (!Objects.equals(this.expression, other.expression)) {
            return false;
        }
        if (!Objects.equals(this.query.getObjClass(), other.query.getObjClass())) {
            return false;
        }
        if (!Objects.equals(this.conditionList, other.conditionList)) {
            return false;
        }
        return true;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public boolean test(Object obj){
        Object value = optValue(obj);
        for (ZQueryFilterCondition condition:conditionList){
            if (!condition.apply(obj, value)){
                return false;
            }
        }
        return true;
    }
    
    public ZQueryFilter<T> addCondition(ZQueryFilterCondition condition){
        conditionList.add(condition);
        return this;
    }
    
    public ZQueryFilter<T> addOrder(boolean ascending){
        ZQueryOrder order = query.addOrder(expression, ascending);
        orderList.add(order);
        return this;
    }
    
    public ZQueryFilter<T> addEquals(T equalsTo){
        conditionList.add(new ZQueryFilterConditionEquals(equalsTo));
        return this;
    }
    
    public ZQueryFilter<T> addNotEquals(T equalsTo){
        conditionList.add(new ZQueryFilterConditionNotEquals(equalsTo));
        return this;
    }
    
    public ZQueryFilter<T> addLike(String like){
        conditionList.add(new ZQueryFilterConditionLike(like));
        return this;
    }
    
    public ZQueryFilter<T> addLessThan(Object value) {
        conditionList.add(new ZQueryFilterConditionLessThan(value));
        return this;
    }
    
    public ZQueryFilter<T> addGreaterOrEqualsTo(Object value) {
        conditionList.add(new ZQueryFilterConditionGreaterOrEqualsTo(value));
        return this;
    }
    
    public ZQueryFilter<T> addLikeCaseInsensitive(String value){
        conditionList.add(new ZQueryFilterConditionLikeCaseInsensitive(value));
        return this;
    }

    public ZQueryFilter<T> addAnyLikeCaseInsensitive(String... values) {
        conditionList.add(new ZQueryFilterConditionAnyLikeCaseInsensitive(values));
        return this;
    }
    
    public ZQueryFilter<T> addIsNull(){
        conditionList.add(new ZQueryFilterConditionIsNull());
        return this;
    }
    
    public ZQueryFilter<T> addIsNull(boolean isNullOrNotNull){
        conditionList.add(new ZQueryFilterConditionIsNull(isNullOrNotNull));
        return this;
    }
    
    public ZQueryFilter<T> addIsNotNull(){
        conditionList.add(new ZQueryFilterConditionIsNotNull());
        return this;
    }
    
    public ZList<ZQueryFilterCondition> listConditions(){
        return conditionList.copy();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getExpression() {
        return expression;
    }

    public ZQuery<?> getQuery() {
        return query;
    }
    
    public ZList<ZPair<ZClass, ZField>> listClassFieldPairs(Class<?> objClass){
        return new ZQueryClassFieldPairLister(objClass, expression).list();
    }
    
    public ZList<ZField> listFields(Class<?> objClass){
        return listClassFieldPairs(objClass).map(p->p.getB());
    }
    
    public T getValue(Object obj) throws ZIntermediaryNullValueException{
        return (T)new ZQueryFilterValuer(this, obj).value();
    }
    
    public T optValue(Object obj){
        try {
            return getValue(obj);
        }catch(ZIntermediaryNullValueException e){
            return null;
        }
    }

}
