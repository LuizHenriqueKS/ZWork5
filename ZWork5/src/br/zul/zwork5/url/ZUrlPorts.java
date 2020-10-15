package br.zul.zwork5.url;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author luizh
 */
class ZUrlPorts {

    public static boolean isPortDefault(String protocol, int port){
        Integer defaultPort = getDefaultPortMap().get(protocol);
        if (defaultPort==null){
            return false;
        }
        return defaultPort==port;
    }
    
    public static Map<String, Integer> getDefaultPortMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("http", 80);
        map.put("https", 443);
        map.put("ftp", 21);
        map.put("telnet", 23);
        map.put("ssh", 22);
        return map;
    }
    
}
