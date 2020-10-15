/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork5.log;

import java.io.PrintStream;

/**
 *
 * @author luiz.silva
 */
public class ZDefaultLogPrinter extends ZLogPrinter {

    @Override
    public void print(ZLogMsg msg) {
        msg.setLogger(ZLogger.this);
        String formattedMessage = formatter.format(msg, true);
        printOnConsoleIfCan(printStream, formattedMessage);
        if (writer != null) {
            writer.print(msg);
        }
    }

    @Override
    public ZLogType getType() {
        return type;
    }
    
}
