package br.zul.zwork5.log;

/**
 *
 * @author Luiz Henrique
 */
public interface ZLogFormatter {
    
    public String format(ZLogMsg msg, boolean console);
    
}
