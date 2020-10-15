package br.zul.zwork5.html.element;

import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork4.util.ZList;
import java.util.stream.Stream;

/**
 *
 * @author luiz.silva
 */
public abstract class ZHtmlRoot extends ZHtmlNode {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZList<ZHtmlNode> nodeList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlRoot(){
        this.nodeList = new ZList<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZList<ZHtmlNode> listNodes(){
        return new ZList<>(nodeList);
    }

    @Override
    public Stream<ZHtmlNode> streamNodes() {
        return nodeList.stream();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void addNode(ZHtmlNode node){
        nodeList.add(node);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    
}
