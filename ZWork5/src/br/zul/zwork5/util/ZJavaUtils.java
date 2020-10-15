package br.zul.zwork5.util;

/**
 *
 * @author luizh
 */
public class ZJavaUtils {
    

    public static String unescapeJava(String escaped) {
        if (!escaped.contains("\\u")) {
            return escaped;
        }

        String processed = "";

        int position = escaped.indexOf("\\u");
        while (position != -1) {
            if (position != 0) {
                processed += escaped.substring(0, position);
            }
            try {
                String token = escaped.substring(position + 2, position + 6);
                processed += (char) Integer.parseInt(token, 16);
                escaped = escaped.substring(position + 6);
            } catch (Exception e) {
                String token = escaped.substring(position + 2, position + 4);
                processed += (char) Integer.parseInt(token, 16);
                escaped = escaped.substring(position + 4);
            }
            position = escaped.indexOf("\\u");
        }
        processed += escaped;

        return processed;
    }
    
}
