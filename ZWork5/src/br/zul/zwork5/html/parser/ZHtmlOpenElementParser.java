package br.zul.zwork5.html.parser;

import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork5.html.element.ZHtmlElement;
import br.zul.zwork4.str.ZStr;
import br.zul.zwork4.str.search.ZStrSearchResult;
import br.zul.zwork4.util.ZHtmlUtils;

/**
 *
 * @author luiz.silva
 */
class ZHtmlOpenElementParser implements ZHtmlNodeParser {

    //==========================================================================
    //CONSTANTES
    //==========================================================================
    private static final String[] TOKENS = {" ", "\t", "\n", "\r"};
    private static final String[] TOKENS2 = {" ", "\t", "\n", "\r", "="};
    private static final String[] QUOTE_TOKEN = {"'", "\""};
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private Integer lastPosition;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    @Override
    public boolean isValid(ZStr str) {
        ZStrSearchResult result = str.search("<", ">").next();
        if (result!=null&&result.getPattern().equals(">")){
            lastPosition = result.getStartIndex();
            return true;
        }
        return false;
    }

    @Override
    public ZHtmlNode apply(ZHtmlParserContext ctx, ZStr str) {
        ZStr tag = str.substr(0, lastPosition).trim();
        ZHtmlElement element = new ZHtmlElement(getTagName(tag));
        fillWithAttrs(element, tag);
        if (isClosed(tag)){
            element.removeAttribute("/");
            ctx.addNode(element);
        } else {
            ctx.openElement(element);
        }
        return element;
    }

    @Override
    public ZStr removeOf(ZStr str) {
        return str.substr(lastPosition+1);
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private String getTagName(ZStr tag){
        return tag.till(" ").toString();
    }
    
    public boolean isClosed(ZStr tag){
        return tag.endsWith("/");
    }

    private void fillWithAttrs(ZHtmlElement element, ZStr str) {
        int index;
        str = removeTagName(str);
        if (str==null) return;
        while ((index = str.indexOf(TOKENS2))!=-1){
            
            String name = str.substr(0, index).trim().toString();
            if (name.isEmpty()) {
                str = str.from(index+1).trim();
                continue;
            }
            
            str = str.substr(index).trim();
            if (str.startsWith("=")){
                ZStrSearchResult pattern = str.search(QUOTE_TOKEN).next();
                str = str.substr(pattern.getStartIndex()+1);
                int index2 = str.indexOf(pattern.getPattern());
                String value = str.till(index2).toString();
                element.setAttribute(name, ZHtmlUtils.unescapeHtml(value));
                str = str.from(index2+1);
            } else {
                element.setAttribute(name, "");
            }
        }
        if (!str.isEmpty()){
            element.setAttribute(str.toString(), "");
        }
    }

    private ZStr removeTagName(ZStr str) {
        str = str.trim();
        int index = str.indexOf(TOKENS);
        if (index==-1) {
            return null;
        } else {
            return str.from(index).trim();
        }
    }
    
}
