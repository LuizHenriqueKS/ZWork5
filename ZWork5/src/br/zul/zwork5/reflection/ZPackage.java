package br.zul.zwork5.reflection;

import br.zul.zwork5.io.ZResource;
import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.stream.ZStream;
import java.io.IOException;

/**
 *
 * @author Luiz Henrique
 */
public class ZPackage {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Class<?> caller;
    private final String path;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZPackage(Class<?> caller, String pack) {
        this.caller = caller;
        if (!pack.contains("\\")&&!pack.contains("/")){
            path = pack.replace(".", "/");
        } else {
            path = pack;
        }
    }
    
    public ZPackage(Class<?> caller, Package pack){
        this(caller, pack.getName());
    }
    
    public static ZPackage fromClass(Class<?> cls){
        return new ZPackage(cls, cls.getPackage());
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String getName(){
        ZStr name = new ZStr(path.replace("/", "."));
        while (name.startsWith(".")){
            name = name.from(".");
        }
        while (name.endsWith(".")){
            name = name.tillLast(".");
        }
        return name.toString();
    }
    
    public ZStream<ZClass<?>> classStream(boolean subclasses) throws IOException, ClassNotFoundException{
        return new ZPackageClassStreamer(this, subclasses).stream();
    }

    public ZStream<ZResource> resourceStream(boolean subresources) throws IOException {
        return new ZResource(caller, path).resourceStream(subresources);
    }
    
}
