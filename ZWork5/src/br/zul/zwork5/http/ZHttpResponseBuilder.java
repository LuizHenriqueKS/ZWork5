package br.zul.zwork5.http;

import br.zul.zwork4.exception.ZException;
import br.zul.zwork4.exception.ZHttpResponseException;
import br.zul.zwork4.str.ZStr;
import br.zul.zwork5.url.ZUrl;
import br.zul.zwork4.util.ZBuilder;
import br.zul.zwork4.util.ZList;
import br.zul.zwork4.util.ZStrUtils;
import br.zul.zwork4.util.ZUtil;
import br.zul.zwork4.util.ZValidations;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;
import javax.net.ssl.SSLHandshakeException;

/**
 *
 * @author luizh
 */
class ZHttpResponseBuilder implements ZBuilder<ZHttpResponse>{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private HttpURLConnection connection;
    private ZUrl url;
    private ZHttpRequest request;
    
    //==========================================================================
    //CONSTRUTORE
    //==========================================================================
    public ZHttpResponseBuilder() {
        
    }

    //==========================================================================
    //MÉTODOS DO PROCESSO
    //==========================================================================
    @Override
    public void validate() {
        ZValidations.requireNonNull(connection);
        ZValidations.requireNonNull(url);
        ZValidations.requireNonNull(request);
    }

    @Override
    public ZHttpResponse implement() throws ZHttpResponseException{
        try {
            ZHttpResponse response = new ZHttpResponse();
            scheduleOperationTimeout();
            response.responseCode = getResponseCode();
            response.responseMessage = getResponseMessage();
            response.url = new ZUrl(connection.getURL().toString());
            fillWithResponseProperties(response);
            fillWithCookies(response);
            response.connection = connection;
            return response;
        }catch(SSLHandshakeException ex){
            return retry(ex);
        }catch (Exception ex){
            throw new ZHttpResponseException(ex);
        }
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void scheduleOperationTimeout() {
        if (request.operationTimeout!=null){
            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        connection.disconnect();
                    } catch (Exception e) {}
                }
            }, request.operationTimeout);
        }
    }

    private ZHttpResponse retry(SSLHandshakeException ex) {
        if (request.ignoreHandshakeException){
            ZHttpRequestBuilder builder = new ZHttpRequestBuilder();
            builder.setHttp(request.http);
            builder.setMethod(request.method);
            builder.setParameterType(request.parameterType);
            builder.setUrl(url);
            builder.setIgnoreHandshakeException(false);
            return builder.build().send();
        }
        throw new ZHttpResponseException();
    }

    private void fillWithResponseProperties(ZHttpResponse response) {
        connection.getHeaderFields().entrySet().forEach(e->response.getResponsePropertyMap().put(e.getKey(), new ZList<>(e.getValue())));
    }

    private void fillWithCookies(ZHttpResponse response) {
        listCookies(response).forEach(response.getCookieManager()::putCookie);
    }
    
    //==========================================================================
    //GETTERS E SETTERES MODIFICADOS
    //==========================================================================
    private Integer getResponseCode(){
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            ZStr z = new ZStr(e.getMessage(), true);
            z = z.from("Server returned HTTP response code: ").till(" ");
            if (ZStrUtils.isLong(z.asString())) {
                return z.asInteger();
            }
            throw new ZException(e);
        }
    }

    private String getResponseMessage() throws SSLHandshakeException, IOException {
        return connection.getResponseMessage();
    }
    
    private ZList<ZCookie> listCookies(ZHttpResponse response){
        ZList<String> cookieList = response.getResponsePropertyMap().get("Set-Cookie");
        ZList<ZCookie> result = new ZList<>();
        if (ZUtil.hasContent(cookieList)){
            for (String cookieStr : cookieList) {
                ZCookie cookie = new ZCookie(cookieStr);
                result.add(cookie);
            }
        }
        return result;
    }
    
    //==========================================================================
    //GETTERS E SETTERES
    //==========================================================================
    public HttpURLConnection getConnection() {
        return connection;
    }
    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    public ZUrl getUrl() {
        return url;
    }
    public void setUrl(ZUrl url) {
        this.url = url;
    }

    public ZHttpRequest getRequest() {
        return request;
    }
    public void setRequest(ZHttpRequest request) {
        this.request = request;
    }
    
}
