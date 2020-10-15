package br.zul.zwork5.io.path;

import java.util.Collection;

/**
 *
 * @author Luiz Henrique
 */
public class ZZipPath extends ZPlainPath {

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZZipPath(Collection<String> pathList) {
        super(pathList);
    }

    public ZZipPath(String... path) {
        super(path);
    }

    public ZZipPath(String path) {
        super(path);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZZipPath getParent() {
        return new ZZipPath(super.getParent().list);
    }
    
    @Override
    public ZZipPath getChild(String path) {
        return new ZZipPath(super.getChild(path).list);
    }

    @Override
    public ZZipPath removeLast() {
        return new ZZipPath(super.removeLast().list);
    }

    @Override
    public ZZipPath removeFirst() {
        return new ZZipPath(super.removeFirst().list);
    }

    @Override
    public ZZipPath tillIndex(int index) {
        return new ZZipPath(super.tillIndex(index).list);
    }

    @Override
    public ZZipPath fromIndex(int index) {
        return new ZZipPath(super.fromIndex(index).list);
    }
    
}
