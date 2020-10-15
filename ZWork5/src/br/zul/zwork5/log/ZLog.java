package br.zul.zwork5.log;

/**
 *
 * @author Luiz Henrique
 */
public class ZLog {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private static boolean defaultPrintOnConsole = true;
    private static boolean defaultPrintOwnerClassMainOnConsole = false;
    private static boolean defaultPrintDateTimeOnConsole = false;
    private static boolean defaultPrintTypeOnConsole = false;
    private static boolean defaultPrintTagOnConsole = false;
    private static ZLogFormatter defaultFormatter = new ZLogDefaultFormatter();
    private static ZLogWriter defaultWriter = null;
    
    private static ZLogger defaultLogger;
    public static ZLogPrinter info;
    public static ZLogPrinter warn;
    public static ZLogPrinter error;
    
    //==========================================================================
    //INICIALIZADOR
    //==========================================================================
    static {
        ZLogger logger = new ZDefaultLogger();
        setDefaultLogger(logger);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public static ZLogPrinter i(Class<?> ownerClass){
        return new ZLogger(ownerClass).getInfo();
    }
    
    public static ZLogPrinter i(Object owner){
        return new ZLogger(owner).getInfo();
    }
    
    public static ZLogPrinter w(Class<?> ownerClass){
        return new ZLogger(ownerClass).getWarn();
    }
    
    public static ZLogPrinter w(Object owner){
        return new ZLogger(owner).getWarn();
    }
    
    public static ZLogPrinter e(Class<?> ownerClass){
        return new ZLogger(ownerClass).getError();
    }
    
    public static ZLogPrinter e(Object owner){
        return new ZLogger(owner).getError();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public static boolean isDefaultPrintOnConsole() {
        return defaultPrintOnConsole;
    }
    public static void setDefaultPrintOnConsole(boolean defaultPrintOnConsole) {
        ZLog.defaultPrintOnConsole = defaultPrintOnConsole;
    }

    public static boolean isDefaultPrintOwnerClassMainOnConsole() {
        return defaultPrintOwnerClassMainOnConsole;
    }
    public static void setDefaultPrintOwnerClassMainOnConsole(boolean defaultPrintOwnerClassMainOnConsole) {
        ZLog.defaultPrintOwnerClassMainOnConsole = defaultPrintOwnerClassMainOnConsole;
    }

    public static boolean isDefaultPrintDateTimeOnConsole() {
        return defaultPrintDateTimeOnConsole;
    }
    public static void setDefaultPrintDateTimeOnConsole(boolean defaultPrintDateTimeOnConsole) {
        ZLog.defaultPrintDateTimeOnConsole = defaultPrintDateTimeOnConsole;
    }

    public static boolean isDefaultPrintTypeOnConsole() {
        return defaultPrintTypeOnConsole;
    }
    public static void setDefaultPrintTypeOnConsole(boolean defaultPrintTypeOnConsole) {
        ZLog.defaultPrintTypeOnConsole = defaultPrintTypeOnConsole;
    }

    public static boolean isDefaultPrintTagOnConsole() {
        return defaultPrintTagOnConsole;
    }
    public static void setDefaultPrintTagOnConsole(boolean defaultPrintTagOnConsole) {
        ZLog.defaultPrintTagOnConsole = defaultPrintTagOnConsole;
    }

    public static ZLogFormatter getDefaultFormatter() {
        return defaultFormatter;
    }
    public static void setDefaultFormatter(ZLogFormatter defaultFormatter) {
        ZLog.defaultFormatter = defaultFormatter;
    }

    public static ZLogWriter getDefaultWriter() {
        return defaultWriter;
    }
    public static void setDefaultWriter(ZLogWriter defaultWriter) {
        ZLog.defaultWriter = defaultWriter;
        getDefaultLogger().setWriter(defaultWriter);
    }

    public static ZLogger getDefaultLogger() {
        return defaultLogger;
    }
    public static void setDefaultLogger(ZLogger defaultLogger) {
        if (defaultLogger==null){
            info = null;
            warn = null;
            error = null;
        } else {
            info = defaultLogger.getInfo();
            warn = defaultLogger.getWarn();
            error = defaultLogger.getError();
        }
        ZLog.defaultLogger = defaultLogger;
    }
    
}
