package br.zul.zwork5.url;

import br.zul.zwork5.exception.ZUrlIsInvalidException;
import br.zul.zwork5.str.ZStr;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public class ZUrl {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final String protocol;
    private final String user;
    private final String password;
    private final String host;
    private final Integer port;
    private final String path;
    private final String resource;
    private final String queries;
    private final String fragment;
    private final String cookies;
    
    private String originalUrl;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUrl(String url) throws ZUrlIsInvalidException {
        this(new ZUrlInfoBuilder(url).build());
    }

    public ZUrl(ZUrl url) throws ZUrlIsInvalidException {
        this(url.asInfo());
        this.originalUrl = url.originalUrl;
    }

    ZUrl(ZUrlInfo info) throws ZUrlIsInvalidException {
        this.protocol = info.getProtocol();
        this.user = info.getUser();
        this.password = info.getPassword();
        this.host = info.getHost();
        this.port = info.getPort();
        this.path = info.getPath();
        this.resource = info.getResource();
        this.queries = info.getQueries();
        this.fragment = info.getFragment();
        this.cookies = info.getCookies();
        new ZUrlValidator(this).validate();
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString() {
        if (originalUrl!=null){
            return originalUrl;
        }
        return new ZUrlStringfier(this).build();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ZUrl) {
            return toString().equals(other.toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.toString());
        return hash;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public URL asURL() throws MalformedURLException, ZUrlIsInvalidException {
        URL result = new URL(getUrlTillWithFragment().toString());
        return result;
    }
    
    public ZUrl getHostPortUrl() throws ZUrlIsInvalidException{
        ZUrlInfo info = asInfo();
        info.setCookies(null);
        info.setFragment(null);
        info.setQueries(null);
        info.setResource(null);
        info.setPath(null);
        return new ZUrl(info);
    }
    
    public ZUrl getUrlTillWithPath() throws ZUrlIsInvalidException {
        ZUrlInfo info = asInfo();
        info.setCookies(null);
        info.setFragment(null);
        info.setQueries(null);
        info.setResource(null);
        return new ZUrl(info);
    }
    
    public ZUrl getUrlTillWithResource() throws ZUrlIsInvalidException {
        ZUrlInfo info = asInfo();
        info.setCookies(null);
        info.setFragment(null);
        info.setQueries(null);
        return new ZUrl(info);
    }
    
    public ZUrl getUrlTillWithQueries() throws ZUrlIsInvalidException {
        ZUrlInfo info = asInfo();
        info.setCookies(null);
        info.setFragment(null);
        return new ZUrl(info);
    }
    
    public ZUrl getUrlTillWithFragment() throws ZUrlIsInvalidException {
        ZUrlInfo info = asInfo();
        info.setCookies(null);
        return new ZUrl(info);
    }
    
    public ZUrl getAbsoluteUrl() throws ZUrlIsInvalidException {
        ZUrlInfo info = asInfo();
        return new ZUrl(info);
    }
    
    public ZUrl getChild(String urlStr) throws ZUrlIsInvalidException {
        try {
            if (!urlStr.contains(":")){
                throw new ZUrlIsInvalidException();
            }
            ZUrlInfoBuilder.requireValidProtocol(new ZStr(urlStr).till(":").toString());
            return new ZUrl(new ZUrlInfoBuilder(urlStr).build());
        } catch (ZUrlIsInvalidException ex){
            ZUrlInfo info = asInfo();
            ZUrlInfoReplacerFromPath replacer = new ZUrlInfoReplacerFromPath(info, urlStr);
            replacer.apply();
            return new ZUrl(info);
        }
    }
    
    public int getRealPort(){
        if (port==null){
            return ZUrlPorts.getDefaultPortMap().get(protocol);
        }
        return port;
    }
    
    //==========================================================================
    //MÉTODOS DE CONVERSÃO
    //==========================================================================
    private ZUrlInfo asInfo() {
        ZUrlInfo info = new ZUrlInfo();
        info.setProtocol(protocol);
        info.setUser(user);
        info.setPassword(password);
        info.setHost(host);
        info.setPort(port);
        info.setPath(path);
        info.setResource(resource);
        info.setQueries(queries);
        info.setFragment(fragment);
        info.setCookies(cookies);
        return info;
    }
    
    public String asString() {
        return toString();
    }
    
    public ZStr asStr(boolean caseSensitive){
        return new ZStr(asString(), caseSensitive);
    }

    public ZUrlBuilder builder() throws UnsupportedEncodingException {
        ZUrlBuilder builder = new ZUrlBuilder();
        builder.setProtocol(protocol);
        builder.setUser(user);
        builder.setPassword(password);
        builder.setHost(host);
        builder.setPort(port);
        builder.setPath(path);
        builder.setResource(resource);
        builder.setQueries(queries);
        builder.setFragment(fragment);
        builder.setCookies(cookies);
        return builder;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS PARA MAPS
    //==========================================================================    
    public Map<String, String> cookieMap() throws UnsupportedEncodingException{
        return new ZUrlStrToMap(cookies).buildMap();
    }
    
    public Map<String, String> queryMap() throws UnsupportedEncodingException{
        return new ZUrlStrToMap(queries).buildMap();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ESTÁTICOS
    //==========================================================================
    public static String encode(String url) throws UnsupportedEncodingException{
        return URLEncoder.encode(url, "UTF-8");
    }
    
    public static String decode(String url) throws UnsupportedEncodingException{
        return URLDecoder.decode(url, "UTF-8");
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getProtocol() {
        return protocol;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public String getResource() {
        return resource;
    }

    public String getQueries() {
        return queries;
    }

    public String getFragment() {
        return fragment;
    }

    public String getCookies() {
        return cookies;
    }

}
