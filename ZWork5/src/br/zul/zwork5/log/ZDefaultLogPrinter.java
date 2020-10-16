/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork5.log;

import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author luiz.silva
 */
public class ZDefaultLogPrinter extends ZLogPrinter {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZLogger logger;
    private final PrintStream printStream;
    private final ZLogType type;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDefaultLogPrinter(ZLogger logger, PrintStream printStream, ZLogType type) {
        this.logger = logger;
        this.printStream = printStream;
        this.type = type;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public void print(ZLogMsg msg) {
        msg.setLogger(logger);
        String formattedMessage = logger.getFormatter().format(msg, true);
        printOnConsoleIfCan(printStream, formattedMessage);
        tryWrite(msg);
    }

    @Override
    public ZLogType getType() {
        return type;
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void printOnConsoleIfCan(PrintStream printStream, String formattedMessage) {
        if (logger.isPrintOnConsole()){
            printStream.print(formattedMessage);
        }
    }

    private void tryWrite(ZLogMsg msg) {
        if (logger.getWriter() != null) {
            try {
                logger.getWriter().print(msg);
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
    
}
