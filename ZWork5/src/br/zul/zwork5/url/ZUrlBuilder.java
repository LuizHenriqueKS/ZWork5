package br.zul.zwork5.url;

import br.zul.zwork5.exception.ZUrlIsInvalidException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author luizh
 */
public class ZUrlBuilder {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private String protocol;
    private String user;
    private String password;
    private String host;
    private Integer port;
    private String path;
    private String resource;
    private String queries;
    private String fragment;
    private String cookies;
    
    private final Map<String, String> queryMap;
    private final Map<String, String> cookieMap;
    
    private boolean useQueryMap;
    private boolean useCookieMap;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUrlBuilder() throws UnsupportedEncodingException {
        this.queryMap = new LinkedHashMap<>(new ZUrlStrToMap(queries).buildMap());
        this.cookieMap = new LinkedHashMap<>(new ZUrlStrToMap(cookies).buildMap());
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZUrl build() throws ZUrlIsInvalidException, UnsupportedEncodingException{
        validate();
        return implement();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    private void validate() {
        
    }
    
    private ZUrl implement() throws ZUrlIsInvalidException, UnsupportedEncodingException {
        ZUrlInfo info = new ZUrlInfo();
        info.setProtocol(protocol);
        info.setUser(user);
        info.setPassword(password);
        info.setHost(host);
        info.setPort(port);
        info.setPath(path);
        info.setResource(resource);
        info.setQueries(buildQueries());
        info.setFragment(fragment);
        info.setCookies(buildCookies());
        return new ZUrl(info);
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private String buildQueries() throws UnsupportedEncodingException{
        if (useQueryMap){
            return new ZUrlMapToStr(queryMap).buildString();
        } else {
            return queries;
        }
    }
    
    private String buildCookies() throws UnsupportedEncodingException{
        if (useCookieMap){
            return new ZUrlMapToStr(cookieMap).buildString();
        } else {
            return cookies;
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODICADOS
    //==========================================================================
    public Map<String, String> getQueryMap(){
        useQueryMap = true;
        return queryMap;
    }
    
    public Map<String, String> getCookieMap(){
        useCookieMap = true;
        return cookieMap;
    }

    public String getQueries() {
        return queries;
    }
    public void setQueries(String queries) {
        useQueryMap = false;
        this.queries = queries;
    }

    public String getCookies() {
        return cookies;
    }
    public void setCookies(String cookies) {
        useCookieMap = false;
        this.cookies = cookies;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getFragment() {
        return fragment;
    }
    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

}
