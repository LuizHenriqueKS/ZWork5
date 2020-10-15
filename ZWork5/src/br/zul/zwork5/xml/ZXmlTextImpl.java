package br.zul.zwork5.xml;

import br.zul.zwork5.exception.ZCouldNotBeChangedException;
import br.zul.zwork5.exception.ZException;
import br.zul.zwork5.util.ZList;
import org.w3c.dom.Node;

/**
 *
 * @author Luiz Henrique
 */
public class ZXmlTextImpl implements ZXmlNode {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    protected ZXmlNode parent;
    protected final Node node;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    protected ZXmlTextImpl(ZXmlNode parent, Node node) {
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
        return false;
    }

    @Override
    public boolean isText() {
        return true;
    }

    @Override
    public ZXmlNode getParent() {
        return parent;
    }

    @Override
    public String getTagName() {
        return null;
    }

    @Override
    public String getTextContent() {
        return node.getTextContent();
    }

    @Override
    public void setTextContent(String textContent) {
        node.setTextContent(textContent);
    }

    @Override
    public ZXmlNodeAttributes getAttributes() {
        return null;
    }

    @Override
    public ZXmlNodeList getNodeList() {
        return null;
    }

    @Override
    public ZXmlNode getElementById(String id) {
        return null;
    }

    @Override
    public ZList<ZXmlNode> getElementsByClassName(String className) {
        return null;
    }

    @Override
    public ZList<ZXmlNode> getElementsByName(String name) {
        return null;
    }

    @Override
    public ZList<ZXmlNode> getElementsByTagName(String tagName) {
        return null;
    }

    @Override
    public ZXmlNode querySelector(String query) {
        return null;
    }

    @Override
    public ZList<ZXmlNode> querySelectorAll(String query) {
        return null;
    }

    @Override
    public void setTagName(String tagName) throws ZCouldNotBeChangedException {
        throw new ZCouldNotBeChangedException();
    }

    @Override
    public void applyChanges() {}

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
    
}
