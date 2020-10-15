package br.zul.zwork5.html;

import br.zul.zwork5.html.element.ZHtmlElement;
import br.zul.zwork5.html.element.ZHtmlText;
import br.zul.zwork4.util.ZList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 *
 * @author luiz.silva
 */
public abstract class ZHtmlNode implements ZHtmlNodeQuery {
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract ZList<ZHtmlNode> listNodes();
    public abstract Stream<ZHtmlNode> streamNodes();
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        streamNodes().map(node->node.toString())
                     .forEach(content->builder.append(content));
        return builder.toString();
    }
    
    @Override
    public Stream<ZHtmlElement> streamElements(ZHtmlElementBy by){
        return streamAllElements().filter(by);
    }

    @Override
    public Stream<ZHtmlNode> streamNodes(ZHtmlNodeBy by){
        return streamAllNodes().filter(by::filter);
    }
    
    @Override
    public int countNodes(ZHtmlNodeBy by){
        return (int)listAllNodes().stream()
                                     .filter(by::filter)
                                     .count();
    }

    @Override
    public boolean hasNodes(ZHtmlNodeBy by){
        return streamAllNodes().anyMatch(by::filter);
    }

    @Override
    public ZList<ZHtmlNode> listNodes(ZHtmlNodeBy by){
        return ZHtmlNode.this.streamNodes(by).collect(ZList.getCollector());
    }

    @Override
    public ZHtmlNode getNode(ZHtmlNodeBy by) throws NoSuchElementException{
        return ZHtmlNode.this.streamNodes(by).findFirst()
                                 .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public int countElements(ZHtmlElementBy by){
        return (int)streamElements(by).count();
    }

    @Override
    public boolean hasElements(ZHtmlElementBy by){
        return streamAllElements().anyMatch(by);
    }

    @Override
    public ZList<ZHtmlElement> listElements(ZHtmlElementBy by){
        return streamElements(by).collect(ZList.getCollector());
    }

    @Override
    public ZHtmlElement getElement(ZHtmlElementBy by) throws NoSuchElementException{
        return streamElements(by).findFirst()
                             .orElseThrow(NoSuchElementException::new);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZList<ZHtmlNode> listAllNodes(){
        return streamAllNodes().collect(ZList.getCollector());
    }
    
    public Stream<ZHtmlNode> streamAllNodes(){
        return new ZHtmlNodeAllStreamer(this).stream();
    }
    
    public ZList<ZHtmlElement> listAllElements(){
        return streamAllElements().collect(ZList.getCollector());
    }
    
    public Stream<ZHtmlElement> streamAllElements(){
        return streamAllNodes().filter(e->e instanceof ZHtmlElement)
                                  .map(e->(ZHtmlElement)e);
    }
    
    public ZList<ZHtmlElement> listElements(){
        return streamElements().collect(ZList.getCollector());
    }
    
    public Stream<ZHtmlElement> streamElements(){
        return streamNodes().filter(e->e instanceof ZHtmlElement)
                               .map(e->(ZHtmlElement)e);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS DE CONVERSÃO
    //==========================================================================
    public ZHtmlElement asElement(){
        return (ZHtmlElement)this;
    }
    
    public ZHtmlText asText(){
        return (ZHtmlText)this;
    }
    
    public boolean isElement(){
        return this instanceof ZHtmlElement;
    }
    
    public boolean isText(){
        return this instanceof ZHtmlText;
    }
    
}
