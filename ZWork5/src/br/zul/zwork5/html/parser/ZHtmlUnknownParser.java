package br.zul.zwork5.html.parser;

import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork5.html.element.ZHtmlUnknown;
import br.zul.zwork4.str.ZStr;

/**
 *
 * @author luiz.silva
 */
class ZHtmlUnknownParser implements ZHtmlNodeParser {

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
        return str.startsWith("!");
    }

    @Override
    public ZHtmlNode apply(ZHtmlParserContext ctx, ZStr str) {
        lastPosition = str.indexOf(">");
        return ctx.addNode(new ZHtmlUnknown(str.till(lastPosition).from("!".length()).toString()));
    }

    @Override
    public ZStr removeOf(ZStr str) {
        return str.from(lastPosition+">".length());
    }
    
}
