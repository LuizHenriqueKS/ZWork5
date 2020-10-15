package br.zul.zwork5.log;

import br.zul.zwork5.exception.ZInvalidValueException;
import br.zul.zwork5.util.ZStrUtils;
import br.zul.zwork5.util.ZUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Luiz Henrique
 */
public class ZLogFileWriter implements ZLogWriter, Closeable {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================    
    private final File directory;
    
    private File genericFile;
    private File infoFile;
    private File warnFile;
    private File errorFile;
    
    private FileWriter genericWriter;
    private FileWriter infoWriter;
    private FileWriter warnWriter;
    private FileWriter errorWriter;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================    
    public ZLogFileWriter(String filePath) {
        this(new File(filePath));
    }
    
    public ZLogFileWriter(File directory){
        this.directory = directory;
        this.genericFile = new File(directory, "Log.log").getAbsoluteFile();
        this.infoFile = new File(directory, "Log.info.log").getAbsoluteFile();
        this.warnFile = new File(directory, "Log.warn.log").getAbsoluteFile();
        this.errorFile = new File(directory, "Log.error.log").getAbsoluteFile();
    }
    
    //========================================================================== 
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public void print(ZLogMsg msg) throws IOException {
        FileWriter ew = getWriter(msg.getType());
        FileWriter gw = getWriter(null);
        write(ew, msg);
        write(gw, msg);
    }
    
    //========================================================================== 
    //MÉTODOS PÚBLICOS
    //==========================================================================
    @Override
    public void close(){
        closeGenericWriter();
        closeInfoWriter();
        closeWarnWriter();
        closeErrorWriter();
    }

    public void closeGenericWriter() {
        try {
            genericWriter.close();
        }catch(NullPointerException|IOException e){}
        genericWriter = null;
    }

    public void closeInfoWriter() {
        try {
            infoWriter.close();
        }catch(NullPointerException|IOException e){}
        infoWriter = null;
    }

    public void closeWarnWriter() {
        try {
            warnWriter.close();
        }catch(NullPointerException|IOException e){}
        warnWriter = null;
    }

    public void closeErrorWriter() {
        try {
            errorWriter.close();
        }catch(NullPointerException|IOException e){}
        errorWriter = null;
    }

    public void closeWriter(ZLogType type) {
        if (type==null){
            closeGenericWriter();
        } else switch (type){
            case ERROR:
                closeErrorWriter();
                break;
            case INFO:
                closeInfoWriter();
                break;
            case WARN:
                closeWarnWriter();
                break;
        }
    }
    
    public File getFile(ZLogType type){
        if (type==null){
            return genericFile;
        } else switch (type){
            case ERROR:
                return errorFile;
            case INFO:
                return infoFile;
            case WARN:
                return warnFile;
            default:
                throw new ZInvalidValueException("Type: {0}", type.name());
        }
    }
    
    public void deleteLogFiles(){
        close();
        genericFile.delete();
        genericFile.deleteOnExit();
        infoFile.delete();
        infoFile.deleteOnExit();
        warnFile.delete();
        warnFile.deleteOnExit();
        errorFile.delete();
        errorFile.deleteOnExit();
    }
    
    //========================================================================== 
    //MÉTODOS PRIVADOS
    //==========================================================================
    private FileWriter getWriter(ZLogType type) throws IOException {
        FileWriter writer = null;
        if (type==null){
            writer = genericWriter;
        } else switch (type){
            case ERROR:
                writer = errorWriter;
                break;
            case INFO:
                writer = infoWriter;
                break;
            case WARN:
                writer = warnWriter;
                break;
        }
        return writer==null?createWriter(type):writer;
    }

    private FileWriter createWriter(ZLogType type) throws IOException {
        File file = getFile(type);
        if (file==null) return null;
        file.getParentFile().mkdirs();
        closeWriter(type);
        FileWriter writer = null;
        if (type==null){
            writer = genericWriter = new FileWriter(file, true);
        } else switch (type){
            case ERROR:
                writer = errorWriter = new FileWriter(file, true);
                break;
            case INFO:
                writer = infoWriter = new FileWriter(file, true);
                break;
            case WARN:
                writer = warnWriter = new FileWriter(file, true);
                break;
        }
        return writer;
    }

    private void write(FileWriter writer, ZLogMsg msg) throws IOException {
        if (writer!=null){
            writer.write(msg.getDateTime().toString());
            writer.write(", ");
            writer.write(msg.getType().name());
            writer.write(", ");
            if (ZUtil.hasContent(msg.getMessage())){
                writer.write(ZStrUtils.format(msg.getMessage(), msg.getArgs()));
            }
            if (msg.getThrowable()!=null){
                writer.write("\r\n");
                writer.write(ZUtil.getStackTrace(msg.getThrowable()));
            }
            writer.write("\r\n");
            writer.flush();
        }
    }
    
    //========================================================================== 
    //GETTERS E SETTERS
    //==========================================================================
    public File getGenericFile() {
        return genericFile;
    }
    public void setGenericFile(File genericFile) {
        this.genericFile = genericFile;
        closeGenericWriter();
    }

    public File getInfoFile() {
        return infoFile;
    }
    public void setInfoFile(File infoFile) {
        this.infoFile = infoFile;
        closeInfoWriter();
    }

    public File getWarnFile() {
        return warnFile;
    }
    public void setWarnFile(File warnFile) {
        this.warnFile = warnFile;
        closeWarnWriter();
    }

    public File getErrorFile() {
        return errorFile;
    }
    public void setErrorFile(File errorFile) {
        this.errorFile = errorFile;
        closeErrorWriter();
    }
    
}