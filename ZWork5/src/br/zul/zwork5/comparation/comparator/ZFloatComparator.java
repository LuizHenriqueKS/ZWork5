package br.zul.zwork5.comparation.comparator;

import br.zul.zwork5.comparation.ZComparator;

/**
 *
 * @author luizh
 */
public class ZFloatComparator implements ZComparator<Float> {

    @Override
    public Class<Float> getType() {
        return Float.class;
    }

    @Override
    public int compare(Float obj1, Float obj2) {
        return obj1.compareTo(obj2);
    }
    
}
