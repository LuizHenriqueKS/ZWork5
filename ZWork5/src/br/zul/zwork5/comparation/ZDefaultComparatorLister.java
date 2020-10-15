package br.zul.zwork5.comparation;

import br.zul.zwork5.comparation.comparator.ZBigDecimalComparator;
import br.zul.zwork5.comparation.comparator.ZDoubleComparator;
import br.zul.zwork5.comparation.comparator.ZFloatComparator;
import br.zul.zwork5.comparation.comparator.ZIntegerComparator;
import br.zul.zwork5.comparation.comparator.ZLongComparator;
import br.zul.zwork5.comparation.comparator.ZStringComparator;
import br.zul.zwork5.comparation.comparator.ZTimestampComparator;
import br.zul.zwork5.util.ZList;

/**
 *
 * @author luizh
 */
class ZDefaultComparatorLister {

    public ZList<ZComparator<?>> list() {
        ZList<ZComparator<?>> result = new ZList<>();
        result.add(new ZBigDecimalComparator());
        result.add(new ZDoubleComparator());
        result.add(new ZFloatComparator());
        result.add(new ZIntegerComparator());
        result.add(new ZLongComparator());
        result.add(new ZStringComparator());
        result.add(new ZTimestampComparator());
        return result;
    }
    
}