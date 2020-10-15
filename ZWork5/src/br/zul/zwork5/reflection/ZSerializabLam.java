package br.zul.zwork5.reflection;

import java.io.Serializable;

/**
 *
 * @author luizh
 */
class ZSerializedLam implements Serializable {
    
    private static final long serialVersionUID = 8025925345765570181L;
    Class<?> capturingClass;
    String functionalInterfaceClass;
    String functionalInterfaceMethodName;
    String functionalInterfaceMethodSignature;
    String implClass;
    String implMethodName;
    String implMethodSignature;
    int implMethodKind;
    String instantiatedMethodType;
    Object[] capturedArgs;

}
