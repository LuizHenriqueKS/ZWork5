package br.zul.zwork5.io;

import br.zul.zwork5.io.path.ZResourcePath;
import br.zul.zwork5.io.path.ZPath;
import br.zul.zwork5.io.zip.ZZipFile;
import br.zul.zwork5.reflection.ZPackage;
import br.zul.zwork5.exception.ZUneditableFileException;
import br.zul.zwork5.stream.ZStream;
import br.zul.zwork5.util.ZAppUtils;
import br.zul.zwork5.util.ZList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public class ZResource implements ZFile{

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    final Class<?> caller;
    private final ZPath path;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZResource(Class<?> caller, String path) {
        this.caller = caller;
        this.path = new ZResourcePath(path);
    }
    
    public ZResource(Class<?> caller, ZPath path){
        this.caller = caller;
        this.path = path;
    }

    public ZResource(Class<?> caller, Package pack, String path) {
        this.caller = caller;
        this.path = new ZResourcePath(new ZPackage(caller, pack), path);
    }
    
    ZResource(Class<?> caller, File file){
        this(caller, file.getAbsolutePath().substring(ZAppUtils.getAppFile(caller).getAbsolutePath().length()).replace("\\", "/"));
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    @Override
    public String getName() {
        return path.getName();
    }

    @Override
    public String getPath() {
        return path.toString();
    }

    @Override
    public ZResource getParent() {
        return new ZResource(caller, path.getParent());
    }

    @Override
    public long getSize() throws SecurityException, IOException {
        return getURL().openConnection().getContentLengthLong();
    }

    @Override
    public ZResource getChild(String filename) {
        return new ZResource(caller, path.getChild(filename));
    }

    @Override
    public ZList<ZResource> listChildren() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exists() {
        return getURL()!=null;
    }

    @Override
    public boolean isDirectory() {
        if (ZAppUtils.isAppFileDirectory(caller)){
            ZOSFile dir = ZAppUtils.getAppFile(caller);
            return dir.getChild(getPath()).exists();
        } else {
            ZOSFile appFile = ZAppUtils.getAppFile(caller);
            ZZipFile zipFile = new ZZipFile(appFile);
            return zipFile.getResource(getPath()).isDirectory();
        }
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
        return getURL().openStream();
    }

    @Override
    public ZFileEdition editFile(boolean append) throws ZUneditableFileException {
        throw new UnsupportedOperationException(); 
    }

    @Override
    public String getExtension() {
        return path.getExtension();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.path);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZResource other = (ZResource) obj;
        return Objects.equals(this.path, other.path);
    }

    //==========================================================================
    //MÉTODOS PUBLICOS
    //==========================================================================
    public ZStream<ZResource> resourceStream(boolean subresources) throws IOException {
        return new ZResourceChildStreamer(this, subresources).stream();
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private URL getURL(){
        return caller.getResource(getPath());
    }
    
    private void requireExists() throws FileNotFoundException{
        if (!exists()){
            throw new FileNotFoundException(getPath());
        }
    }
    
}
