package br.zul.zwork5.entity;

import br.zul.zwork5.reflection.ZField;
import br.zul.zwork5.util.ZList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author luizh
 */
class ZAttrManagerMapper {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZEntityManager entityManager;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZAttrManagerMapper(ZEntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Map<String, ZAttrManager> map(){
        Map<String, ZAttrManager> result = new LinkedHashMap<>();
        listFieldsWithAttr().forEach(field->{
            ZAttrManager attr = new ZAttrManager(field);
            result.put(attr.getName(), attr);
        });
        return result;
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZList<ZField> listFieldsWithAttr() {
        return entityManager.getEntityClass().listFields().filter(f->f.hasAnnotation(ZAttribute.class));
    }
    
}
