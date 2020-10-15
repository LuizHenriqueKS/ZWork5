package br.zul.zwork5.reflection;

import br.zul.zwork5.io.ZResource;
import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.stream.ZStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author luiz.silva
 */
public class ZPackageClassStreamer {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZPackage pack;
    private final boolean subclasses;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZPackageClassStreamer(ZPackage pack, boolean subclasses) {
        this.pack = pack;
        this.subclasses = subclasses;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZStream<ZClass<?>> stream() throws IOException, ClassNotFoundException {
        return pack.resourceStream(subclasses)
                   .filter(this::filterResource)
                   .toList()
                   .mapThrowable(this::resourceToClass)
                   .stream();
    }
 
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private boolean filterResource(ZResource resource){
        return resource.getName().toLowerCase().endsWith(".class");
    }
    
    private ZClass<?> resourceToClass(ZResource resource) throws ClassNotFoundException{
        String className = resourcePathToClassName(resource.getPath());
        return new ZClass<>(className);
    }

    private String resourcePathToClassName(String path) {
        ZStr str = new ZStr(path);
        str = str.till(".");
        if (str.startsWith("/")) str = str.from("/");
        if (str.endsWith("/")) str = str.tillLast("/");
        return str.toString().replace("/", ".");
    }
    
}

