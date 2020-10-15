package br.zul.zwork5.comparation.comparator;

import br.zul.zwork5.comparation.ZComparator;
import br.zul.zwork5.timestamp.ZTimeStamp;

/**
 *
 * @author luizh
 */
public class ZTimestampComparator implements ZComparator<ZTimeStamp> {

    @Override
    public Class<ZTimeStamp> getType() {
        return ZTimeStamp.class;
    }

    @Override
    public int compare(ZTimeStamp obj1, ZTimeStamp obj2) {
        return obj1.compareTo(obj2);
    }
    
}
