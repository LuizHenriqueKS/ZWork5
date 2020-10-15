package br.zul.zwork5.http.data;

import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.json.ZJson;
import br.zul.zwork5.json.ZJsonArray;
import br.zul.zwork5.json.ZJsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 *
 * @author luizh
 */
public class ZHttpDataJson implements ZHttpData {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private ZJson json;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHttpDataJson(){
        this.json = new ZJson();
    }
    
    public ZHttpDataJson(String json){
        this.json = new ZJson(json);
    }
    
    public ZHttpDataJson(ZJson json){
        this.json = new ZJson(json.toString());
    }
    
    public ZHttpDataJson(ZJsonObject jsonObject){
        this.json = new ZJson(jsonObject);
    }
    
    public ZHttpDataJson(ZJsonArray jsonArray){
        this.json = new ZJson(jsonArray);
    }
    
    public ZHttpDataJson(ZEntity entity){
        this.json = new ZJson(entity);
    }
    
    public ZHttpDataJson(Object obj){
        this.json = new ZJson(obj);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String getContentType(HttpURLConnection connection) {
        return connection.getRequestProperty("Content-Length")==null?"application/json":null;
    }

    @Override
    public Integer getContentLength(HttpURLConnection connection) {
        return json.toString().getBytes().length;
    }

    @Override
    public void changeRequest(HttpURLConnection connection) {}

    @Override
    public void sendData(HttpURLConnection connection, OutputStream os) throws IOException {
        os.write(json.toString().getBytes());
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================

    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZJson getJson() {
        return json;
    }
    public void setJson(ZJson json) {
        this.json = json;
    }
    
}
