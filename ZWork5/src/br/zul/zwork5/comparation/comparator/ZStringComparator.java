package br.zul.zwork5.comparation.comparator;

import br.zul.zwork5.comparation.ZComparator;

/**
 *
 * @author luizh
 */
public class ZStringComparator implements ZComparator<String> {

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public int compare(String obj1, String obj2) {
        return obj1.compareTo(obj2);
    }
    
}
