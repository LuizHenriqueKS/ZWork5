package br.zul.zwork5.log;

import br.zul.zwork5.util.ZStrUtils;
import br.zul.zwork5.util.ZUtil;

/**
 *
 * @author Luiz Henrique
 */
public class ZLogDefaultFormatter implements ZLogFormatter {

    @Override
    public String format(ZLogMsg msg, boolean printOnConsole) {
        if (printOnConsole){
            return formatToConsole(msg);
        } else {
            return formatToFile(msg);
        }
    }

    private String formatToFile(ZLogMsg msg) {
        StringBuilder builder = new StringBuilder();
        appendType(msg, builder, "", ";");
        appendTime(msg, builder, "[", "];");
        appendOwnerClassName(msg, builder, "(", ");");
        appendTag(msg, builder, "#", ";");
        appendMessage(msg, builder, "", ";");
        appendThrowable(msg, builder, "", ";");
        return builder.toString();
    }

    private String formatToConsole(ZLogMsg msg) {
        StringBuilder builder = new StringBuilder();
        if (msg.getLogger().isPrintTypeOnConsole()) {
            appendType(msg, builder, " ", " -");
        }
        if (msg.getLogger().isPrintDateTimeOnConsole()) {
            appendTime(msg, builder, " [", "]");
        }
        if (msg.getLogger().isPrintOwnerClassMainOnConsole()) {
            appendOwnerClassName(msg, builder, " (", ")");
        }
        if (msg.getLogger().isPrintTagOnConsole()){
            appendTag(msg, builder, " #", " -");
        }
        appendMessage(msg, builder, " ", "");
        appendThrowable(msg, builder, " \r\n", "");
        if (builder.length()==0) return "";
        return builder.toString().substring(1);
    }
    
    private void appendType(ZLogMsg msg, StringBuilder builder, String start, String end){
        if (msg.getType()!=null){
            builder.append(start);
            builder.append(msg.getType());
            builder.append(end);
        }
    }
    
    private void appendTime(ZLogMsg msg, StringBuilder builder, String start, String end) {
        if (msg.getDateTime()!=null){
            builder.append(start);
            builder.append(msg.getDateTime().toString());
            builder.append(end);
        }
    }

    private void appendMessage(ZLogMsg msg, StringBuilder builder, String start, String end) {
        if (msg.getMessage()!=null){
            builder.append(start);
            builder.append(ZStrUtils.format(msg.getMessage(), msg.getArgs()));
            builder.append(end);
        }
    }

    private void appendOwnerClassName(ZLogMsg msg, StringBuilder builder, String start, String end) {
        if (msg.getLogger()!=null&&msg.getLogger().getOwnerClass()!=null){
            builder.append(start);
            builder.append(msg.getLogger().getOwnerClass().getName());
            builder.append(end);
        }
    }

    private void appendThrowable(ZLogMsg msg, StringBuilder builder, String start, String end) {
        if (msg.getThrowable()!=null){
            builder.append(start);
            builder.append(ZUtil.getStackTrace(msg.getThrowable()));
            builder.append(end);
        }
    }
    
    private void appendTag(ZLogMsg msg, StringBuilder builder, String start, String end) {
       if (msg.getLogger()!=null&&msg.getLogger().getTag()!=null){
            builder.append(start);
            builder.append(msg.getLogger().getTag());
            builder.append(end);
        }
    }
    
}
