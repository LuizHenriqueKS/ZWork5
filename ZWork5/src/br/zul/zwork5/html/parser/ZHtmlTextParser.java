package br.zul.zwork5.html.parser;

import br.zul.zwork5.html.ZHtmlNode;
import br.zul.zwork4.str.ZStr;
import br.zul.zwork4.str.search.ZStrSearchResult;

/**
 *
 * @author luiz.silva
 */
class ZHtmlTextParser implements ZHtmlNodeParser {

    //==========================================================================
    //CONSTANTES
    //==========================================================================
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private int lastPosition;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    @Override
    public boolean isValid(ZStr str) {
        return false;
    }

    @Override
    public ZHtmlNode apply(ZHtmlParserContext ctx, ZStr str) {
        findLastPosition(str);
        insertTextContent(ctx, str);
        return null;
    }

    @Override
    public ZStr removeOf(ZStr str) {
        return str.substr(lastPosition+1);
    }

    private void findLastPosition(ZStr str) {
        lastPosition = str.indexOf("<");
        if (lastPosition==-1) lastPosition = str.length();
    }

    private void insertTextContent(ZHtmlParserContext ctx, ZStr str) {
        throw new RuntimeException(); //ENCONTRAR O ULTIMO NODE E VERIFICAR SE É DE TEXTO
        //SE FOR DE TEXTO SÓ ADICIONA O CONTEUDO NESSE NODE
        //SE NÃO FOR TEXTO CRIA UM NODE DE TEXTO COM ESSE CONTEUDO
    }
    
}
