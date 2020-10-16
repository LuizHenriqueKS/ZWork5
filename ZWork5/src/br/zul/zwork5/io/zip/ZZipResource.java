package br.zul.zwork5.io.zip;

import br.zul.zwork5.io.ZFile;
import br.zul.zwork5.io.ZFileEdition;
import br.zul.zwork5.io.path.ZZipPath;
import br.zul.zwork5.exception.ZUneditableFileException;
import br.zul.zwork5.util.ZList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 *
 * @author Luiz Henrique
 */
public class ZZipResource implements ZFile {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZZipFile zipFile;
    private final ZZipPath path;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZZipResource(ZZipFile zipFile, String path) {
        this.zipFile = zipFile;
        this.path = new ZZipPath(path);
    }

    protected ZZipResource(ZZipFile zipFile, ZZipPath path) {
        this.zipFile = zipFile;
        this.path = path;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String getName() {
        return path.getName();
    }

    @Override
    public String getExtension() {
        return path.getExtension();
    }

    @Override
    public String getPath() {
        return path.toString();
    }

    @Override
    public ZZipResource getParent() {
        return new ZZipResource(zipFile, path.getParent());
    }

    @Override
    public long getSize() throws FileNotFoundException {
        requireExists();
        return getNode().getSize();
    }

    @Override
    public ZZipResource getChild(String filename) {
        return new ZZipResource(zipFile, path.getChild(filename));
    }

    @Override
    public ZList<ZZipResource> listChildren() {
        return zipFile.listResources(getPath());
    }

    @Override
    public boolean exists() {
        return getNode().exists();
    }

    @Override
    public boolean isDirectory() {
        return getNode().isDirectory();
    }

    @Override
    public boolean mkdirs() throws SecurityException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete() throws SecurityException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException, IOException {
        requireExists();
        return zipFile.getInputStream(getEntry());
    }

    @Override
    public ZFileEdition editFile(boolean append) throws ZUneditableFileException {
        throw new UnsupportedOperationException();
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZipEntry getEntry() throws FileNotFoundException, IOException{
        ZipEntry entry = zipFile.getEntry(getPath());
        if (entry==null){
            throw new FileNotFoundException(getPath());
        }
        return entry;
    }
    
    private ZZipTreeNode getNode(){
        return zipFile.getNode(getPath());
    }
    
    private void requireExists() throws FileNotFoundException{
        if (!getNode().exists()){
            throw new FileNotFoundException(getPath());
        }
    }
    
}
