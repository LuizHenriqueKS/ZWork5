package br.zul.zwork5.url;

import static br.zul.zwork5.url.ZUrl.decode;
import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.util.ZUtil;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author luizh
 */
public class ZUrlStrToMap {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final String string;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZUrlStrToMap(String string) {
        this.string = string;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Map<String, String> buildMap() throws UnsupportedEncodingException{
        Map<String, String> paramMap = new LinkedHashMap<>();
        if (ZUtil.hasContent(string)){
            for (String pair: string.split("&")){
                ZStr str = new ZStr(pair);
                String key = str.till("=").toString();
                String value = str.from("=").toString();
                key = decode(key);
                value = decode(value);
                paramMap.put(key, value);
            }
        }
        return Collections.unmodifiableMap(paramMap);
    }
    
}
