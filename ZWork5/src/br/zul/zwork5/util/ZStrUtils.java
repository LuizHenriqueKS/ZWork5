package br.zul.zwork5.util;

import br.zul.zwork5.str.ZStr;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.Objects;

/**
 *
 * @author Luiz Henrique
 */
public class ZStrUtils {
    
    public static final String ONLY_LETTERS_OF_ALPHABET_UPPERCASE_AND_LOWERCASE = "ABCDEFGHIJKLMNOPQRSTUVXWYZabcdefghijklmnopqrstuvxywz";
    public static final String ONLY_LETTERS_OF_ALPHABET_UPPERCASE_AND_LOWERCASE_AND_NUMBERS = "ABCDEFGHIJKLMNOPQRSTUVXWYZabcdefghijklmnopqrstuvxywz0123456789";

    public static String format(String pattern, Object... args){
        if (ZUtil.hasContent(args)){
            return MessageFormat.format(pattern, args);
        } else {
            return pattern;
        }
    }
    
    public static String simpleFormat(String pattern, Object... args){
        if (ZUtil.hasContent(pattern)){
            return String.format(pattern, args);
        } else {
            return pattern;
        }
    }
    
    public static String normalize(String string){
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
    public static boolean hasContent(String str){
        return !(str==null||str.isEmpty());
    }
    
    public static ZStr[] parseArray(String... array){
        ZStr result[] = new ZStr[array.length];
        for (int i=0;i<array.length;i++){
            String string = array[i];
            result[i] = new ZStr(string);
        }
        return result;
    }

    public static String only(String string, String alphabet) {
        String result = string;
        for (char c : string.toCharArray()) {
            if (!alphabet.contains("" + c)) {
                result = result.replace("" + c, "");
            }
        }
        return result;
    }

    public static String firstCharToUpperCase(String string) {
        if (hasContent(string)){
            if (string.length()>1){
                string = string.substring(0, 1).toUpperCase() + string.substring(1);
            } else if (string.length()==1) {
                string = string.toUpperCase();
            }
        }
        return string;
    }

    public static String firstCharToLowerCase(String string) {
        if (hasContent(string)){
            if (string.length()>1){
                string = string.substring(0, 1).toLowerCase()+ string.substring(1);
            } else if (string.length()==1) {
                string = string.toLowerCase();
            }
        }
        return string;
    }
    
    public static boolean equalsIgnoreCase(String str1, String str2) {
        String s1 = str1==null?null:str1.toLowerCase();
        String s2 = str2==null?null:str2.toLowerCase();
        return Objects.equals(s1, s2);
    }

    public static int hashCodeIgnoreCase(String str) {
        if (str==null){
            return Objects.hashCode(str);
        } else {
            return Objects.hashCode(str.toLowerCase());
        }
    }

    public static boolean isLong(String str) {
        try {
            Long.valueOf(str);
            return true;
        } catch (NullPointerException|NumberFormatException e){
            return false;
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (NullPointerException|NumberFormatException e){
            return false;
        }
    }

    public static String firstNotEmpty(String... strs) {
        for (String str:strs){
            if (hasContent(str)){
                return str;
            }
        }
        return null;
    }
    
}
