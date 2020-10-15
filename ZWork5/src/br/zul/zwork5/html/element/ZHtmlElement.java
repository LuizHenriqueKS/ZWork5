package br.zul.zwork5.html.element;

import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork4.str.ZStr;
import br.zul.zwork4.util.ZHtmlUtils;
import br.zul.zwork4.util.ZList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlElement extends ZHtmlNode {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZList<ZHtmlNode> childList;
    private final Map<String, String> attrMap;
    private String tagName;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlElement(String tagName){
        this.attrMap = new LinkedHashMap<>();
        this.tagName = tagName;
        this.childList = new ZList<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString(){
        return new ZHtmlElementStringifier(this).stringify();
    }
    
    @Override
    public ZList<ZHtmlNode> listNodes() {
        return new ZList<>(childList);
    }

    @Override
    public Stream<ZHtmlNode> streamNodes() {
        return childList.stream();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String getOnlyText(){
        StringBuilder builder = new StringBuilder();
        streamNodes(n->n.isText()).forEach(n->{
            if (builder.length()>0){
                builder.append(" ");
            }
            builder.append(n.asText().getText());
        });
        return builder.toString();
    }
    
    public void addNode(ZHtmlNode node){
        childList.add(node);
    }
    
    public boolean hasClass(String className){
        return listClassNames().stream()
                               .anyMatch(s->s.equalsIgnoreCase(className));
    }
    
    public boolean hasAttribute(String attributeName){
        return getAttribute(attributeName)!=null;
    }
    
    private ZList<String> listClassNames() {
        ZList<String> result = new ZList<>();
        String cls = getAttribute("class");
        if (cls!=null){
            ZStr z = new ZStr(cls);
            z.split(" ").map(s->s.toString()).forEach(result::add);
        }
        return result;
    }
    
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public Map<String, String> attributeMap(){
        return attrMap;
    }
    
    public Set<String> attributeNameSet(){
        return attrMap.keySet();
    }
    
    public String getAttribute(String name){
        return ZHtmlUtils.unescapeHtml(attrMap.get(name));
    }
    public void setAttribute(String name, String value){
        if (value==null){
            removeAttribute(name);
        } else {
            attrMap.put(name, ZHtmlUtils.escapeHtml(value));
        }
    }
    public void removeAttribute(String name){
        attrMap.remove(name);
    }
    
    public String getId(){
        return getAttribute("id");
    }
    public void setId(String id){
        setAttribute("id", id);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName){
        this.tagName = tagName;
    }

}
