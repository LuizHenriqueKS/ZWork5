package br.zul.zwork5.log;

import java.io.PrintStream;

/**
 *
 * @author Luiz Henrique
 */
public class ZLogger {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    public ZLogPrinter info;
    public ZLogPrinter warn;
    public ZLogPrinter error;
    
    private final Class<?> ownerClass;
    private final Object owner;
    
    private final String tag;
    
    private ZLogFormatter formatter = ZLog.getDefaultFormatter();
    private ZLogWriter writer = ZLog.getDefaultWriter();
    private boolean printOnConsole = ZLog.isDefaultPrintOnConsole();
    private boolean printOwnerClassMainOnConsole = ZLog.isDefaultPrintOwnerClassMainOnConsole();
    private boolean printDateTimeOnConsole = ZLog.isDefaultPrintDateTimeOnConsole();
    private boolean printTypeOnConsole = ZLog.isDefaultPrintTypeOnConsole();
    private boolean printTagOnConsole = ZLog.isDefaultPrintTagOnConsole();
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZLogger(Class<?> ownerClass){
        this.ownerClass = ownerClass;
        this.owner = null;
        this.tag = null;
        init();
    }
    
    public ZLogger(Object owner){
        this.owner = owner;
        this.ownerClass = owner.getClass();
        this.tag = null;
        init();
    } 

    public ZLogger(Class<?> ownerClass, String tag) {
        this.ownerClass = ownerClass;
        this.owner = null;
        this.tag = tag;
        init();
    }

    public ZLogger(Object owner, String tag) {
        this.ownerClass = owner.getClass();
        this.owner = owner;
        this.tag = tag;
        init();
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(){
        initInfoPrinter();
        initWarnPrinter();
        initErrorPrinter();
    }
    
    private void initInfoPrinter(){
        info = initPrinter(System.out, ZLogType.INFO);
    }
    
    private void initWarnPrinter(){
        warn = initPrinter(System.out, ZLogType.WARN);
    }
    
    private void initErrorPrinter(){
        error = initPrinter(System.err, ZLogType.ERROR);
    }

    private ZLogPrinter initPrinter(PrintStream printStream, ZLogType type) {
        return new ZDefaultLogPrinter(printStream, type);
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void printOnConsoleIfCan(PrintStream printStream, String formattedMessage) {
        if (isPrintOnConsole()){
            printStream.print(formattedMessage);
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZLogFormatter getFormatter() {
        return formatter;
    }
    public void setFormatter(ZLogFormatter formatter) {
        this.formatter = formatter;
    }

    public ZLogWriter getWriter() {
        return writer;
    }
    public void setWriter(ZLogWriter writer) {
        this.writer = writer;
    }

    public boolean isPrintOnConsole() {
        return printOnConsole;
    }
    public void setPrintOnConsole(boolean printOnConsole) {
        this.printOnConsole = printOnConsole;
    }

    public boolean isPrintOwnerClassMainOnConsole() {
        return printOwnerClassMainOnConsole;
    }
    public void setPrintOwnerClassMainOnConsole(boolean printOwnerClassMainOnConsole) {
        this.printOwnerClassMainOnConsole = printOwnerClassMainOnConsole;
    }

    public Class<?> getOwnerClass() {
        return ownerClass;
    }

    public Object getOwner() {
        return owner;
    }

    public String getTag() {
        return tag;
    }

    public ZLogPrinter getInfo() {
        return info;
    }
    public void setInfo(ZLogPrinter info) {
        this.info = info;
    }

    public ZLogPrinter getWarn() {
        return warn;
    }
    public void setWarn(ZLogPrinter warn) {
        this.warn = warn;
    }

    public ZLogPrinter getError() {
        return error;
    }
    public void setError(ZLogPrinter error) {
        this.error = error;
    }

    public boolean isPrintDateTimeOnConsole() {
        return printDateTimeOnConsole;
    }
    public void setPrintDateTimeOnConsole(boolean printDateTimeOnConsole) {
        this.printDateTimeOnConsole = printDateTimeOnConsole;
    }

    public boolean isPrintTypeOnConsole() {
        return printTypeOnConsole;
    }
    public void setPrintTypeOnConsole(boolean printTypeOnConsole) {
        this.printTypeOnConsole = printTypeOnConsole;
    }

    public boolean isPrintTagOnConsole() {
        return printTagOnConsole;
    }
    public void setPrintTagOnConsole(boolean printTagOnConsole) {
        this.printTagOnConsole = printTagOnConsole;
    }
    
}
