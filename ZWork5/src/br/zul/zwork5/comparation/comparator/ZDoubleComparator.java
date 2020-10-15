package br.zul.zwork5.comparation.comparator;

import br.zul.zwork5.comparation.ZComparator;

/**
 *
 * @author luizh
 */
public class ZDoubleComparator implements ZComparator<Double> {

    @Override
    public Class<Double> getType() {
        return Double.class;
    }

    @Override
    public int compare(Double obj1, Double obj2) {
        return obj1.compareTo(obj2);
    }
    
}
