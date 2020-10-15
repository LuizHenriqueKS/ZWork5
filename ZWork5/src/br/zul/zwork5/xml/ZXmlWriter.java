package br.zul.zwork5.xml;

import br.zul.zwork5.exception.ZException;
import br.zul.zwork5.exception.ZXmlException;
import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.io.ZFileEdition;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

/**
 *
 * @author Luiz Henrique
 */
public class ZXmlWriter {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private boolean indent;
    private boolean omitXmlDeclaration;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXmlWriter() {
        
    }
    
    public String convertToString(ZXml xml) throws ZXmlException {
        try {
            StringWriter writer = new StringWriter();
            buildTransformer().transform(new DOMSource(xml.document), new StreamResult(writer));
            String xmlString = writer.getBuffer().toString();
            return xmlString;
        } catch (TransformerException ex) {
            throw new ZXmlException(ex);
        }
    }
    
    public String convertToString(ZXmlNode node) throws ZException{
        try {
            if (node.isText()){
                return node.getTextContent();
            } else {
                Node n = ((ZXmlTagImpl)node).node;
                StringWriter writer = new StringWriter();
                buildTransformer().transform(new DOMSource(n), new StreamResult(writer));
                String xmlString = writer.getBuffer().toString();
                return xmlString;
            }
        } catch (TransformerException ex) {
            throw new ZException(ex);
        }
    }

    public void write(ZXml xml, ZFile file) throws ZException {
        try {
            ZFileEdition edition = file.editFile(false);
            buildTransformer().transform(new DOMSource(xml.document), new StreamResult(edition.getOutputStream()));
            edition.commit();
        } catch (TransformerException ex) {
            throw new ZException(ex);
        }
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private Transformer buildTransformer() throws ZXmlException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            setOutputProperty(transformer);
            return transformer;
        } catch (TransformerException e) {
            throw new ZXmlException(e);
        }
    }
    
    private void setOutputProperty(Transformer transformer){
        if (indent) transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        if (omitXmlDeclaration) transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public boolean isIndent() {
        return indent;
    }
    public void setIndent(boolean indent) {
        this.indent = indent;
    }

    public boolean isOmitXmlDeclaration() {
        return omitXmlDeclaration;
    }
    public void setOmitXmlDeclaration(boolean omitXmlDeclaration) {
        this.omitXmlDeclaration = omitXmlDeclaration;
    }
    
}
