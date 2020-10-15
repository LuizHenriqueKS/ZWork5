package br.zul.zwork5.url;

/**
 *
 * @author luizh
 */
class ZUrlStringfier {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZUrl url;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUrlStringfier(ZUrl url) {
        this.url = url;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String build() {
        StringBuilder builder = new StringBuilder();
        buildProtocol(builder);
        buildUser(builder);
        buildPassword(builder);
        buildHost(builder);
        buildPort(builder);
        buildPath(builder);
        buildResource(builder);
        buildQueries(builder);
        buildFragment(builder);
        buildCookies(builder);
        return builder.toString();
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void buildProtocol(StringBuilder builder) {
        if (url.getProtocol()!=null){
            builder.append(url.getProtocol());
            builder.append("://");
        }
    }

    private void buildUser(StringBuilder builder) {
        if (url.getUser()!=null){
            builder.append(url.getUser());
        }
    }

    private void buildPassword(StringBuilder builder) {
        if (url.getPassword()!=null){
            builder.append(":");
            builder.append(url.getPassword());
        }
    }

    private void buildHost(StringBuilder builder) {
        if (url.getUser()!=null||url.getPassword()!=null){
            builder.append("@");
        }
        builder.append(url.getHost());
    }

    private void buildPort(StringBuilder builder) {
        if (url.getPort()!=null){
            if (!ZUrlPorts.isPortDefault(url.getProtocol(), url.getPort())){
                builder.append(":");
                builder.append(url.getPort());
            }
        }
    }

    private void buildPath(StringBuilder builder) {
        if (url.getPath()!=null){
            builder.append(url.getPath());
        }
    }

    private void buildResource(StringBuilder builder) {
        if (url.getResource()!=null){
            builder.append(url.getResource());
        }
    }

    private void buildQueries(StringBuilder builder) {
        if (url.getQueries()!=null){
            builder.append("?");
            builder.append(url.getQueries());
        }
    }

    private void buildFragment(StringBuilder builder) {
        if (url.getFragment()!=null){
            builder.append("#");
            builder.append(url.getFragment());
        }
    }

    private void buildCookies(StringBuilder builder) {
        if (url.getCookies()!=null){
            builder.append(";");
            builder.append(url.getCookies());
        }
    }
    
}
