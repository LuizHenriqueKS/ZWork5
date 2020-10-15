package br.zul.zwork5.reflection;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author luizh
 */
public class ZLambdaInterfaces {

    public static interface ZLambdaFunction<T, R> extends Serializable, Function<T, R>{
        
    }
    
    public static interface ZLambdaSupplier<T> extends Serializable, Supplier<T>{ 
        
    }
    
}
