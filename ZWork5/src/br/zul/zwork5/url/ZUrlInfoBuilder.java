package br.zul.zwork5.url;

import br.zul.zwork5.exception.ZUrlIsInvalidException;
import br.zul.zwork5.str.ZStr;

/**
 *
 * @author luizh
 */
class ZUrlInfoBuilder {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private ZStr str;
    private ZUrlInfo info;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUrlInfoBuilder(String urlStr) {
        this.str = new ZStr(urlStr);
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZUrlInfo build() throws ZUrlIsInvalidException {
        info = new ZUrlInfo();
        info.setProtocol(extractProtocol());
        info.setUser(extractUser());
        info.setPassword(extractPassword());
        info.setHost(extractHost());
        info.setPort(extractPort());
        info.setPath(extractPath());
        info.setResource(extractResource());
        info.setQueries(extractQueries());
        info.setFragment(extractFragment());
        info.setCookies(extractCookies());
        return info;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private String extractProtocol() {
        String protocol = str.till("://").toString();
        try {
            requireValidProtocol(protocol);
        } catch (ZUrlIsInvalidException e){
            protocol = "http://";
        }
        return protocol;
    }    
    
    private String extractUser() {
       if (hasUserOrPassword()){
           return fromProtocol().till("@").till(":").optString();
       }
       return null;
    }
    
    private String extractPassword(){
        if (hasPassword()){
            return fromProtocol().till("@").from(":").toString();
        }
        return null;
    }
    
    private String extractHost(){
        ZStr hostPort = extractHostPort();
        if (hostPort.containsAny(":")){
            return hostPort.till(":").toString();
        }
        return hostPort.toString();
    }
    
    private Integer extractPort(){
        ZStr hostPort = extractHostPort();
        if (hostPort.containsAny(":")){
            return hostPort.from(":").optInteger();
        }
        return null;
    }

    private String extractPath() {
        ZStr s = extractPathAndResource();
        if (s.isEmpty()){
            return null;
        } else if (s.endsWith("/")){
            return s.toString();
        } else if (s.containsAny("/")) {
            return s.tillLastWith("/").toString();
        }
        return null;
    }
    
    private String extractResource(){
        ZStr s = extractPathAndResource();
        if (s.isEmpty()) {
            return null;
        } else if (!s.containsAny("/")){
            return s.toString();
        } else {
            return s.fromLast("/").toString();
        }
    }
    
    private String extractQueries() {
        ZStr s = fromResource();
        if (s.startsWith("?")){
            return s.from("?").till(";","#").toString();
        }
        return null;
    }

    private String extractFragment() {
        ZStr s = fromQueries();
        if (s.startsWith("#")){
            return s.from("#").till(";").toString();
        }
        return null;
    }

    private String extractCookies() {
        ZStr s = fromFragment();
        if (s.startsWith(";")){
            return s.from(";").toString();
        }
        return null;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS DE APOIO
    //==========================================================================
    private ZStr fromProtocol(){
        if (str.startsWith(info.getProtocol())){
            return str.from(info.getProtocol()+"://");
        }
        return str;
    }
    
    private ZStr extractHostPort(){
        if (hasUserOrPassword()){
            return str.from("@").till("/", "?", ";", "#");
        }
        return fromProtocol().till("/", "?", ";", "#");
    }
    
    private ZStr extractPathAndResource(){
        return fromPort().till("?", ";", "#");
    }
    
    private ZStr fromPassword(){
        if (hasUserOrPassword()){
            return str.from("@");
        } else {
            return fromProtocol();
        }
    }
    
    private ZStr fromPort(){
        if (info.getPort()==null){
            ZStr s = fromPassword();
            if (s.containsAny("/", "?", ";", "#")){
                return fromPassword().fromWith("/", "?", ";", "#");
            } else {
                return new ZStr("");
            }
        } else {
            ZStr s = fromPassword().from(":");
            if (s.containsAny("/","?",";","#")){
                return s.fromWith("/","?",";","#");
            }
            return new ZStr("");
        }
    }
    
    private ZStr fromResource(){
        return fromPort().substr(extractPathAndResource().length());
    }

    private ZStr fromQueries() {    
        ZStr s = fromResource();
        if (s.containsAny("#", ";")){
            return fromResource().fromWith("#", ";");
        }
        return new ZStr("");
    }

    private ZStr fromFragment() {
        ZStr s = fromResource();
        if (s.containsAny(";")){
            return s.fromWith(";");   
        }
        return new ZStr("");
    }

    //==========================================================================
    //MÉTODOS DE VALIDAÇÃO
    //==========================================================================
    public static void requireValidProtocol(String protocol) throws ZUrlIsInvalidException {
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (char c:protocol.toCharArray()){
            if (!alphabet.contains(c+"")){
                throw new ZUrlIsInvalidException();
            }
        }
    }
    
    private boolean hasUserOrPassword(){
        return str.containsAny("@")&&str.from("://").till("/").containsAny("@");
    }
    
    private boolean hasPassword(){
        return str.containsAny("@")&&str.from("://").till("@").containsAny(":");
    }
    
}
