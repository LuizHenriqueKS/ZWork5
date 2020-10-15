package br.zul.zwork5.url;

import br.zul.zwork5.str.ZStr;

/**
 *
 * @author luizh
 */
class ZUrlInfoReplacerFromPath {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZUrlInfo info;
    private ZStr urlStr;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUrlInfoReplacerFromPath(ZUrlInfo info, String urlStr) {
        this.info = info;
        this.urlStr = new ZStr(urlStr);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void apply() {
        resolveRootPattern();
        resolveParentPattern();
        resolveLocalPattern();
        replacePath();
        replaceResource();
        replaceQueries();
        replaceFragment();
        replaceCookies();
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void resolveRootPattern() {
        while (urlStr.startsWith("/")){
            urlStr = urlStr.substr(1);
            info.setPath("/");
        }
    }

    private void resolveParentPattern() {
        while (urlStr.startsWith("../")){
            urlStr = urlStr.substr("../".length());
            info.setPath(new ZStr(info.getPath()).tillLast("/").tillLastWith("/").toString());
        }
    }

    private void resolveLocalPattern() {
        while (urlStr.startsWith("./")){
            urlStr = urlStr.substr("./".length());
        }
    }

    private void replacePath() {
        ZStr pathAndResource = tillPathAndResource();
        if (pathAndResource.isEmpty()) return;
        String newPath = info.getPath()==null?"/":info.getPath();
        if (pathAndResource.containsAny("/")){
            newPath += pathAndResource.tillLastWith("/");
        }
        info.setPath(newPath);
    }

    private void replaceResource() {
        ZStr pathAndResource = tillPathAndResource();
        if (urlStr.containsAny("?", "#", ";")) urlStr = new ZStr("");
        urlStr = urlStr.fromWith("?", "#", ";");
        if (pathAndResource.containsAny("/")){
            info.setResource(pathAndResource.fromLast("/").toString());
        } else if (!pathAndResource.isEmpty()) {
            info.setResource(pathAndResource.toString());
        } else {
            info.setResource(null);
        }
    }

    private void replaceQueries() {
        if (urlStr.startsWith("?")){
            info.setQueries(urlStr.from("?").till("#", ";").toString());
            if (urlStr.containsAny("#", ";")) urlStr = urlStr.fromWith("#", ";");
        } else {
            info.setQueries(null);
        }
    }

    private void replaceFragment() {
        if (urlStr.startsWith("#")){
            info.setFragment(urlStr.from("#").till(";").toString());
            if (urlStr.containsAny(";")) urlStr = urlStr.fromWith(";");
        } else {
            info.setFragment(null);
        }
    }

    private void replaceCookies() {
        if (urlStr.startsWith(";")){
            info.setCookies(urlStr.from(";").toString());
            urlStr = new ZStr("");
        } else {
            info.setCookies(null);
        }
    }

    //==========================================================================
    //MÉTODOS PRIVADOS DE APOIO
    //==========================================================================
    private ZStr tillPathAndResource() {
        return urlStr.till("?","#",";");
    }
    
}
