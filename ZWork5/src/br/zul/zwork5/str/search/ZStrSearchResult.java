package br.zul.zwork5.str.search;

/**
 *
 * @author Luiz Henrique
 */
public class ZStrSearchResult {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    protected int startIndex;
    protected int endIndex;
    protected String pattern;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String substr(String string){
        return string.substring(startIndex, endIndex);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public int getStartIndex() {
        return startIndex;
    }
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    
}
