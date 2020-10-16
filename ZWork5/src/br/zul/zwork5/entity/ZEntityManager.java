package br.zul.zwork5.entity;

import br.zul.zwork5.reflection.ZClass;
import br.zul.zwork5.exception.ZInstantiationException;
import br.zul.zwork5.util.ZList;
import java.util.Map;

/**
 *
 * @author luizh
 */
public class ZEntityManager {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Class<? extends ZEntity> entityClass;
    
    private ZClass<? extends ZEntity> clazz;
    private Map<String, ZAttrManager> attrMap;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZEntityManager(Class<? extends ZEntity> entityClass){
        this.entityClass = entityClass;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZEntityHandler createNewEntityHandler() throws ZInstantiationException{
        return new ZEntityHandler(createNewEntity());
    }
    
    public ZEntity createNewEntity() throws ZInstantiationException{
        try {
            return entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new ZInstantiationException(ex);
        }
    }
    
    public ZList<ZAttrManager> listAttrs(){
        return new ZList<>(getAttrMap().values());
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getEntityName() {
        ZClass<?> CLS = getEntityClass();
        ZEntityName entityName = CLS.getAnnotation(ZEntityName.class);
        if (entityName!=null){
            return entityName.value();
        } else {
            return CLS.getSimpleName();
        }
    }
    
    public ZClass<? extends ZEntity> getEntityClass(){
        if (clazz==null){
            clazz = new ZClass(entityClass);
        }
        return clazz;
    }
    
    public Map<String, ZAttrManager> getAttrMap(){
        if (attrMap==null){
            attrMap = new ZAttrManagerMapper(this).map();
        }
        return attrMap;
    }
    
    public ZAttrManager getAttr(String name){
        return getAttrMap().get(name);
    }
    
    public ZAttrManager getAttrByFieldName(String fieldName){
        return listAttrs().first(a->a.getFieldName().equals(fieldName)).get();
    }
    
    public ZAttrManager getPrimaryKeyAttr(){
        return listAttrs().first(a->a.isPrimaryKey()).get();
    }
    
}
