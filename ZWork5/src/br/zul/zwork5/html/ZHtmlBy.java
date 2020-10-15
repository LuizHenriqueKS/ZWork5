package br.zul.zwork5.html;

import br.zul.zwork4.util.ZStrUtils;
import java.util.Objects;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlBy {

    public static ZHtmlElementBy id(String id){
        return (element)->Objects.equals(id, element.getId());
    }
    
    public static ZHtmlElementBy tagName(String tagName){
        return (element)->ZStrUtils.equalsIgnoreCase(element.getTagName(), tagName);
    }
    
    public static ZHtmlElementBy className(String className){
        return (element)->element.hasClass(className);
    }
    
}
