package br.zul.zwork5.url;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author luizh
 */
public class ZUrlMapToStr {

    //==========================================================================
    //VARIÁVEIS 
    //==========================================================================
    private final Map<String, String> map;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUrlMapToStr(Map<String, String> map) {
        this.map = map;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String buildString() throws UnsupportedEncodingException{
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> e:map.entrySet()){
            if (!first){
                builder.append("&");
            }
            first = false;
            builder.append(ZUrl.encode(e.getKey()));
            builder.append("=");
            builder.append(ZUrl.encode(e.getValue()));
        }
        return builder.toString();
    }
    
}
