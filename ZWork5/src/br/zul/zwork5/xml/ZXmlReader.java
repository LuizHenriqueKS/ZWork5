package br.zul.zwork5.xml;

import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.exception.ZException;
import br.zul.zwork5.log.ZLog;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Luiz Henrique
 */
public class ZXmlReader {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXmlReader() {
    }
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    protected Document readDocument(ZFile file) throws ZException{
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            try (InputStream is = file.getInputStream()){
                return dBuilder.parse(is);
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            ZLog.e(ZXmlReader.class).println(ex);
            throw new ZException(ex);
        }
    }
    
    public ZXml readXml(ZFile file) throws ZException{
        return new ZXml(readDocument(file));
    }
    
}
