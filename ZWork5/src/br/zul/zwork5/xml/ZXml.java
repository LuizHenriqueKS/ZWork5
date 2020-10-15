package br.zul.zwork5.xml;

import br.zul.zwork5.exception.ZException;
import br.zul.zwork5.exception.ZRuntimeException;
import br.zul.zwork5.exception.ZXmlException;
import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.log.ZLog;
import br.zul.zwork5.util.ZList;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Luiz Henrique
 */
public class ZXml implements ZXmlFinder {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    protected Document document;
    private ZXmlNode rootElement;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXml() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.newDocument();
            rootElement = createTag("root");
            document.appendChild(((ZXmlTagImpl)rootElement).node);
            //((ZXmlTagImpl)rootElement).node.setPr
        } catch (ParserConfigurationException ex) {
            ZLog.e(ZXml.class).println(ex);
        }
    }
    
    public ZXml(String xmlString){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(new InputSource(new StringReader(xmlString)));
            rootElement = new ZXmlTagImpl(this, null, document.getDocumentElement());
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ZLog.e(ZXml.class).println(ex);
        }
    }
    
    public ZXml(ZFile file) throws ZException{
        this(new ZXmlReader().readDocument(file));
    }
    
    protected ZXml(Document document){
        this.document = document;
        this.rootElement = new ZXmlTagImpl(this, null, document.getDocumentElement());
    }
    
    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZXmlNode getElementById(String id) {
        return rootElement.getElementById(id);
    }

    @Override
    public ZList<ZXmlNode> getElementsByClassName(String className) {
        return rootElement.getElementsByClassName(className);
    }

    @Override
    public ZList<ZXmlNode> getElementsByName(String name) {
        return rootElement.getElementsByName(name);
    }

    @Override
    public ZList<ZXmlNode> getElementsByTagName(String tagName) {
        return rootElement.getElementsByTagName(tagName);
    }

    @Override
    public ZXmlNode querySelector(String query) {
        return rootElement.querySelector(query);
    }

    @Override
    public ZList<ZXmlNode> querySelectorAll(String query) {
        return rootElement.querySelectorAll(query);
    }

    @Override
    public String toString() {
        try {
            return toString(false, false);
        } catch (ZXmlException ex) {
            throw new ZRuntimeException(ex);
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String toString(boolean indent, boolean omitXmlDeclaration) throws ZXmlException {
        ZXmlWriter writer = new ZXmlWriter();
        writer.setIndent(indent);
        writer.setOmitXmlDeclaration(omitXmlDeclaration);
        return writer.convertToString(this);
    }
    
    public ZXmlNode createTag(String tagName){
        return new ZXmlTagImpl(this, null, document.createElement(tagName));
    }
    
    public ZXmlNode createText(String text){
        return new ZXmlTextImpl(null, document.createTextNode(text));
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZXmlNode getRootElement() {
        return rootElement;
    }
    public void setRootElement(ZXmlNode rootElement) {
        this.rootElement = rootElement;
    }
    
}
