package br.zul.zwork5.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlUtils {
    
    public static String unescapeHtml(String str) {
        if (str==null) return str;
        Map<String, String> replaceMap = getHtmlMapUnescape();
        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            str = str.replace(e.getKey(), e.getValue());
        }
        return str;
    }
    
    public static String escapeHtml(String str) {
        if (str==null) return str;
        Map<String, String> replaceMap = new TreeMap<>((a,b)->a.length()-b.length());
        replaceMap.putAll(getHtmlMapUnescape());
        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            if (!e.getValue().isEmpty()){
                str = str.replace(e.getValue(), e.getKey());
            }
        }
        return str;
    }

    private static Map<String, String> getHtmlMapUnescape() {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("&#160;", " ");
        replaceMap.put("&#039;", "'");
        replaceMap.put("&#39;", "'");
        replaceMap.put("&Agrave;", "À");
        replaceMap.put("&#192;", "À");
        replaceMap.put("&#193;", "Á");
        replaceMap.put("&Aacute;", "Á");
        replaceMap.put("&#194;", "Â");
        replaceMap.put("&Acirc;", "Â");
        replaceMap.put("&#195;", "Ã");
        replaceMap.put("&Atilde;", "Ã");
        replaceMap.put("&#196;", "Ä");
        replaceMap.put("&Auml;", "Ä");
        replaceMap.put("&#197;", "Å");
        replaceMap.put("&Aring;", "Å");
        replaceMap.put("&#198;", "Æ");
        replaceMap.put("&AElig;", "Æ");
        replaceMap.put("&#199;", "Ç");
        replaceMap.put("&Ccedil;", "Ç");
        replaceMap.put("&#200;", "È");
        replaceMap.put("&Egrave;", "È");
        replaceMap.put("&#201;", "É");
        replaceMap.put("&Eacute;", "É");
        replaceMap.put("&#202;", "Ê");
        replaceMap.put("&Ecirc;", "Ê");
        replaceMap.put("&#203;", "Ë");
        replaceMap.put("&Euml;", "Ë");
        replaceMap.put("&#204;", "Ì");
        replaceMap.put("&Igrave;", "Ì");
        replaceMap.put("&#205;", "Í");
        replaceMap.put("&Iacute;", "Í");
        replaceMap.put("&#206;", "Î");
        replaceMap.put("&Icirc;", "Î");
        replaceMap.put("&#207;", "Ï");
        replaceMap.put("&Iuml;", "Ï");
        replaceMap.put("&#208;", "Ð");
        replaceMap.put("&ETH;", "Ð");
        replaceMap.put("&#209;", "Ñ");
        replaceMap.put("&Ntilde;", "Ñ");
        replaceMap.put("&#210;", "Ò");
        replaceMap.put("&Ograve;", "Ò");
        replaceMap.put("&#211;", "Ó");
        replaceMap.put("&Oacute;", "Ó");
        replaceMap.put("&#212;", "Ô");
        replaceMap.put("&Ocirc;", "Ô");
        replaceMap.put("&#213;", "Õ");
        replaceMap.put("&Otilde;", "Õ");
        replaceMap.put("&#214;", "Ö");
        replaceMap.put("&Ouml;", "Ö");
        replaceMap.put("&#215;", "×");
        replaceMap.put("&times;", "×");
        replaceMap.put("&#216;", "Ø");
        replaceMap.put("&Oslash;", "Ø");
        replaceMap.put("&#217;", "Ù");
        replaceMap.put("&Ugrave;", "Ù");
        replaceMap.put("&#218;", "Ú");
        replaceMap.put("&Uacute;", "Ú");
        replaceMap.put("&#219;", "Û");
        replaceMap.put("&Ucirc;", "Û");
        replaceMap.put("&#220;", "Ü");
        replaceMap.put("&Uuml;", "Ü");
        replaceMap.put("&#221;", "Ý");
        replaceMap.put("&Yacute;", "Ý");
        replaceMap.put("&#222;", "Þ");
        replaceMap.put("&THORN;", "Þ");
        replaceMap.put("&#223;", "ß");
        replaceMap.put("&szlig;", "ß");
        replaceMap.put("&#224;", "à");
        replaceMap.put("&agrave;", "à");
        replaceMap.put("&#225;", "á");
        replaceMap.put("&aacute;", "á");
        replaceMap.put("&#226;", "â");
        replaceMap.put("&acirc;", "â");
        replaceMap.put("&#227;", "ã");
        replaceMap.put("&atilde;", "ã");
        replaceMap.put("&#228;", "ä");
        replaceMap.put("&auml;", "ä");
        replaceMap.put("&#229;", "å");
        replaceMap.put("&aring;", "å");
        replaceMap.put("&#230;", "æ");
        replaceMap.put("&aelig;", "æ");
        replaceMap.put("&#231;", "ç");
        replaceMap.put("&ccedil;", "ç");
        replaceMap.put("&#232;", "è");
        replaceMap.put("&egrave;", "è");
        replaceMap.put("&#233;", "é");
        replaceMap.put("&eacute;", "é");
        replaceMap.put("&#234;", "ê");
        replaceMap.put("&ecirc;", "ê");
        replaceMap.put("&#235;", "ë");
        replaceMap.put("&euml;", "ë");
        replaceMap.put("&#236;", "ì");
        replaceMap.put("&igrave;", "ì");
        replaceMap.put("&#237;", "í");
        replaceMap.put("&iacute;", "í");
        replaceMap.put("&#238;", "î");
        replaceMap.put("&icirc;", "î");
        replaceMap.put("&#239;", "ï");
        replaceMap.put("&iuml;", "ï");
        replaceMap.put("&#240;", "ð");
        replaceMap.put("&eth;", "ð");
        replaceMap.put("&#241;", "ñ");
        replaceMap.put("&ntilde;", "ñ");
        replaceMap.put("&#242;", "ò");
        replaceMap.put("&ograve;", "ò");
        replaceMap.put("&#243;", "ó");
        replaceMap.put("&oacute;", "ó");
        replaceMap.put("&#244;", "ô");
        replaceMap.put("&ocirc;", "ô");
        replaceMap.put("&#245;", "õ");
        replaceMap.put("&otilde;", "õ");
        replaceMap.put("&#246;", "ö");
        replaceMap.put("&ouml;", "ö");
        replaceMap.put("&#247;", "÷");
        replaceMap.put("&divide;", "÷");
        replaceMap.put("&#248;", "ø");
        replaceMap.put("&oslash;", "ø");
        replaceMap.put("&#249;", "ù");
        replaceMap.put("&ugrave;", "ù");
        replaceMap.put("&#250;", "ú");
        replaceMap.put("&uacute;", "ú");
        replaceMap.put("&#251;", "û");
        replaceMap.put("&ucirc;", "û");
        replaceMap.put("&#252;", "ü");
        replaceMap.put("&uuml;", "ü");
        replaceMap.put("&#253;", "ý");
        replaceMap.put("&yacute;", "ý");
        replaceMap.put("&#254;", "þ");
        replaceMap.put("&thorn;", "þ");
        replaceMap.put("&#255;", "ÿ");
        replaceMap.put("&yuml;", "ÿ");
        replaceMap.put("&quot;", "\"");
        replaceMap.put("&amp;", "&");
        replaceMap.put("&gt;", ">");
        replaceMap.put("&lt;", "<");
        replaceMap.put("&ensp;", " ");
        replaceMap.put("&emsp;", " ");
        replaceMap.put("&thinsp;", " ");
        replaceMap.put("&zwnj;", "");
        replaceMap.put("&zwj;", "");
        replaceMap.put("&lrm;", "");
        replaceMap.put("&rlm;", "");
        replaceMap.put("&ndash;", "–");
        replaceMap.put("&mdash;", "—");
        replaceMap.put("&ldquo;", "“");
        replaceMap.put("&rdquo;", "”");
        replaceMap.put("&bdquo;", "„");
        replaceMap.put("&lsquo;", "‘");
        replaceMap.put("&rsquo;", "’");
        replaceMap.put("&sbquo;", "‚");
        replaceMap.put("&dagger;", "†");
        replaceMap.put("&Dagger;", "‡");
        replaceMap.put("&bull;", "•");
        replaceMap.put("&hellip;", "…");
        replaceMap.put("&permil;", "‰");
        replaceMap.put("&prime;", "′");
        replaceMap.put("&Prime;", "″");
        replaceMap.put("&lsaquo;", "‹");
        replaceMap.put("&rsaquo;", "›");
        replaceMap.put("&oline;", "‾");
        replaceMap.put("&frasl;", "⁄");
        replaceMap.put("&nbsp;", " ");
        return replaceMap;
    }
    
}
