package br.zul.zwork5.xml;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Luiz Henrique
 */
public class ZXmlNodeAttributes extends LinkedHashMap<String, String>{
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private ZXmlNode parent;
    private final boolean applyChanges;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXmlNodeAttributes(){
        this.applyChanges = true;
    }
    
    public ZXmlNodeAttributes(ZXmlNode parent, NamedNodeMap attributes) {
        this.parent = parent;
        for (int i=0;i<attributes.getLength();i++){
            Node item = attributes.item(i);
            super.put(item.getNodeName(), item.getNodeValue());
        }
        this.applyChanges = true;
    }
    
    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public void replaceAll(BiFunction<? super String, ? super String, ? extends String> function) {
        super.replaceAll(function);
        applyChanges();
    }

    @Override
    protected boolean removeEldestEntry(Entry<String, String> eldest) {
        boolean result = super.removeEldestEntry(eldest);
        applyChanges();
        return result;
    }

    @Override
    public String merge(String key, String value, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        String result = super.merge(key, value, remappingFunction);
        applyChanges();
        return result;
    }

    @Override
    public String replace(String key, String value) {
        String result = super.replace(key, value);
        applyChanges();
        return result;
    }

    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        boolean result = super.replace(key, oldValue, newValue);
        applyChanges();
        return result;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean result = super.remove(key, value);
        applyChanges();
        return result;
    }

    @Override
    public String putIfAbsent(String key, String value) {
        String result = super.putIfAbsent(key, value); 
        applyChanges();
        return result;
    }

    @Override
    public String remove(Object key) {
        String result = super.remove(key);
        applyChanges();
        return result;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        super.putAll(m);
        applyChanges();
    }

    @Override
    public String put(String key, String value) {
        String result = super.put(key, value); 
        applyChanges();
        return result;
    }

    private void applyChanges() {
        if (parent!=null&&applyChanges){
            parent.applyChanges();
        }
    }
    
}
