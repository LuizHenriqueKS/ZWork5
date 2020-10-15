package br.zul.zwork5.http;

import br.zul.zwork4.exception.ZHttpResponseBodyException;
import br.zul.zwork4.str.ZStr;
import br.zul.zwork5.url.ZUrl;
import br.zul.zwork4.util.ZListTreeMap;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 *
 * @author luizh
 */
public class ZHttpResponse{
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZListTreeMap<String, String> responsePropertyMap;
    private final ZCookieManager cookieManager;
    
    protected HttpURLConnection connection;
    
    protected Integer responseCode;
    protected String responseMessage;
    
    protected ZUrl url;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZHttpResponse(){
        this.responsePropertyMap = new ZListTreeMap<>(ZStr.getCaseInsensitive());
        this.cookieManager = new ZCookieManager();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZHttpResponseBody getResponseBody() throws ZHttpResponseBodyException {
        try {
            return new ZHttpResponseBody(this, connection.getInputStream());
        } catch (IOException ex) {
            try {
                return new ZHttpResponseBody(this, connection.getErrorStream());
            } catch (Exception ex2){
                throw new ZHttpResponseBodyException(ex);
            }
        }
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    public String getLocation() {
        try {
            return getResponsePropertyMap().first("Location");
        }catch (IndexOutOfBoundsException|NullPointerException e){
            return null;
        }
    }
    
    public Long getContentLength(){
        try {
            String contentLength = getResponsePropertyMap().first("Content-Length");
            return Long.valueOf(contentLength);
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e){
            return null;
        }
    }
        
    public String getChartsetName() {
        String charset = "UTF-8";
        if (responsePropertyMap.containsKey("Content-Type")) {
            for (String contentType : responsePropertyMap.get("Content-Type")) {
                if (contentType.contains("charset")) {
                    charset = new ZStr(contentType, false).from("charset=").till(";").asString();
                }
            }
        }
        return charset;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public ZListTreeMap<String, String> getResponsePropertyMap() {
        return responsePropertyMap;
    }

    public ZCookieManager getCookieManager() {
        return cookieManager;
    }

    public ZUrl getUrl() {
        return url;
    }
    
}
