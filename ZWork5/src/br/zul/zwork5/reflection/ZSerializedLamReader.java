package br.zul.zwork5.reflection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author luizh
 */
class ZSerializedLamReader {
    
    //==========================================================================
    //CONSTANTES
    //==========================================================================
    private static final String BINARY = "iso-8859-1";
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Serializable lambda;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZSerializedLamReader(Serializable lambda) {
        this.lambda = lambda;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZSerializedLam read() throws ZLambdaException {
        try {

            String originalName = getBinaryName(java.lang.invoke.SerializedLambda.class);
            String replacedName = getBinaryName(ZSerializedLam.class);

            try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(lambda);
                oos.flush();

                byte[] bytes = new String(bos.toByteArray(), BINARY).replace(originalName, replacedName).getBytes(BINARY);

                try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes))){
                    return (ZSerializedLam) (in.readObject());
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            throw new ZLambdaException(ex);
        }
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private static String getBinaryName(Class<?> cls) throws ZLambdaException {
        String name = cls.getName();
        int length = name.length();
        if (length>255*255) throw new ZLambdaException("Tamanho do nome da classe é inválido.");
        StringBuilder builder = new StringBuilder();
        builder.append("\0\0");
        builder.append(name);
        builder.setCharAt(1, (char)(length%256));
        builder.setCharAt(0, (char)(length/256));
        return builder.toString();
    }
    
}
