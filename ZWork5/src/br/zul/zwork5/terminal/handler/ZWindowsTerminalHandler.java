package br.zul.zwork5.terminal.handler;

import br.zul.zwork5.terminal.ZTerminalOSHandler;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author luizh
 */
public class ZWindowsTerminalHandler implements ZTerminalOSHandler {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZWindowsTerminalHandler() {
        
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public List<String> getCommandList() {
        String commands[] = {"cmd.exe", "/k"};
        return Arrays.asList(commands);
    }

    @Override
    public void preprocess(Process process) {
        
    }
    
}
