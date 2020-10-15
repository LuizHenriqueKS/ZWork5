package br.zul.zwork5.html.element;

import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork4.util.ZHtmlUtils;
import br.zul.zwork4.util.ZList;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlComment extends ZHtmlNode {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private String content;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlComment(String content) {
        Objects.requireNonNull(content);
        this.content = content;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString() {
        return "<!-- " + content + " -->";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.content);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZHtmlComment other = (ZHtmlComment) obj;
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        return true;
    }
    
    @Override
    public ZList<ZHtmlNode> listNodes() {
        return new ZList<>();
    }

    @Override
    public Stream<ZHtmlNode> streamNodes() {
        return listNodes().stream();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getText(){
        return ZHtmlUtils.unescapeHtml(content);
    }
    public void setText(String text){
        this.content = ZHtmlUtils.escapeHtml(content);
    }
    
}
