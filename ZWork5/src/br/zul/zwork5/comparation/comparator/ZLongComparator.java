package br.zul.zwork5.comparation.comparator;

import br.zul.zwork5.comparation.ZComparator;

/**
 *
 * @author luizh
 */
public class ZLongComparator implements ZComparator<Long> {

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

    @Override
    public int compare(Long obj1, Long obj2) {
        return obj1.compareTo(obj2);
    }
    
}
