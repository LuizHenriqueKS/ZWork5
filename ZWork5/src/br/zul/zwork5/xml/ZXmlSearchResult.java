package br.zul.zwork5.xml;

import br.zul.zwork5.util.ZList;

/**
 *
 * @author Luiz Henrique
 */
class ZXmlSearchResult extends ZList<ZXmlNode> {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private boolean breakOnFirst;
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public boolean isBreakOnFirst() {
        return breakOnFirst;
    }
    public void setBreakOnFirst(boolean breakOnFirst) {
        this.breakOnFirst = breakOnFirst;
    }
    
}
