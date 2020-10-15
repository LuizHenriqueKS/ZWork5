package br.zul.zwork5.html.parser;

import br.zul.zwork5.html.ZHtml;
import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork4.str.ZStr;
import br.zul.zwork4.util.ZList;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlParser {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZHtml html;
    private final ZList<ZHtmlNodeParser> parserList;
    
    private boolean blockScript;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlParser(ZHtml html) {
        this.html = html;
        this.parserList = new ZList<>();
        loadParsers();
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void loadParsers(){ //CUIDADO COM A ORDEM QUE É IMPORTANTE
        this.parserList.add(new ZHtmlCommentParser());
        this.parserList.add(new ZHtmlUnknownParser());
        this.parserList.add(new ZHtmlCloseElementParser());                                              
        this.parserList.add(new ZHtmlOpenElementParser());
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void parse(String source){
        ZHtmlParserContext context = new ZHtmlParserContext(html);
        ZStr str = new ZStr(source, false);
        int index;
        while ((index = str.indexOf("<"))!=-1){
            str = toStart(context, str, index);
            if (blockScript) {
                if (!str.startsWith("/script>")) {
                    context.getLastText().setContent(context.getLastText().getContent() + "<");
                    continue;
                }
                blockScript = false;
            }
            ZHtmlNodeParser nodeParser = getNodeParser(str);
            ZHtmlNode lastNode = nodeParser.apply(context, str);
            str = nodeParser.removeOf(str);
            if (isToBlockScript(lastNode)) blockScript = true;
        }
         if (!str.isEmpty()){
            context.addTextByContent(str.toString());
        }
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZStr toStart(ZHtmlParserContext context, ZStr str, int index) {
        String textContent = str.substr(0, index).toString();
        if (!textContent.isEmpty()) {
            context.addTextByContent(textContent);
        }
        return str.substr(index+1).trim();
    }

    private ZHtmlNodeParser getNodeParser(ZStr str) {
        for (ZHtmlNodeParser parser:this.parserList){
            if (parser.isValid(str)){
                return parser;
            }
        }
        return null;
    }

    private boolean isToBlockScript(ZHtmlNode lastNode) {
        if (lastNode!=null&&lastNode.isElement()){
            return lastNode.asElement().getTagName().equalsIgnoreCase("script");
        }
        return false;
    }
    
}
