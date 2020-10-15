package br.zul.zwork5.query;

import br.zul.zwork5.comparation.ZComparationManager;
import br.zul.zwork5.exception.ZComparationException;
import br.zul.zwork5.exception.ZComparatorNotFoundException;
import br.zul.zwork5.exception.ZUnexpectedException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZPair;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luizh
 */
class ZQueryObjListSorter<T> implements Comparator<T> {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZQuery<T> query;
    private final ZList<ZPair<ZQueryFilter, ZQueryOrder>> list;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZQueryObjListSorter(ZQuery<T> query) {
        this.query = query;
        this.list = buildList();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    @Override
    public int compare(T o1, T o2) {
        for (ZPair<ZQueryFilter, ZQueryOrder> pair:list){
            try {
                Object v1 = pair.getA().optValue(o1);
                Object v2 = pair.getA().optValue(o2);
                int result = new ZComparationManager().compare(v1, v2);
                if (result!=0) {
                    if (!pair.getB().isAscending()) result = -result;
                    return result;
                }
            } catch (ZComparatorNotFoundException | ZComparationException ex) {
                throw new ZUnexpectedException(ex);
            }
        }
        return 0;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZList<ZPair<ZQueryFilter, ZQueryOrder>> buildList() {
        ZList<ZPair<ZQueryFilter, ZQueryOrder>> result = new ZList<>();
        for (ZQueryOrder order:query.listOrders()){
            ZPair<ZQueryFilter, ZQueryOrder> pair = new ZPair<>();
            pair.setA(query.getFilter(order.getExpression()));
            pair.setB(order);
            result.add(pair);
        }
        return result;
    }
    
}
