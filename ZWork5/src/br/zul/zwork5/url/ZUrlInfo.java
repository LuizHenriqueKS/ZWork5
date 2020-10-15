package br.zul.zwork5.url;

/**
 *
 * @author luizh
 */
class ZUrlInfo {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
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
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================

    
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

    public String getQueries() {
        return queries;
    }
    public void setQueries(String queries) {
        this.queries = queries;
    }

    public String getFragment() {
        return fragment;
    }
    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public String getCookies() {
        return cookies;
    }
    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
    
}
