package br.zul.zwork5.html;

import br.zul.zwork5.html.parser.ZHtmlParser;
import br.zul.zwork4.exception.ZHtmlParseException;
import br.zul.zwork5.html.element.ZHtmlRoot;
import br.zul.zwork5.html.element.ZHtmlElement;
import br.zul.zwork4.util.ZList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 *
 * @author luiz.silva
 */
public class ZHtml implements ZHtmlNodeQuery {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    final ZHtmlRoot root;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtml(String source) throws ZHtmlParseException{
        this.root = new ZHtmlRoot(){};
        new ZHtmlParser(this).parse(source);
    }
    
    public ZHtml(){
        this.root = new ZHtmlRoot(){};
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZHtmlNode getNode(ZHtmlNodeBy by) {
        return root.getNode(by);
    }

    @Override
    public ZList<ZHtmlNode> listNodes(ZHtmlNodeBy by) {
        return root.listNodes(by);
    }

    @Override
    public boolean hasNodes(ZHtmlNodeBy by) {
        return root.hasNodes(by);
    }

    @Override
    public int countNodes(ZHtmlNodeBy by) {
        return root.countNodes(by);
    }

    @Override
    public Stream<ZHtmlNode> streamNodes(ZHtmlNodeBy by) {
        return root.streamNodes(by);
    }

    @Override
    public ZHtmlElement getElement(ZHtmlElementBy by) throws NoSuchElementException {
        return root.getElement(by);
    }

    @Override
    public ZList<ZHtmlElement> listElements(ZHtmlElementBy by) {
        return root.listElements(by);
    }

    @Override
    public Stream<ZHtmlElement> streamElements(ZHtmlElementBy by) {
        return root.streamElements(by);
    }

    @Override
    public boolean hasElements(ZHtmlElementBy by) {
        return root.hasElements(by);
    }

    @Override
    public int countElements(ZHtmlElementBy by) {
        return root.countElements(by);
    }
    
    @Override
    public String toString(){
        return getRoot().toString();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ESTÁTICOS
    //==========================================================================
    
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZHtmlRoot getRoot() {
        return root;
    }
    
}
