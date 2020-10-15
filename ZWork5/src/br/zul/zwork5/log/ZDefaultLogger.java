package br.zul.zwork5.log;

/**
 *
 * @author Luiz Henrique
 */
public class ZDefaultLogger extends ZLogger {

    public ZDefaultLogger() {
        super(ZLogger.class);
    }

    @Override
    public boolean isPrintTagOnConsole() {
        return ZLog.isDefaultPrintTagOnConsole();
    }

    @Override
    public boolean isPrintTypeOnConsole() {
        return ZLog.isDefaultPrintTypeOnConsole();
    }

    @Override
    public boolean isPrintDateTimeOnConsole() {
        return ZLog.isDefaultPrintDateTimeOnConsole();
    }

    @Override
    public boolean isPrintOwnerClassMainOnConsole() {
        return ZLog.isDefaultPrintOwnerClassMainOnConsole();
    }

    @Override
    public boolean isPrintOnConsole() {
        return ZLog.isDefaultPrintOnConsole();
    }

    @Override
    public ZLogWriter getWriter() {
        return ZLog.getDefaultWriter();
    }

    @Override
    public ZLogFormatter getFormatter() {
        return ZLog.getDefaultFormatter();
    }
    
}
