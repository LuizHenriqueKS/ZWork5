package br.zul.zwork5.io.zip;

import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.io.path.ZZipPath;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZStringMap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.ZipEntry;

/**
 *
 * @author Luiz Henrique
 */
public class ZZipFile {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZFile file;
    private final Charset charset;
    private boolean caseSensitive = false;
    private ZStringMap<ZipEntry> entryMap;
    private ZZipTreeNode root;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZZipFile(ZFile file, Charset charset){
        this.file = file;
        this.charset = charset;
    }

    public ZZipFile(ZFile file) {
        this.file = file;
        this.charset = Charset.defaultCharset();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZZipResource getResource(String path) {
        return new ZZipResource(this, path);
    }
    
    public ZipEntry getEntry(String path) throws IOException{
        return entryMap().get(path);
    }
    
    public ZList<ZZipResource> listResources() throws IOException{
        ZList<ZZipResource> result = new ZList<>();
        for (ZipEntry entry:entryMap().values()){
            result.add(new ZZipResource(this, entry.getName()));
        }
        return result;
    }
    
    //==========================================================================
    //MÉTODOS PROTEGIDOS
    //==========================================================================
    ZZipTreeNode getRoot(){
        if (root==null){
            root = new ZZipTreeNode(this, "/", null);
        }
        return root;
    }
    
    ZZipTreeNode getNode(String path){
        return getRoot().getChild(path);
    }

    ZList<ZZipResource> listResources(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    InputStream getInputStream(ZipEntry entry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZList<ZipEntry> newListEntries() throws IOException{
        ZList<ZipEntry> result = new ZList<>();
        ZZipFileReader zis = new ZZipFileReader(file, charset);
        ZipEntry entry;
        while ((entry = zis.getNextEntry())!=null){
            result.add(entry);
        }
        zis.close();
        return result;
    }
    
    private Map<String, ZipEntry> entryMap() throws IOException{
        if (entryMap!=null) return entryMap;
        entryMap = new ZStringMap<>();
        entryMap.setCaseSensitive(isCaseSensitive());
        for (ZipEntry entry:newListEntries()){
            ZZipPath path = new ZZipPath(entry.getName());
            entryMap.put(path.toString(), entry);
        }
        return entryMap;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public boolean isCaseSensitive() {
        return caseSensitive;
    }
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        if (entryMap!=null){
            entryMap.setCaseSensitive(caseSensitive);
        }
    }
    
}
