package br.zul.zwork5.html.parser;

import br.zul.zwork5.html.ZHtml;
import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork5.html.element.ZHtmlElement;
import br.zul.zwork5.html.element.ZHtmlText;
import br.zul.zwork4.util.ZList;

/**
 *
 * @author luiz.silva
 */
class ZHtmlParserContext {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZHtml html;
    private final ZList<ZHtmlElement> elementList;
    
    private ZHtmlText lastText;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZHtmlParserContext(ZHtml html) {
        this.html = html;
        this.elementList = new ZList<>();
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void addTextByContent(String content) {
        ZHtmlText text = new ZHtmlText();
        text.setContent(content);
        addNode(text);
        lastText = text;
    }
    
    public void openElement(ZHtmlElement element){
        addNode(element);
        elementList.add(element);
    }
    
    public void closeElement(String tagName){
        if (hasTagName(tagName)){
            while (!elementList.isEmpty()) {
                ZHtmlElement last = elementList.removeLast();
                if (last.getTagName().equalsIgnoreCase(tagName)){
                    break;
                }
            }
        }
    }
    
    private boolean hasTagName(String tagName){
        return elementList.stream().anyMatch(e->e.getTagName().equalsIgnoreCase(tagName));
    }
    
    public ZHtmlNode addNode(ZHtmlNode node){
        if (elementList.isEmpty()){
            html.getRoot().addNode(node);
        } else {
            elementList.last().addNode(node);
        }
        return node;
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZHtmlText getLastText() {
        return lastText;
    }
    
}
