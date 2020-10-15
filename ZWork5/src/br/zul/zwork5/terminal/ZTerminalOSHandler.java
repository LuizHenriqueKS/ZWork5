package br.zul.zwork5.terminal;

import java.util.List;

/**
 *
 * @author luizh
 */
public interface ZTerminalOSHandler {
    
    public List<String> getCommandList();
    public void preprocess(Process process);
    
}
