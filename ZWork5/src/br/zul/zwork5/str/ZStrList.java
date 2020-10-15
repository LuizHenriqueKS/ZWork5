package br.zul.zwork5.str;

import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZStrUtils;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Luiz Henrique
 */
public class ZStrList extends ZList<ZStr> {

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZStrList() {
    
    }

    public ZStrList(Collection<? extends ZStr> collection) {
        super(collection);
    }

    public ZStrList(ZStr... array) {
        super(Arrays.asList(array));
    }
    
    public ZStrList(String... array){
        this(ZStrUtils.parseArray(array));
    }
    
    public ZStrList fromStringList(Collection<String> collection){
        String[] array = collection.stream().toArray(String[]::new);
        return new ZStrList(array);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void add(String str){
        add(new ZStr(str));
    }
    
    public void addAll(String... array){
        for (String string: array){
            add(string);
        }
    }
    
    public ZStrList caseSensitive(boolean caseSensitive){
        ZStrList list = new ZStrList();
        for (ZStr str:this){
            list.add(str.caseSensitive(caseSensitive));
        }
        return list;
    }

    public ZList<String> asStringList() {
        ZList<String> result = new ZList<>();
        for (ZStr str:this){
            result.add(str.toString());
        }
        return result;
    }
    
}
