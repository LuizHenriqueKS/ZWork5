package br.zul.zwork5.io;

import br.zul.zwork5.exception.ZUneditableFileException;
import br.zul.zwork5.util.ZList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Luiz Henrique
 */
public interface ZFile {
    
    public String getName();
    public String getExtension();
    public String getPath();
    public ZFile getParent();
    public long getSize() throws SecurityException, IOException, FileNotFoundException;
    public ZFile getChild(String filename);
    public ZList<? extends ZFile> listChildren();
    public boolean exists();
    public boolean isDirectory();
    public boolean mkdirs() throws SecurityException;
    public boolean delete() throws SecurityException;
    
    @Override
    public boolean equals(Object obj);
    
    @Override
    public int hashCode();
    
    public InputStream getInputStream() throws FileNotFoundException, IOException;
    
    public ZFileEdition editFile(boolean append) throws ZUneditableFileException;
    
}
