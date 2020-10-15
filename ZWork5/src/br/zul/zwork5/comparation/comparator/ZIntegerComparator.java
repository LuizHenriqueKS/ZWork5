package br.zul.zwork5.comparation.comparator;

import br.zul.zwork5.comparation.ZComparator;

/**
 *
 * @author luizh
 */
public class ZIntegerComparator implements ZComparator<Integer> {

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public int compare(Integer obj1, Integer obj2) {
        return obj1.compareTo(obj2);
    }
    
}
