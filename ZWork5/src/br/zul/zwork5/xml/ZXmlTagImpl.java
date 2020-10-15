package br.zul.zwork5.xml;

import br.zul.zwork5.exception.ZCouldNotBeChangedException;
import br.zul.zwork5.exception.ZException;
import br.zul.zwork5.util.ZList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Luiz Henrique
 */
class ZXmlTagImpl implements ZXmlNode {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    protected final ZXml xml;
    protected final Node node;
    protected ZXmlNode parent;
    private ZXmlNodeList nodeList;
    private ZXmlNodeAttributes attrs;
    private String tagName;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    protected ZXmlTagImpl(ZXml xml, ZXmlNode parent, Node node) {
        this.xml = xml;
        this.parent = parent;
        this.node = node;
    }

    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public void normalize() {
        node.normalize();
    }

    @Override
    public boolean isTag() {
        return true;
    }

    @Override
    public ZXmlNode getParent() {
        return parent;
    }

    @Override
    public String getTagName() {
        return node.getNodeName();
    }

    @Override
    public String getTextContent() {
        return node.getTextContent();
    }

    @Override
    public boolean isText() {
        return false;
    }

    @Override
    public ZXmlNodeAttributes getAttributes() {
        if (attrs==null){
            attrs = new ZXmlNodeAttributes(this, node.getAttributes());
        }
        return attrs;
    }

    @Override
    public ZXmlNodeList getNodeList() {
        if (nodeList==null){
            List<ZXmlNode> nl = new ArrayList<>();
            if (node.hasChildNodes()){
                for (int i=0;i<node.getChildNodes().getLength();i++){
                    Node item = node.getChildNodes().item(i);
                    nl.add(parseNode(item));
                }
            }
            nodeList = new ZXmlNodeList(this, nl);
        }
        return nodeList;
    }

    @Override
    public ZXmlNode getElementById(String id) {
        try {
            ZXmlSearcher searcher = new ZXmlSearcher();
            searcher.getPredicateList().add(t->{
                return t.isTag()&&id.equals(t.getAttributes().get("id"));
            });
            return searcher.searchFirst(this);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public ZList<ZXmlNode> getElementsByClassName(String className) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ZList<ZXmlNode> getElementsByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ZList<ZXmlNode> getElementsByTagName(String tagName) {
         try {
            ZXmlSearcher searcher = new ZXmlSearcher();
            searcher.getPredicateList().add(t->{
                return t.isTag()&&tagName.equals(t.getTagName());
            });
            return searcher.searchAll(this);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public ZXmlNode querySelector(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ZList<ZXmlNode> querySelectorAll(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private ZXmlNode parseNode(Node node){
        if (node.getNodeType()==Element.ELEMENT_NODE){
            return new ZXmlTagImpl(xml, this, node);
        } else {
            return new ZXmlTextImpl(this, node);
        }
    }

    @Override
    public void setTextContent(String textContent) throws ZCouldNotBeChangedException {
        throw new ZCouldNotBeChangedException();
    }

    @Override
    public void setTagName(String tagName) throws ZCouldNotBeChangedException {
        this.tagName = tagName;
        xml.document.renameNode(node, null, tagName);
    }

    @Override
    public void applyChanges() {
        refreshChildParents();
        clearChilds();
        refillChilds();
        clearAttrs();
        refillAttrs();
    }

    @Override
    public void remove() {
        if (getParent()!=null){
            getParent().getNodeList().remove(this);
            parent = null;
        }
    }

    @Override
    public String toString() {
        try {
            ZXmlWriter writer = new ZXmlWriter();
            writer.setIndent(true);
            writer.setOmitXmlDeclaration(true);
            return writer.convertToString(this);
        } catch (ZException ex) {
            return super.toString();
        }
    }

    private ZList<Node> listNodes() {
        ZList<Node> result = new ZList<>();
        for (int i=0;i<node.getChildNodes().getLength();i++){
            result.add(node.getChildNodes().item(i));
        }
        return result;
    }
    
    private Node natToNod(ZXmlNode node){
        if (node instanceof ZXmlTagImpl){
            return ((ZXmlTagImpl)node).node;
        } else if (node instanceof ZXmlTextImpl) {
            return ((ZXmlTextImpl)node).node;
        } else {
            return null;
        }
    }

    private void refreshChildParents() {
        getNodeList().forEach(n->{
            if (n instanceof ZXmlTagImpl){
                ((ZXmlTagImpl)n).parent = this;
            } else if (n instanceof ZXmlTextImpl){
                ((ZXmlTextImpl)n).parent = this;
            }
        });
    }
    
    private void clearChilds(){
        ZList<Node> nl = listNodes();
        nl.forEach(node::removeChild);
    }
    
    private void refillChilds() {
        getNodeList().map(this::natToNod).forEach(node::appendChild);
    }
    
    private void clearAttrs() {
        for (int i=0;i<node.getAttributes().getLength();i++){
            String name = node.getAttributes().item(i).getNodeName();
            node.getAttributes().removeNamedItem(name);
        }
    }

    private void refillAttrs() {
        Element el = (Element)node;
        for (Entry<String, String> e:getAttributes().entrySet()){
            el.setAttribute(e.getKey(), e.getValue());
        }
    }
    
}
