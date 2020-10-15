package br.zul.zwork5.query;

import br.zul.zwork5.util.ZList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author luizh
 * @param <T>
 */
public class ZQuery<T> {
        
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    final Class<T> objClass;
    final Map<String, ZQueryFilter<?>> filterMap;
    final ZList<ZQueryOrder> orderList;
    
    private Integer offset;
    private Integer limit;
    private Integer maxLevel;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQuery(Class<T> objClass){
        this.objClass = objClass;
        this.filterMap = new LinkedHashMap<>();
        this.orderList = new ZList<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.objClass);
        hash = 89 * hash + Objects.hashCode(this.filterMap);
        hash = 89 * hash + Objects.hashCode(this.orderList);
        hash = 89 * hash + Objects.hashCode(this.offset);
        hash = 89 * hash + Objects.hashCode(this.limit);
        hash = 89 * hash + Objects.hashCode(this.maxLevel);
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
        final ZQuery<?> other = (ZQuery<?>) obj;
        if (!Objects.equals(this.objClass, other.objClass)) {
            return false;
        }
        if (!Objects.equals(this.filterMap, other.filterMap)) {
            return false;
        }
        if (!Objects.equals(this.orderList, other.orderList)) {
            return false;
        }
        if (!Objects.equals(this.offset, other.offset)) {
            return false;
        }
        if (!Objects.equals(this.limit, other.limit)) {
            return false;
        }
        if (!Objects.equals(this.maxLevel, other.maxLevel)) {
            return false;
        }
        return true;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<T> list(Collection<T> collection){
        return new ZList<>(stream(collection));
    }
    
    public Stream<T> stream(Collection<T> collection){
        Stream<T> result = collection.stream().filter(this::filter).sorted(buildComparator());
        if (offset!=null){
            result = result.skip(offset);
        }
        if (limit!=null){
            result = result.limit(limit);
        }
        return result;
    }
    
    public long count(Collection<T> collection){
        return collection.stream().filter(this::filter).count();
    }
    
    public boolean filter(T obj){
        for (ZQueryFilter<?> filter:filterMap.values()){
            if (!filter.test(obj)){
                return false;
            }
        }
        return true;
    }
    
    public void sort(List<T> list){
        list.sort(buildComparator());
    }
    
    public ZList<ZQueryFilter<?>> listFilters(){
        return new ZList<>(filterMap.values());
    }
    
    public ZList<ZQueryOrder> listOrders(){
        return new ZList<>(orderList);
    }
    
    public ZQueryOrder addOrder(String expression, boolean ascending){
        ZQueryOrder order = new ZQueryOrder(orderList.size(), expression, ascending);
        orderList.add(order);
        return order;
    }
    
    public ZQueryFilter<Object> getFilter(String expression){
        return getFilter(expression, Object.class);
    }
    
    public <F> ZQueryFilter<F> getFilter(String expression, Class<F> filterType){
        if (!filterMap.containsKey(expression)){
            ZQueryFilter<F> filter = new ZQueryFilter<>(this, expression);
            filterMap.put(expression, filter);
            return filter;
        }
        return (ZQueryFilter<F>)filterMap.get(expression);
    }
    
    public Comparator<T> buildComparator(){
        return new ZQueryObjListSorter<>(this);
    }
    
    public ZQuery<T> copy(){
        ZQuery<T> query = new ZQuery<>(objClass);
        filterMap.forEach((key, val)->{
            val.listConditions().forEach(query.getFilter(key)::addCondition);
        });
        query.orderList.addAll(orderList);
        query.setOffset(query.getOffset());
        query.setLimit(query.getLimit());
        query.setMaxLevel(query.getMaxLevel());
        return query;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Class<T> getObjClass() {
        return objClass;
    }

    public Integer getOffset() {
        return offset;
    }
    public ZQuery<T> setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }
    public ZQuery<T> setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }
    public ZQuery<T> setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }
    
}
