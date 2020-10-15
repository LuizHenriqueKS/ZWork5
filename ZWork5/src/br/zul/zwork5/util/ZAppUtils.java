package br.zul.zwork5.util;

import br.zul.zwork5.io.ZOSFile;
import br.zul.zwork5.log.ZLogger;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author Luiz
 */
public class ZAppUtils {
    
    public static ZOSFile getAppFile(Class _class){
        String path = _class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath;
        try {
            decodedPath = URLDecoder.decode(path, "UTF-8");
            return new ZOSFile(decodedPath);
        } catch (UnsupportedEncodingException ex) {
            new ZLogger(ZAppUtils.class).error.println(ex);
        }
        return new ZOSFile(path);
    }
    
    public static String getAppFilename(Class anyClass){
        return getAppFile(anyClass).getName();
    }
    
    public static boolean isAppFileJar(Class _class){
        return getAppFile(_class).getExtension().toLowerCase().equals("jar");
    }
    
    public static boolean isAppFileExe(Class _class){
        return getAppFile(_class).getExtension().toLowerCase().equals("exe");
    }
    
    public static boolean isAppFileDirectory(Class _class){
        return getAppFile(_class).isDirectory();
    }
    
    public static String getCurrentRelativePath(){
        return new File("").getAbsolutePath();
    }
    
    public static void onExit(Runnable runnable){
         Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }
    
}
