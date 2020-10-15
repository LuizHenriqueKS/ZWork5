package br.zul.zwork5.html.parser;

import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork4.str.ZStr;
import br.zul.zwork4.str.search.ZStrSearchResult;

/**
 *
 * @author luiz.silva
 */
class ZHtmlCloseElementParser implements ZHtmlNodeParser {

    //==========================================================================
    //CONSTANTES
    //==========================================================================
    
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
            return str.startsWith("/");
        }
        return false;
    }

    @Override
    public ZHtmlNode apply(ZHtmlParserContext ctx, ZStr str) {
        ZStr tagName = str.substr(1, lastPosition).trim();
        ctx.closeElement(tagName.toString());
        return null;
    }

    @Override
    public ZStr removeOf(ZStr str) {
        return str.substr(lastPosition+1);
    }
    
}
