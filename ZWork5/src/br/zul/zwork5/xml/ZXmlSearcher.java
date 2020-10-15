package br.zul.zwork5.xml;

import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZValidations;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author Luiz Henrique
 */
class ZXmlSearcher {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZList<Predicate<ZXmlNode>> predicateList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZXmlSearcher() {
        this.predicateList = new ZList<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZXmlSearchResult searchAll(ZXmlNode node){
        validate(node);
        ZXmlSearchResult result = new ZXmlSearchResult();
        try {
            search(node, result);
        } catch (ZXmlSearchBreak e){}
        return result;
    }
    
    public ZXmlNode searchFirst(ZXmlNode node){
        validate(node);
        ZXmlSearchResult result = new ZXmlSearchResult();
        result.setBreakOnFirst(true);
        try {
            search(node, result);
        } catch (ZXmlSearchBreak e){}
        try {
            return result.first();
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void search(ZXmlNode node, ZXmlSearchResult result) throws ZXmlSearchBreak {
        if (check(node)){
            result.add(node);
            if (result.isBreakOnFirst()) throw new ZXmlSearchBreak();
        }
        if (node.isTag()){
            for (ZXmlNode n:node.getNodeList()){
                search(n, result);
            }
        }
    }

    private void validate(ZXmlNode node) {
        Objects.requireNonNull(node);
        ZValidations.requireContent(predicateList);
    }

    private boolean check(ZXmlNode node) {
        return predicateList.stream().anyMatch(p->p.test(node));
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZList<Predicate<ZXmlNode>> getPredicateList() {
        return predicateList;
    }
    
}
