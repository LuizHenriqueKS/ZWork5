package br.zul.zwork5.util;

import br.zul.zwork5.conversion.ZConversionObj;
import br.zul.zwork5.conversion.annotation.ZFormatAnnotation;
import br.zul.zwork5.entity.ZAttribute;
import java.math.BigDecimal;

/**
 *
 * @author Luiz Henrique
 */
public class ZBigDecimalFormat {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private String format;
    private Integer scale;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZBigDecimalFormat(String format) {
        this.format = format;
    }
    
    public ZBigDecimalFormat(ZConversionObj conversionObj){
        if (conversionObj.hasAnnotation(ZFormatAnnotation.class)){
            this.format = conversionObj.getAnnotation(ZFormatAnnotation.class).value();
        }
        if (conversionObj.hasAnnotation(ZAttribute.class)&&conversionObj.getAnnotation(ZAttribute.class).scale()!=-1){
            this.scale = conversionObj.getAnnotation(ZAttribute.class).scale();
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public BigDecimal parse(String value){
        if (ZUtil.hasContent(format)){
            throw new UnsupportedOperationException();
        } else {
            return new BigDecimal(value);
        }
    }

    public String format(BigDecimal bigDecimal) {
        bigDecimal = applyScale(bigDecimal);
        return bigDecimal.toString();
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private BigDecimal applyScale(BigDecimal bigDecimal){
        if (scale!=null){
            return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_DOWN);
        } else {
            return bigDecimal;
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getScale() {
        return scale;
    }
    public void setScale(Integer scale) {
        this.scale = scale;
    }
    
}
