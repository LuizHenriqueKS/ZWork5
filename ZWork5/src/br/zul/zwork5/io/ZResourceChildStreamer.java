package br.zul.zwork5.io;

import br.zul.zwork5.io.zip.ZZipFile;
import br.zul.zwork5.io.zip.ZZipResource;
import br.zul.zwork5.str.ZStr;
import br.zul.zwork5.stream.ZStream;
import br.zul.zwork5.util.ZAppUtils;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZPair;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 *
 * @author luiz.silva
 */
class ZResourceChildStreamer {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZResource resource;
    private final boolean subresources;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZResourceChildStreamer(ZResource resource, boolean subresources) {
        this.resource = resource;
        this.subresources = subresources;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZStream<ZResource> stream() throws IOException {
        if (ZAppUtils.isAppFileDirectory(resource.caller)){
            return streamResourceFromDirectory();
        } else {
            return streamResourceFromFile();
        }
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZStream<ZResource> streamResourceFromDirectory() {
        ZList<ZResource> result = new ZList<>();
        ZList<File> dirList = new ZList<>();
        dirList.add(ZAppUtils.getAppFile(resource.caller).getChild(resource.getPath()).toFile());
        while (!dirList.isEmpty()){
            File dir = dirList.first().get();
            streamResourceFromDirectory(dir).forEach(p->{
                result.add(p.getB());
                if (subresources&&p.getA().isDirectory()){
                    dirList.add(p.getA());
                }
            });
            dirList.removeFirst();
        }
        return new ZStream<>(result.stream());
    }
    
    private Stream<ZPair<File,ZResource>> streamResourceFromDirectory(File dir) {
        return Arrays.asList(dir.listFiles()).stream().map(f->{
           return new ZPair<>(f, new ZResource(resource.caller, f));
        });
    }

    private ZStream<ZResource> streamResourceFromFile() throws IOException {
        ZOSFile appFile = ZAppUtils.getAppFile(resource.caller);
        ZZipFile zipFile = new ZZipFile(appFile);
        return zipFile.listResources().stream()
                                      .filter(this::filterZipResource)
                                      .map(this::mapResource);
    }
    
    private boolean filterZipResource(ZZipResource resource){
        String path = "/"+resource.getPath();
        if (isPathEquals(path)){
            return false;
        } else if (isChildPath(path)){
            return true;
        } else {
            return subresources&&isRelativePath(path);
        }
    }
    
    private ZResource mapResource(ZZipResource resource){
        return new ZResource(this.resource.caller, resource.getPath());
    }

    private boolean isPathEquals(String path) {
        return path.equals(resource.getPath());
    }

    private boolean isChildPath(String path) {
        return levelRelative(path)==1;
    }
    
    public boolean isRelativePath(String path){
        return levelRelative(path)>1;
    }
    
    private String getResourcePath(){
        String path = resource.getPath();
        if (!path.endsWith("/")){
            path += "/";
        }
        return path;
    }

    private int levelRelative(String path) {
        if (path.startsWith(getResourcePath())){
            ZStr str = new ZStr(path);
            str = str.from(getResourcePath());
            return str.count("/") + 1;
        }
        return 0;
    }
    
}
