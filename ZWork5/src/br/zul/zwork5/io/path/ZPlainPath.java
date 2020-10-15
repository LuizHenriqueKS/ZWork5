package br.zul.zwork5.io.path;

import br.zul.zwork4.str.ZStr;
import br.zul.zwork4.util.ZList;
import br.zul.zwork4.util.ZUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Luiz Henrique
 */
public class ZPlainPath implements ZPath {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    protected final ZList<String> list;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZPlainPath(Collection<String> pathList) {
        this.list = new ZList<>();
        init(pathList);
    }
    
    public ZPlainPath(String... path){
        this(Arrays.asList(path));
    }
    
    public ZPlainPath(String path){
        this(Arrays.asList(path));
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(Collection<String> pathList) {
        String path = pathList.stream().reduce((a,b)->a+"/"+b).orElse("");
        this.list.addAll(new ZStr(path).split("/").asStringList());
        this.list.removeIf((str)->!ZUtil.hasContent(str));
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public int firstIndex() {
        return this.list.firstIndex();
    }

    @Override
    public int lastIndex() {
        return this.list.lastIndex();
    }

    @Override
    public String get(int index) throws IndexOutOfBoundsException {
        return this.list.get(index);
    }

    @Override
    public String set(int index, String value) {
        return list.set(index, value);
    }

    @Override
    public String getExtension() {
        ZStr name = new ZStr(getName());
        if (name.containsAny(".")){
            return name.from(".").toString();
        } else {
            return null;
        }
    }

    @Override
    public ZPlainPath fromIndex(int index) {
        ZList<String> tmpList = list.copy();
        tmpList.fromIndex(index);
        return new ZPlainPath(tmpList);
    }

    @Override
    public ZPlainPath tillIndex(int index) {
        ZList<String> tmpList = list.copy();
        tmpList.tillIndex(index);
        return new ZPlainPath(tmpList);
    }

    @Override
    public ZPlainPath getChild(String path) {
        ZList<String> tmpList = this.list.copy();
        tmpList.add(path);
        return new ZPlainPath(tmpList);
    }

    @Override
    public ZPlainPath getParent() {
        ZList<String> tmpList = list.copy();
        tmpList.removeLast();
        return new ZPlainPath(tmpList);
    }

    @Override
    public String getName() {
        return last();
    }

    @Override
    public Iterator<String> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return list.stream().reduce((a,b)->a+"/"+b).orElse("/");
    }

    @Override
    public ZPlainPath removeFirst() {
        ZList<String> tmpList = this.list.copy();
        tmpList.removeFirst();
        return new ZPlainPath(tmpList);
    }

    @Override
    public ZPlainPath removeLast() {
        ZList<String> tmpList = this.list.copy();
        tmpList.removeLast();
        return new ZPlainPath(tmpList);
    }
    
}
