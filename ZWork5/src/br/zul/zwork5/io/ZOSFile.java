package br.zul.zwork5.io;

import br.zul.zwork4.exception.ZFileNotFoundException;
import br.zul.zwork4.exception.ZIOException;
import br.zul.zwork4.exception.ZUneditableFileException;
import br.zul.zwork4.str.ZStr;
import br.zul.zwork4.util.ZList;
import br.zul.zwork4.util.ZStrUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public class ZOSFile implements ZFile {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final File file;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZOSFile(String filePath) {
        this.file = new File(filePath);
    }
    
    public ZOSFile(File file){
        this.file = file;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String getName() {
        return file.getName();
    }
    
    @Override
    public long getSize() throws SecurityException {
        return file.length();
    }

    @Override
    public ZList<ZOSFile> listChildren() {
        ZList<ZOSFile> result = new ZList<>();
        for (File child:file.listFiles()){
            result.add(new ZOSFile(child));
        }
        return result;
    }
    
    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public ZOSFile getChild(String filename) {
        return new ZOSFile(new File(file, filename));
    }

    @Override
    public InputStream getInputStream() throws ZFileNotFoundException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            throw new ZFileNotFoundException(ex, ZStrUtils.format("{0} ({1})", file.getPath(), file.getAbsolutePath()));
        }
    }

    @Override
    public ZFileEdition editFile(boolean append) throws ZUneditableFileException {
        try {
            OutputStream os = new FileOutputStream(file, append);
            return new ZFileEdition() {
                @Override
                public OutputStream getOutputStream() {
                    return os;
                }
                @Override
                public void commit() throws ZIOException {}
                @Override
                public void rollback() throws ZIOException {}
            };
        }catch(FileNotFoundException f){
            throw new ZUneditableFileException(f);
        }
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj instanceof ZOSFile){
            ZOSFile otherFile = (ZOSFile)obj;
            return otherFile.file.equals(file);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.file);
        return hash;
    }
    
    @Override
    public ZOSFile getParent(){
        return new ZOSFile(file.getParentFile());
    }

    @Override
    public boolean mkdirs() throws SecurityException {
        return file.mkdirs();
    }

    @Override
    public boolean delete() throws SecurityException {
        return file.delete();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //========================================================================== 
    public void deleteOnExit(){
        file.deleteOnExit();
    }
    
    public File toFile(){
        return file;
    }
    
    public String getAbsolutePath(){
        return file.getAbsolutePath();
    }
    
    public String getCanonicalPath() throws IOException{
        return file.getCanonicalPath();
    }
    
    public long getFreeSpace(){
        return file.getFreeSpace();
    }
    
    public long getTotalSpace(){
        return file.getTotalSpace();
    }
    
    public long getUsableSpace(){
        return file.getUsableSpace();
    }

    @Override
    public String getExtension() {
        return new ZStr(getName()).from(".").toString();
    }
    
}
