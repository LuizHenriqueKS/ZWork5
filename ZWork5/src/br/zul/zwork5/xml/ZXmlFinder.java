package br.zul.zwork5.xml;

import br.zul.zwork5.util.ZList;

/**
 *
 * @author Luiz Henrique
 */
interface ZXmlFinder {
    
    public ZXmlNode getElementById(String id);
    public ZList<ZXmlNode> getElementsByClassName(String className);
    public ZList<ZXmlNode> getElementsByName(String name);
    public ZList<ZXmlNode> getElementsByTagName(String tagName);
    public ZXmlNode querySelector(String query);
    public ZList<ZXmlNode> querySelectorAll(String query);
    
}
