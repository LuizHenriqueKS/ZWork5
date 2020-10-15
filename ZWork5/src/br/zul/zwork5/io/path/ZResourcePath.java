package br.zul.zwork5.io.path;

import br.zul.zwork5.reflection.ZPackage;
import java.util.Collection;

/**
 *
 * @author Luiz Henrique
 */
public class ZResourcePath extends ZLinuxPath {

    public ZResourcePath(Collection<String> pathList) {
        super(pathList);
    }

    public ZResourcePath(String... path) {
        super(path);
    }

    public ZResourcePath(String path) {
        super(path);
    }
    
    public ZResourcePath(ZPackage pack, String path){
        super(pack.getName().replace(".","/")+"/"+path);
    }
    
}
