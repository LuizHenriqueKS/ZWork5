package br.zul.zwork5.html.parser;

import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork4.str.ZStr;

/**
 *
 * @author luiz.silva
 */
interface ZHtmlNodeParser {
    
    public boolean isValid(ZStr str);
    public ZHtmlNode apply(ZHtmlParserContext ctx, ZStr str);
    public ZStr removeOf(ZStr str);
    
}
