package br.zul.zwork5.terminal;

/**
 *
 * @author luizh
 */
public class ZTerminalMsg {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    String message;
    ZTerminalMsgType type;

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public ZTerminalMsgType getType() {
        return type;
    }
    public void setType(ZTerminalMsgType type) {
        this.type = type;
    }
    
}
