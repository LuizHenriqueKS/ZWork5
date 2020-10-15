package br.zul.zwork5.reflection;

import br.zul.zwork5.exception.ZMethodException;
import br.zul.zwork5.exception.ZMethodNotFoundException;
import java.io.Serializable;

/**
 *
 * @author luizh
 */
public class ZLambda {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Serializable lambda;
    private final ZSerializedLam serializedLam;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZLambda(Serializable lambda) throws ZLambdaException{
        this.lambda = lambda;
        this.serializedLam = new ZSerializedLamReader(lambda).read();
    }
    
    //==========================================================================
    //CONSTRUTORES ESTÁTICOS
    //==========================================================================
    public static <T, R> ZLambda fromFunction(ZLambdaInterfaces.ZLambdaFunction<T, R> function) throws ZLambdaException{
        return new ZLambda(function);
    }
    
    public static <T> ZLambda fromSupplier(ZLambdaInterfaces.ZLambdaSupplier<T> supplier) throws ZLambdaException{
        return new ZLambda(supplier);
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public String getImplMethodName(){
        return serializedLam.implMethodName;
    }
    
    public String getImplClassName(){
        return serializedLam.implClass.replace("/", ".");
    }
    
    public ZClass<?> getImplClass() throws ClassNotFoundException{
        return new ZClass<>(getImplClassName());
    }
    
    public ZMethod getImplMethod() throws ClassNotFoundException, ZMethodNotFoundException{
        return getImplClass().getMethod(getImplMethodName());
    }
    
}
