
package br.zul.zwork5.currency;

import java.util.Currency;
import java.util.Locale;

/**
 *
 * @author luizh
 */
public enum ZCurrencyCode {
    
    //==========================================================================
    //VALORES
    //==========================================================================
    BRL,
    USD;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Locale getLocale(){
        for (Locale locale:Locale.getAvailableLocales()){
            try {
                Currency currency = Currency.getInstance(locale);
                if (currency.getCurrencyCode().toUpperCase().equals(name())){
                    return locale;
                }
            } catch (Exception e){}
        }
        return null;
    }
    
}
