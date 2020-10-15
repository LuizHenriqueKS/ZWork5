package br.zul.zwork5.http.data;

import br.zul.zwork5.entity.ZEntity;
import br.zul.zwork5.entity.ZEntityHandler;
import br.zul.zwork5.reflection.ZObjHandler;
import br.zul.zwork5.url.ZUrlMapToStr;
import br.zul.zwork5.url.ZUrlStrToMap;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author luizh
 */
public class ZHttpDataParams implements ZHttpData {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private Map<String, String> map;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHttpDataParams(){
        this.map = new LinkedHashMap<>();
    }
    
    public ZHttpDataParams(String data){
        this.map = new ZUrlStrToMap(data).buildMap();
    }
    
    public ZHttpDataParams(Map<String, String> map){
        this.map = new LinkedHashMap<>(map);
    }
    
    public ZHttpDataParams(ZEntity entity){
        this.map = new ZEntityHandler(entity).getStringVarMap();
        init();
    }
    
    public ZHttpDataParams(Object obj){
        this.map = new ZObjHandler(obj).getStringVarMap();
        init();
    }
    
    //==========================================================================
    //MÉTODOS DE INICIALIZAÇÃO
    //==========================================================================
    private void init(){
        this.map.remove("class");
    }
    
    private void removeNullValuesOf(Map<String, String> map) {
        List<String> keyListToRemove = new ArrayList<>();
        map.entrySet().stream()
                           .filter(e->e.getValue()==null)
                           .map(e->e.getKey())
                           .forEach(keyListToRemove::add);
        keyListToRemove.forEach(map::remove);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String getContentType(HttpURLConnection connection) {
        return connection.getRequestProperty("Content-Length")==null?"application/x-www-form-urlencoded":null;
    }

    @Override
    public Integer getContentLength(HttpURLConnection connection) {
        return getData().getBytes().length;
    }

    @Override
    public void changeRequest(HttpURLConnection connection) {}

    @Override
    public void sendData(HttpURLConnection connection, OutputStream os) throws IOException {
        os.write(getData().getBytes());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.map);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZHttpDataParams other = (ZHttpDataParams) obj;
        return Objects.equals(this.map, other.map);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String getData(){
        Map<String, String> map = new LinkedHashMap<>(this.map);
        removeNullValuesOf(map);
        return new ZUrlMapToStr(map).buildString();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Map<String, String> getMap() {
        return map;
    }
    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    
}
