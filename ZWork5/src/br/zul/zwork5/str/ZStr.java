package br.zul.zwork5.str;

import br.zul.zwork5.str.search.ZStrSearch;
import br.zul.zwork5.str.search.ZStrSearchResult;
import br.zul.zwork5.str.search.ZStrSearcher;
import br.zul.zwork5.util.ZUtil;
import br.zul.zwork5.value.ZValue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Luiz Henrique
 */
public class ZStr implements ZValue {
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public static boolean DEFAULT_CASE_SENSITIVE = true;
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final String str;
    private final boolean caseSensitive;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZStr(String string) {
        this.str = string;
        this.caseSensitive = DEFAULT_CASE_SENSITIVE;
    }

    public ZStr(String string, boolean caseSensitive) {
        this.str = string;
        this.caseSensitive = caseSensitive;
    }
    
    public ZStr(ZStr str){
        this.str = str.str;
        this.caseSensitive = str.caseSensitive;
    }
    
    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString(){
        return str;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        if (this.str==null||caseSensitive){
            hash = 41 * hash + Objects.hashCode(this.str);
        } else {
            hash = 41 * hash + Objects.hashCode(upperCase(this.str));
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZStr other = (ZStr) obj;
        if (caseSensitive){
            return Objects.equals(this.str, other.str);
        } else {
            return Objects.equals(upperCase(this.str), upperCase(other.str));
        }
    }

    @Override
    public Object asObject() {
        return toString();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public boolean equals(String string){
        if (caseSensitive){
            return str.equals(string);
        } else {
            return str.equalsIgnoreCase(string);
        }
    }
    
    public boolean isEmpty(){
        return str.isEmpty();
    }
    
    public ZStr copy(){
        return new ZStr(this);
    }
    
    public ZStrList split(String... pattern){
        ZStrSearch search = search(pattern);
        ZStrList list = new ZStrList();
        ZStrSearchResult result;
        int index = 0;
        while ((result = search.next())!=null){
            if (index!=result.getStartIndex()){
                list.add(str.substring(index, result.getStartIndex()));
            }
            index = result.getEndIndex();
        }
        if (index<str.length()) list.add(from(index));
        if (list.isEmpty()) list.add(this);
        return list;
    }
    
    public int count(String... pattern){
        ZStrSearch search = search(pattern);
        int count = 0;
        while (search.hasNext()){
            count++;
            search.next();
        }
        return count;
    }
    
    public ZStr from(ZStrSearcher searcher){
        ZStrSearch search = searcher.search(str);
        if (search.hasNext()){
            ZStrSearchResult result = search.next();
            return from(result.getEndIndex());
        }
        return this;
    }
    
    public ZStr fromWith(ZStrSearcher searcher){
        ZStrSearch search = searcher.search(str);
        if (search.hasNext()){
            ZStrSearchResult result = search.next();
            return from(result.getStartIndex());
        }
        return this;
    }
    
    public ZStr till(ZStrSearcher searcher){
        ZStrSearch search = searcher.search(str);
        if (search.hasNext()){
            ZStrSearchResult result = search.next();
            return till(result.getStartIndex());
        }
        return this;
    }
    
    public ZStr tillWith(ZStrSearcher searcher){
        ZStrSearch search = searcher.search(str);
        if (search.hasNext()){
            ZStrSearchResult result = search.next();
            return till(result.getEndIndex());
        }
        return this;
    }
    
    public ZStr from(String... pattern) {
        return from(searcher(pattern));
    }
    
    public ZStr fromWith(String... pattern) {
        return fromWith(searcher(pattern));
    }
    
    public ZStr fromLast(String... pattern){
        ZStrSearcher searcher = searcher(pattern);
        searcher.setBackwards(true);
        return from(searcher);
    }
    
    public ZStr till(String... pattern){
        return till(searcher(pattern));
    }
    
    public ZStr tillWith(String... pattern){
        return tillWith(searcher(pattern));
    }

    public ZStr from(int startIndex) {
        return new ZStr(str.substring(startIndex), caseSensitive);
    }
    
    public ZStr till(int endIndex){
        return new ZStr(str.substring(0, endIndex), caseSensitive);
    }
    
    public ZStr substr(int startIndex, int endIndex) {
        return new ZStr(str.substring(startIndex, endIndex), caseSensitive);
    }
    
    public ZStr substr(int startIndex){
        return from(startIndex);
    }

    public ZStr tillLast(String... pattern) {
        ZStrSearcher searcher = searcher(pattern);
        searcher.setBackwards(true);
        return till(searcher);
    }

    public ZStr tillLastWith(String... pattern) {
        ZStrSearcher searcher = searcher(pattern);
        searcher.setBackwards(true);
        return tillWith(searcher);
    }
    
    public ZStrSearcher searcher(String... pattern){
        ZStrSearcher builder = new ZStrSearcher(pattern);
        builder.setCaseSensitive(caseSensitive);
        return builder;
    }
    
    public ZStrSearch search(String... pattern){
        ZStrSearcher searcher = searcher(pattern);
        return searcher.search(str);
    }

    public boolean containsAny(String... patterns) {
        for (String pattern:patterns){
            if (str.contains(pattern)){
                return true;
            }
        }
        return false;
    }

    public boolean startsWith(String pattern) {
        if (caseSensitive){
            return str.startsWith(pattern);
        } else {
            return str.toLowerCase().startsWith(pattern.toLowerCase());
        }
    }

    public boolean endsWith(String pattern) {
        if (caseSensitive){
            return str.endsWith(pattern);
        } else {
            return str.toLowerCase().endsWith(pattern.toLowerCase());
        }
    }

    public ZStr trim() {
        return new ZStr(str.trim(), caseSensitive);
    }
    
    public ZStr append(Object... objs){
        StringBuilder builder = new StringBuilder();
        builder.append(str);
        for (Object obj:objs){
            builder.append(obj);
        }
        return new ZStr(builder.toString(), caseSensitive);
    }
    
    public ZStr prepend(Object... objs){
        StringBuilder builder = new StringBuilder();
        for (Object obj:objs){
            builder.append(obj);
        }
        builder.append(str);
        return new ZStr(builder.toString(), caseSensitive);
    }
    
    public int length(){
        return str.length();
    }
    
    public static Comparator<String> getCaseInsensitive(){
        return (a,b)->{
            if (a==null&&b==null) return 0;
            if (a==null) return -1;
            if (b==null) return 1;
            return String.CASE_INSENSITIVE_ORDER.compare(a, b);
        };
    }
    
    public int indexOf(String... pattern){
        ZStrSearch search = search(pattern);
        if (search.hasNext()){
            return search.next().getStartIndex();
        } else {
            return -1;
        }
    }
    
    public ZStr replace(String pattern, String replacement) {
        if (caseSensitive){
            return new ZStr(str.replace(pattern, replacement), caseSensitive);
        } else {
            String quoted = Pattern.quote(pattern);;
            return new ZStr(str.replaceAll("(?i)"+quoted, Matcher.quoteReplacement(replacement)), caseSensitive);
        }
    }

    public ZStr remove(String... patterns){
        ZStr result = this;
        for (String pattern:patterns){
            result = result.replace(pattern, "");
        }
        return result;
    }
    
    public ZStr removeRecursive(String... patterns){
        ZStr result = this;
        for (String pattern:patterns){
            result = result.replaceRecursive(pattern, "");
        }
        return result;
    }
    
    public ZStr replaceRecursive(String pattern, String replacement){
        ZStr result = this;
        ZStr old;
        for (int i=0;i<100000;i++){
            old = result;
            result = result.replace(pattern, replacement);
            if (old.equals(result)){
                break;
            }
        }
        return result;
    }
    
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private String upperCase(String string){
        if (ZUtil.hasContent(string)){
            return string.toUpperCase();
        } else {
            return string;
        }
    }

    public ZStr trimStart() {
        String localStr = str;
        final List<String> spaceList = Arrays.asList(" ", "\t");
        while (!localStr.isEmpty()){
            String ch = localStr.substring(0, 1);
            if (spaceList.contains(ch)){
                localStr = localStr.substring(1);
            } else {
                break;
            }
        }
        return new ZStr(localStr);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public boolean isCaseSensitive() {
        return caseSensitive;
    }
    public ZStr caseSensitive(boolean caseSensitive) {
        return new ZStr(str, caseSensitive);
    }

}
