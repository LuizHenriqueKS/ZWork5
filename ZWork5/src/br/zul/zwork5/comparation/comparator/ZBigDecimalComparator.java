package br.zul.zwork5.comparation.comparator;

import br.zul.zwork5.comparation.ZComparator;
import java.math.BigDecimal;

/**
 *
 * @author luizh
 */
public class ZBigDecimalComparator implements ZComparator<BigDecimal> {

    @Override
    public Class<BigDecimal> getType() {
        return BigDecimal.class;
    }

    @Override
    public int compare(BigDecimal obj1, BigDecimal obj2) {
        return obj1.compareTo(obj2);
    }
    
}
