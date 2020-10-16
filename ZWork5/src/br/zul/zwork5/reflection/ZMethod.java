package br.zul.zwork5.reflection;

import br.zul.zwork5.conversion.ZConversionOut;
import br.zul.zwork5.exception.ZConversionErrorException;
import br.zul.zwork5.exception.ZMethodException;
import br.zul.zwork5.exception.ZMethodIsNotStaticException;
import br.zul.zwork5.exception.ZMethodIsStaticException;
import br.zul.zwork5.exception.ZUnexpectedException;
import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZUtil;
import br.zul.zwork5.value.ZValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author luizh
 */
public class ZMethod {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final ZClass<?> objClass;
    private final Method method;
    private final String name;
    private final ZList<ZMethodParam> paramList;
    private final boolean accessible;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    ZMethod(ZClass<?> _class, Method method){
        this.objClass = _class;
        this.method = method;
        this.name = method.getName();
        this.paramList = initParamList(method.getParameters());
        this.accessible = method.isAccessible();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.objClass._class);
        hash = 61 * hash + Objects.hashCode(this.method);
        hash = 61 * hash + Objects.hashCode(this.name);
        hash = 61 * hash + Objects.hashCode(this.paramList);
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
        final ZMethod other = (ZMethod) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.objClass._class, other.objClass._class)) {
            return false;
        }
        if (!Objects.equals(this.method, other.method)) {
            return false;
        }
        return Objects.equals(this.paramList, other.paramList);
    }
    
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZValue invoke() throws ZMethodException, ZMethodIsNotStaticException{
        return invokeStatic(null);
    }    
    
    public ZValue invoke(Collection<Object> paramValueList) throws ZMethodException, ZMethodIsNotStaticException{
        return invokeStatic(paramValueList);
    }
    
    public <T> ZConversionOut<T> invoke(Class<T> returnType) throws ZMethodException, ZMethodIsNotStaticException{
       return invoke().convertTo(returnType);
    }
    
    public <T> ZConversionOut<T> invoke(Class<T> returnType, Collection<Object> paramValueList) throws ZMethodException, ZMethodIsNotStaticException{
        return invoke(paramValueList).convertTo(returnType);
    }
    
    public ZValue invoke(Object obj) throws ZMethodException, ZMethodIsStaticException{
        return invokeNoStatic(obj, null);
    }
    
    public ZValue invoke(Object obj, Collection<Object> paramValueList) throws ZMethodException, ZMethodIsStaticException{
        return invokeNoStatic(obj, paramValueList);
    }
    
    public <T> ZConversionOut<T> invoke(Object obj, Class<T> returnType) throws ZMethodException, ZMethodIsStaticException{
        return invoke(obj).convertTo(returnType);
    }
    
    public <T> ZConversionOut<T> invoke(Object obj, Class<T> returnType, Collection<Object> paramValueList) throws ZMethodException, ZMethodIsStaticException{
        return invoke(obj, paramValueList).convertTo(returnType);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return method.getAnnotation(annotationClass);
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
        return method.isAnnotationPresent(annotationClass);
    }
    
    public ZList<ZMethodParam> listParams(){
        return paramList.copy();
    }
    
    public ZList<Class<?>> listParamTypes(){
        return paramList.map(p->p.getType());
    }
    
    public ZList<Annotation> listAnnotations(){
        ZList<Annotation> result = new ZList<>();
        result.addAll(method.getAnnotations());
        result.addAll(method.getDeclaredAnnotations());
        return result;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZValue invokeStatic(Collection<Object> paramValueList) throws ZMethodException, ZMethodIsNotStaticException{
        try {
            return invokeSpecial(null, paramValueList);
        } catch (ZMethodIsStaticException ex) {
            throw new ZUnexpectedException(ex);
        }
    }
    
    private ZValue invokeNoStatic(Object obj, Collection<Object> paramValueList) throws ZMethodException, ZMethodIsStaticException{
        try {
            return invokeSpecial(null, paramValueList);
        } catch (ZMethodIsNotStaticException ex) {
            throw new ZUnexpectedException(ex);
        }
    }
    
    private ZValue invokeSpecial(Object obj, Collection<Object> paramValueList) throws ZMethodException, ZMethodIsNotStaticException, ZMethodIsStaticException {
        try {
            if (obj==null){
                requireMethodStatic();
            } else {
                requireMethodNotStatic();
            }
            method.setAccessible(true);
            Object value;
            if (ZUtil.hasContent(paramValueList)){
                Object[] params = paramValueList.toArray(new Object[paramValueList.size()]);
                value = method.invoke(obj, params);
            } else {
                value = method.invoke(obj);
            }
            method.setAccessible(accessible);
            return () -> value;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ZMethodException(ex);
        }
    }
    

    private void requireMethodStatic() throws ZMethodIsNotStaticException {
        if (!isStatic()){
            throw new ZMethodIsNotStaticException(this);
        }
    }

    private void requireMethodNotStatic() throws ZMethodIsStaticException {
        if (isStatic()){
            throw new ZMethodIsStaticException(this);
        }
    }
    
    private ZList<ZMethodParam> initParamList(Parameter[] parameters) {
        ZList<ZMethodParam> result = new ZList<>();
        for (Parameter parameter:parameters){
            ZMethodParam param = new ZMethodParam(parameter);
            result.add(param);
        }
        return result;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public boolean isStatic() {
        return Modifier.isStatic(method.getModifiers());
    }
    
    public boolean isAbstract(){
        return Modifier.isAbstract(method.getModifiers());
    }
    
    public boolean isNative(){
        return Modifier.isNative(method.getModifiers());
    }

    public boolean isPrivate(){
        return Modifier.isPrivate(method.getModifiers());
    }
    
    public boolean isProtected(){
        return Modifier.isProtected(method.getModifiers());
    }
            
    public boolean isPublic(){
        return Modifier.isPublic(method.getModifiers());
    }
    
    public boolean isSynchronized(){
        return Modifier.isSynchronized(method.getModifiers());
    }
    
    public boolean isBridge(){
        return method.isBridge();
    }
    
    public boolean isDefault(){
        return method.isDefault();
    }
    
    public boolean isSynthetic(){
        return method.isSynthetic();
    }

    public boolean isAccessible() {
        return accessible;
    }
    
    public String getName() {
        return name;
    }
    
    public Class<?> getReturnType(){
        return method.getReturnType();
    }

    public ZClass<?> getObjClass() {
        return objClass;
    }
    
}
