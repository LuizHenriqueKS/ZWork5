package br.zul.zwork5.io.path;

import java.util.Collection;

/**
 *
 * @author Luiz Henrique
 */
public class ZLinuxPath extends ZPlainPath {

    public ZLinuxPath(Collection<String> pathList) {
        super(pathList);
    }

    public ZLinuxPath(String... path) {
        super(path);
    }

    public ZLinuxPath(String path) {
        super(path);
    }
    
    @Override
    public String toString() {
        return "/"+super.toString();
    }
    
}
