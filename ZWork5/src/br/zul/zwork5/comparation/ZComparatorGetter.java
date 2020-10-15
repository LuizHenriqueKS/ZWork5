package br.zul.zwork5.comparation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author luizh
 */
public class ZComparatorGetter {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZComparationManager comparationManager;
    private final Class<?> type;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZComparatorGetter(ZComparationManager comparationManager, Class<?> type) {
        this.comparationManager = comparationManager;
        this.type = type;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZComparator get(){
        for (Supplier<ZComparator<?>> getter:listGetters()){
            ZComparator<?> comparator = getter.get();
            if (comparator!=null){
                return comparator;
            }
        }
        return null;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private List<Supplier<ZComparator<?>>> listGetters() {
        return Arrays.asList(
            ()->getComparatorFromList(),
            ()->getComparatorFromObj(),
            ()->getComparatorSimilarFromList()
        );
    }

    private ZComparator<?> getComparatorFromList() {
        return comparationManager.getComparatorList().optFirst(c->c.getType().equals(type));
    }

    private ZComparator<?> getComparatorFromObj() {
        if (type.isAssignableFrom(Comparator.class)||type.isAssignableFrom(Comparable.class)){
            return new ZComparatorFrom(type);
        }
        return null;
    }

    private ZComparator<?> getComparatorSimilarFromList() {
        return comparationManager.getComparatorList().optFirst(c->type.isAssignableFrom(c.getType()));
    }
    
}
