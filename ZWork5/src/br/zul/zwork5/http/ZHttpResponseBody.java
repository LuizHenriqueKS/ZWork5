package br.zul.zwork5.http;

import br.zul.zwork4.exception.ZIOException;
import br.zul.zwork5.html.ZHtml;
import br.zul.zwork4.value.ZValue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author luiz.silva
 */
public class ZHttpResponseBody implements ZValue {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZHttpResponse response;
    private final InputStream inputStream;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZHttpResponseBody(ZHttpResponse response, InputStream inputStream) {
        this.response = response;
        this.inputStream = inputStream;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void save(File file) throws ZIOException{
        try {
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new ZIOException(ex);
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String asString() {
        return new ZHttpResponseBodyTxtReader(response, inputStream).read();
    }

    @Override
    public Object asObject() {
        return asString().trim();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZHtml asHtml(){
        return new ZHtml(asString());
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public InputStream getInputStream() {
        return inputStream;
    }
    
}
