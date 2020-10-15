package br.zul.zwork5.currency;

import br.zul.zwork4.exception.ZParseException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;

import java.util.Objects;

/**
 *
 * @author luizh
 */
public final class ZCurrency {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    public static ZCurrencyCode DEFAULT_CURRENCY_CODE = ZCurrencyCode.BRL;
    
    private final ZCurrencyCode currencyCode;
    private final BigDecimal value;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZCurrency(BigDecimal value){
        this.value = value;
        this.currencyCode = DEFAULT_CURRENCY_CODE;
    }
    
    public ZCurrency(BigDecimal value, ZCurrencyCode currencyCode){
        this.value = value;
        this.currencyCode = currencyCode;
    }
    
    public ZCurrency(double value){
        this(new BigDecimal(value));
    }
    
    public ZCurrency(double value, ZCurrencyCode currencyCode){
        this(new BigDecimal(value), currencyCode);
    }
    
    public ZCurrency(String value) throws ZParseException{
        this(value, DEFAULT_CURRENCY_CODE);
    }
    
    public ZCurrency(String value, ZCurrencyCode currencyCode) throws ZParseException{
        BigDecimal val;
        this.currencyCode = DEFAULT_CURRENCY_CODE;
        try {
            val = new BigDecimal(getNumberFormat().parse(value).doubleValue());
        } catch (ParseException e){
            try {
               val = new BigDecimal(value);
            }catch(NumberFormatException ex2){
                throw new ZParseException(e);
            }
        }
        this.value = val;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString(){
        return getNumberFormat().format(value);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.currencyCode);
        hash = 59 * hash + Objects.hashCode(round().value.toString());
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
        final ZCurrency other = (ZCurrency) obj;
        if (this.currencyCode != other.currencyCode) {
            return false;
        }
        return Objects.equals(round().value, other.round().value);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public double asDouble(){
        return value.doubleValue();
    }
    
    public BigDecimal asBigDecimal(){
        return value;
    }
    
    public int compareTo(ZCurrency val){
        return value.compareTo(val.value);
    }
    
    public ZCurrency add(ZCurrency augend){
        return new ZCurrency(value.add(augend.value), currencyCode);
    }
    
    public ZCurrency subtract(ZCurrency subtrahend){
        return new ZCurrency(value.subtract(subtrahend.value), currencyCode);
    }
    
    public ZCurrency multiply(ZCurrency multiplicand){
        return new ZCurrency(value.multiply(multiplicand.value), currencyCode);
    }
    
    public ZCurrency divide(ZCurrency divisor, RoundingMode roundingMode){
        return new ZCurrency(divisor.value.divide(value, roundingMode), currencyCode);
    }
    
    public ZCurrency round(int scale, RoundingMode roundingMode){
        return new ZCurrency(value.setScale(scale, roundingMode));
    }
    
    public ZCurrency round(){
        return new ZCurrency(toString(), currencyCode);
    }
    
    public ZCurrency abs(){
        return new ZCurrency(asBigDecimal().abs());
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZCurrencyCode getCurrencyCode(){
        return currencyCode;
    }
    
    public NumberFormat getNumberFormat(){
        return NumberFormat.getCurrencyInstance(currencyCode.getLocale());
    }
    
}
