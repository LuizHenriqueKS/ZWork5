package br.zul.zwork5.io.zip;

import br.zul.zwork5.io.path.ZZipPath;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZStringMap;
import java.io.FileNotFoundException;
import java.util.zip.ZipEntry;

/**
 *
 * @author Luiz Henrique
 */
class ZZipTreeNode {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZZipFile zipFile;
    private final ZZipPath path;
    private final ZipEntry entry;
    private final ZStringMap<ZZipTreeNode> nodeMap;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZZipTreeNode(ZZipFile zipFile, ZZipPath path, ZipEntry entry) {
        this.zipFile = zipFile;
        this.path = path;
        this.entry = entry;
        this.nodeMap = new ZStringMap<>();
    }
    
    public ZZipTreeNode(ZZipFile zipFile, String path, ZipEntry entry){
        this(zipFile, new ZZipPath(path), entry);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public boolean isDirectory() {
        if (entry!=null){
            return entry.isDirectory();
        }
        return !nodeMap.isEmpty();
    }

    public boolean exists() {
        return entry!=null||!nodeMap.isEmpty();
    }

    public long getSize() throws FileNotFoundException {
        requireExists();
        if (entry!=null){
            return entry.getSize();
        }
        throw new UnsupportedOperationException();
    }
    
    public long getCompressedSize() throws FileNotFoundException{
        requireExists();
        if (entry!=null){
            return entry.getCompressedSize();
        }
        throw new UnsupportedOperationException();
    }
    
    public String getPath(){
        return path.toString();
    }
    
    public ZZipPath toPath(){
        return path;
    }
    
    void add(ZZipTreeNode node){
        String firstName = node.toPath().first();
        ZZipTreeNode first = getNode(firstName);
        if (first==null){
            first = new ZZipTreeNode(zipFile, path.getChild(firstName), null);
        }
        nodeMap.put(firstName, first);
        ZZipTreeNode rest = new ZZipTreeNode(zipFile, node.toPath().removeLast(), node.entry);
        first.add(rest);
    }

    ZZipTreeNode getChild(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    ZList<ZZipTreeNode> listChildren(){
        return new ZList<>(nodeMap.values());
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void requireExists() throws FileNotFoundException {
        if (!exists()){
            throw new FileNotFoundException(getPath());
        }
    }
    
    private ZZipTreeNode getNode(String name){
        nodeMap.setCaseSensitive(zipFile.isCaseSensitive());
        return nodeMap.get(name);
    }
    
}
