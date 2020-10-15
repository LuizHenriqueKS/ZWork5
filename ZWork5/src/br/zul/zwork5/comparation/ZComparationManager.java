package br.zul.zwork5.comparation;

import br.zul.zwork5.exception.ZComparationException;
import br.zul.zwork5.exception.ZComparatorNotFoundException;
import br.zul.zwork5.util.ZList;

/**
 *
 * @author luizh
 */
public class ZComparationManager {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZList<ZComparator<?>> comparatorList;
    
    private static ZComparationManager instance;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZComparationManager() {
        comparatorList = new ZList<>();
        loadDefault();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public int compare(Object obj1, Object obj2, Class<?> type) throws ZComparatorNotFoundException, ZComparationException{
        ZComparator<Object> comparator = (ZComparator<Object>)getComparator(type);
        if (comparator==null) throw new ZComparatorNotFoundException(type);
        return comparator.compare(obj1, obj2);
    }
    
    public int compare(Object obj1, Object obj2) throws ZComparatorNotFoundException, ZComparationException{
        if (obj1==null&&obj2==null){
            return 0;
        } else if (obj1==null&&obj2!=null){
            return -1;
        } else if (obj1!=null&&obj2==null){
            return 1;
        } else {
            Class<?> type = obj1.getClass();
            return compare(obj1, obj2, type);
        }
    }
    
    private void loadDefault(){
        comparatorList.addAll(new ZDefaultComparatorLister().list());
    }
    
    public static ZComparationManager getInstance(){
        if (instance == null){
            instance = new ZComparationManager();
        }
        return instance;
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public ZList<ZComparator<?>> getComparatorList() {
        return comparatorList;
    }

    private ZComparator<?> getComparator(Class<?> type) {
        return new ZComparatorGetter(this, type).get();
    }
    
}
