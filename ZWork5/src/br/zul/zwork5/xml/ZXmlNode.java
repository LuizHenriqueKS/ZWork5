package br.zul.zwork5.xml;

import br.zul.zwork5.exception.ZCouldNotBeChangedException;

/**
 *
 * @author Luiz Henrique
 */
public interface ZXmlNode extends ZXmlFinder {
    
    public void normalize();
    public boolean isTag();
    public boolean isText();
    public ZXmlNode getParent();
    public String getTagName();
    public void setTagName(String tagName) throws ZCouldNotBeChangedException;
    public String getTextContent();
    public void setTextContent(String textContent) throws ZCouldNotBeChangedException;
    public ZXmlNodeAttributes getAttributes();
    public ZXmlNodeList getNodeList();

    public void applyChanges();
    public void remove();
    
}
