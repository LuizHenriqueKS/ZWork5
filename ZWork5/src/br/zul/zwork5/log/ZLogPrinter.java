package br.zul.zwork5.log;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZLogPrinter {

    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract ZLogType getType();
    public abstract void print(ZLogMsg msg);
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void print(String message, Object... args){
        ZLogMsg msg = buildMsg();
        msg.setMessage(message);
        msg.setArgs(args);
        print(msg);
    }
    
    public void println(String message, Object... args){
        print(message+"\r\n", args);
    }
    
    public void println(Throwable throwable){
        ZLogMsg msg = buildMsg();
        msg.setThrowable(throwable);
        print(msg);
    }
    
    public void println(Throwable throwable, String message, Object... args){
        ZLogMsg msg = buildMsg();
        msg.setThrowable(throwable);
        msg.setMessage(message+"\r\n");
        msg.setArgs(args);
        print(msg);
    }

    //==========================================================================
    //MÉTODOS PROTEGIDOS
    //==========================================================================
    protected ZLogMsg buildMsg(){
        ZLogMsg msg = new ZLogMsg();
        msg.setType(getType());
        return msg;
    }
    
}
