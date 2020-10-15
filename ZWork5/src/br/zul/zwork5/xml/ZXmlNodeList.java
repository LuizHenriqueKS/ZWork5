package br.zul.zwork5.xml;

import br.zul.zwork5.util.ZList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 *
 * @author Luiz Henrique
 */
public class ZXmlNodeList extends ZList<ZXmlNode> {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZXmlNode parent;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXmlNodeList(ZXmlNode parent) {
        this.parent = parent;
    }
    
    public ZXmlNodeList(ZXmlNode parent, Collection<ZXmlNode> collection){
        super(collection);
        this.parent = parent;
    }
    
    public ZXmlNodeList() {
        this.parent = null;
    }

    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZList<ZXmlNode> addAll(ZXmlNode... items) {
        super.addAll(items);
        applyChanges();
        return this;
    }

    @Override
    public void sort(Comparator<? super ZXmlNode> c) {
        super.sort(c);
        applyChanges();
    }

    @Override
    public void replaceAll(UnaryOperator<ZXmlNode> operator) {
        super.replaceAll(operator); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeIf(Predicate<? super ZXmlNode> filter) {
        boolean result = super.removeIf(filter); 
        applyChanges();
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = super.retainAll(c);
        applyChanges();
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = super.removeAll(c);
        applyChanges();
        return result;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        applyChanges();
    }

    @Override
    public boolean addAll(int index, Collection<? extends ZXmlNode> c) {
        boolean result = super.addAll(index, c);
        applyChanges();
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends ZXmlNode> c) {
        boolean result = super.addAll(c);
        applyChanges();
        return result;
    }

    @Override
    public void clear() {
        super.clear();
        applyChanges();
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        applyChanges();
        return result;
    }

    @Override
    public ZXmlNode remove(int index) {
        ZXmlNode result = super.remove(index);
        applyChanges();
        return result;
    }

    @Override
    public void add(int index, ZXmlNode element) {
        super.add(index, element);
        applyChanges();
    }

    @Override
    public boolean add(ZXmlNode e) {
        boolean result = super.add(e);
        applyChanges();
        return result;
    }

    @Override
    public ZXmlNode set(int index, ZXmlNode element) {
        ZXmlNode result = super.set(index, element);
        applyChanges();
        return result;
    }

    @Override
    public void trimToSize() {
        super.trimToSize();
        applyChanges();
    }

    private void applyChanges() {
        if (parent!=null){
            parent.applyChanges();
        }
    }
    
}

