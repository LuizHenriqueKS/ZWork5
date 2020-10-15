package br.zul.zwork5.html;

import br.zul.zwork5.html.element.ZHtmlElement;
import br.zul.zwork4.util.ZList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 *
 * @author luiz.silva
 */
interface ZHtmlNodeQuery {
    
    public ZHtmlNode getNode(ZHtmlNodeBy by) throws NoSuchElementException;
    public ZList<ZHtmlNode> listNodes(ZHtmlNodeBy by);
    public Stream<ZHtmlNode> streamNodes(ZHtmlNodeBy by);
    public boolean hasNodes(ZHtmlNodeBy by);
    public int countNodes(ZHtmlNodeBy by);
    
    public ZHtmlElement getElement(ZHtmlElementBy by) throws NoSuchElementException;
    public ZList<ZHtmlElement> listElements(ZHtmlElementBy by);
    public Stream<ZHtmlElement> streamElements(ZHtmlElementBy by);
    public boolean hasElements(ZHtmlElementBy by);
    public int countElements(ZHtmlElementBy by);
    
}
