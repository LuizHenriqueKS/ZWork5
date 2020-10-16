package br.zul.zwork5.exception;

/**
 *
 * @author luizh
 */
public class ZComparatorNotFoundException extends ZException {

    public ZComparatorNotFoundException(Class<?> type) {
        super("Comparador não encontrado para a classe: "+ type.getName());
    }

}
