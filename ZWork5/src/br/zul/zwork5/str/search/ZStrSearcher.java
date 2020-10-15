package br.zul.zwork5.str.search;

import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZUtil;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public class ZStrSearcher {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZList<String> patternList;
    private ZList<String> patternListToIgnore;
    private boolean caseSensitive = ZStr.DEFAULT_CASE_SENSITIVE;
    private boolean backwards;
    private boolean compulsive;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZStrSearcher(ZList<String> patternList) {
        this.patternList = patternList;
    }
    
    public ZStrSearcher(String... patternList) {
        this(ZList.fromArray(patternList));
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.patternList);
        hash = 79 * hash + Objects.hashCode(this.patternListToIgnore);
        hash = 79 * hash + (this.caseSensitive ? 1 : 0);
        hash = 79 * hash + (this.backwards ? 1 : 0);
        hash = 79 * hash + (this.compulsive ? 1 : 0);
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
        final ZStrSearcher other = (ZStrSearcher) obj;
        if (this.caseSensitive != other.caseSensitive) {
            return false;
        }
        if (this.backwards != other.backwards) {
            return false;
        }
        if (this.compulsive != other.compulsive) {
            return false;
        }
        if (!Objects.equals(this.patternList, other.patternList)) {
            return false;
        }
        return Objects.equals(this.patternListToIgnore, other.patternListToIgnore);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZStrSearch search(String string, Integer offset){
        validate(string);
        ZStrSearch searcher = new ZStrSearch();
        searcher.string = treat(string);
        searcher.patternList = treat(patternList);
        searcher.patternListToIgnore = treat(patternListToIgnore);
        searcher.offset = offset;
        searcher.compulsive = compulsive;
        //searcher.caseSensitive = caseSensitive;
        searcher.backwards = backwards;
        return searcher;
    }
    
    public ZStrSearch search(String string){
        return search(string, null);
    }
    
    //==========================================================================
    //MÉTODOS PROTEGIDOS
    //==========================================================================
    protected void validate(String string){
        Objects.requireNonNull(patternList);
        Objects.requireNonNull(string);
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private String treat(String string) {
        if (caseSensitive){
            return string;
        } else {
            return string.toLowerCase();
        }
    }

    private ZList<String> treat(ZList<String> list) {
        if (!ZUtil.hasContent(list)) return list;
        return list.map(this::treat);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZList<String> getPatternListToIgnore() {
        return patternListToIgnore;
    }
    public void setPatternListToIgnore(ZList<String> patternListToIgnore) {
        this.patternListToIgnore = patternListToIgnore;
    }

    public ZList<String> getPatternList() {
        return patternList;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public boolean isBackwards() {
        return backwards;
    }
    public void setBackwards(boolean backwards) {
        this.backwards = backwards;
    }

    public boolean isCompulsive() {
        return compulsive;
    }
    public void setCompulsive(boolean compulsive) {
        this.compulsive = compulsive;
    }

}
