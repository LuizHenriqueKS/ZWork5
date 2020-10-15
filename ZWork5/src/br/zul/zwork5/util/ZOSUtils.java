package br.zul.zwork5.util;

/**
 *
 * @author Luiz Henrique
 */
public class ZOSUtils {
    
    public static boolean isWindows(){
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
    
    public static ZOSEnum getOS(){
        if (isWindows()){
            return ZOSEnum.WINDOWS;
        }
        throw new UnsupportedOperationException();
    }
    
    public static void timeZoneBrasil() {
        System.setProperty("user.timezone", "America/Sao_Paulo");
    }
    
}
