package br.zul.zwork5.log;

import br.zul.zwork5.timestamp.ZDateTime;

/**
 *
 * @author Luiz Henrique
 */
public class ZLogMsg {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    protected ZDateTime dateTime;
    protected ZLogType type;
    protected ZLogger logger;
    protected Throwable throwable;
    protected String message;
    protected Object[] args;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZLogMsg(){
        this.dateTime = new ZDateTime();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(ZDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public ZLogType getType() {
        return type;
    }
    public void setType(ZLogType type) {
        this.type = type;
    }

    public ZLogger getLogger() {
        return logger;
    }
    public void setLogger(ZLogger logger) {
        this.logger = logger;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getArgs() {
        return args;
    }
    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Throwable getThrowable() {
        return throwable;
    }
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
    
}
