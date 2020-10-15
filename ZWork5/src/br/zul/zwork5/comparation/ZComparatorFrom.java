package br.zul.zwork5.comparation;

import br.zul.zwork5.exception.ZComparationException;
import br.zul.zwork5.exception.ZNewInstanceException;
import br.zul.zwork5.reflection.ZClass;
import java.util.Comparator;

/**
 *
 * @author luizh
 */
class ZComparatorFrom<T> implements ZComparator<T> {

    private final Class<T> type;
    private Comparator<T> comparator;
    
    public ZComparatorFrom(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public int compare(T obj1, T obj2) throws ZComparationException {
        try {
            Comparable<T> comparable = getComparable(obj1);
            if (comparable!=null){
                return comparable.compareTo(obj2);
            }
            Comparator<T> c = getComparator();
            if (c!=null){
                return compare(obj1, obj2);
            }
            throw new ZComparationException();
        } catch (ZNewInstanceException ex) {
            throw new ZComparationException(ex);
        }
    }

    private Comparable<T> getComparable(T obj1) {
        if (obj1 instanceof Comparable){
            return (Comparable<T>) obj1;
        }
        return null;
    }

    private Comparator<T> getComparator() throws ZNewInstanceException {
        if (comparator==null){
            comparator = (Comparator<T>)new ZClass<>(type).newInstance();
        }
        return comparator;
    }
    
}
