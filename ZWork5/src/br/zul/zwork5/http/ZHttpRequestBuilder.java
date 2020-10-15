package br.zul.zwork5.http;

import br.zul.zwork4.exception.ZException;
import br.zul.zwork5.url.ZUrl;
import br.zul.zwork4.util.ZBuilder;
import br.zul.zwork4.util.ZUtil;
import br.zul.zwork4.util.ZValidations;

/**
 *
 * @author luizh
 */
class ZHttpRequestBuilder implements ZBuilder<ZHttpRequest>{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private ZHttp http;
    private String method;
    private ZHttpParameterType parameterType;
    private ZUrl url;
    private String urlStr;
    private Boolean ignoreHandshakeException;
    
    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public void validate() {
        ZValidations.requireNonNull(http);
        ZValidations.requireContent(method);
        ZValidations.requireNonNull(parameterType);
        requireUrl();
    }

    @Override
    public ZHttpRequest implement() {
        ZHttpRequest request = new ZHttpRequest(http);
        request.url = buildUrl();
        request.method = method;
        request.parameterType = parameterType;
        if (ignoreHandshakeException!=null) request.ignoreHandshakeException = ignoreHandshakeException;
        return request;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void requireUrl() {
        if (!ZUtil.hasContent(urlStr)&&url==null){
            if (http.getUrl()==null){
                ZValidations.requireNonNull(url);
            }
        } else if (ZUtil.hasContent(urlStr)&&url!=null) {
            throw new ZException("Informe apenas o url ou o urlStr.");
        }
    }

    private ZUrl buildUrl() {
        if (http.getUrl()!=null){
            return buildUrlBaseInHttp();
        } else {
            return buildMyOwnUrl();
        }
    }

    private ZUrl buildUrlBaseInHttp() {
        ZUrl urlBase = http.getUrl();
        if (url!=null){
            return url;
        } else if (urlStr!=null) {
            return urlBase.getChild(urlStr);
        } else {
            return urlBase;
        }
    }
    
    private ZUrl buildMyOwnUrl() {
        if (url!=null){
            return url;
        } else {
            return new ZUrl(urlStr);
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZUrl getUrl() {
        return url;
    }
    public void setUrl(ZUrl url) {
        this.url = url;
    }

    public ZHttp getHttp() {
        return http;
    }
    public void setHttp(ZHttp http) {
        this.http = http;
    }

    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public ZHttpParameterType getParameterType() {
        return parameterType;
    }
    public void setParameterType(ZHttpParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public String getUrlStr() {
        return urlStr;
    }
    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public Boolean getIgnoreHandshakeException() {
        return ignoreHandshakeException;
    }
    public void setIgnoreHandshakeException(Boolean ignoreHandshakeException) {
        this.ignoreHandshakeException = ignoreHandshakeException;
    }
    
}
