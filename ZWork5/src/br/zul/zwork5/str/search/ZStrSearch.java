package br.zul.zwork5.str.search;

import br.zul.zwork5.util.ZList;
import br.zul.zwork5.util.ZStreamUtils;
import br.zul.zwork5.util.ZUtil;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author Luiz Henrique
 */
public class ZStrSearch {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    protected String string;
    protected ZList<String> patternList;
    protected ZList<String> patternListToIgnore;
    //protected boolean caseSensitive;
    protected boolean backwards;
    protected boolean compulsive;
    protected Integer offset;
    
    private boolean started;
    private ZStrSearchResult next;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    protected ZStrSearch(){
        
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.string);
        hash = 83 * hash + Objects.hashCode(this.patternList);
        hash = 83 * hash + Objects.hashCode(this.patternListToIgnore);
        hash = 83 * hash + (this.backwards ? 1 : 0);
        hash = 83 * hash + (this.compulsive ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.offset);
        hash = 83 * hash + (this.started ? 1 : 0);
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
        final ZStrSearch other = (ZStrSearch) obj;
        if (this.backwards != other.backwards) {
            return false;
        }
        if (this.compulsive != other.compulsive) {
            return false;
        }
        if (!Objects.equals(this.string, other.string)) {
            return false;
        }
        if (!Objects.equals(this.patternList, other.patternList)) {
            return false;
        }
        if (!Objects.equals(this.patternListToIgnore, other.patternListToIgnore)) {
            return false;
        }
        return Objects.equals(this.offset, other.offset);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZStrSearchResult next(){
        ZStrSearchResult current;
        if (started){
            current = next;
        } else {
            current = nextResult();
        }
        if (current!=null) next = nextResult();
        return current;
    }
    
    public boolean hasNext(){
        if (started){
            return next!=null;
        } else {
            next = nextResult();
            return next!=null;
        }
    }
    
    public ZList<ZStrSearchResult> listAll(){
        ZList<ZStrSearchResult> resultList = new ZList<>();
        while (hasNext()){
            resultList.add(next());
        }
        return resultList;
    }
    
    public Stream<ZStrSearchResult> stream(){
        Iterator<ZStrSearchResult> sourceIterator = new Iterator<ZStrSearchResult>() {
            @Override
            public boolean hasNext() {
                return ZStrSearch.this.hasNext();
            }
            @Override
            public ZStrSearchResult next() {
                return ZStrSearch.this.next();
            }
        };
        return ZStreamUtils.fromIterator(sourceIterator);
    }

    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private ZStrSearchResult nextResult() {
        started = true;
        ZList<ZStrSearchResult> resultList = searchPatternList(patternList);
        if (resultList.isEmpty()) {
            offset = string.length();
            return null;
        } else {
            int newOffset = nextOffset(resultList);
            removePatternsToIgnore(resultList);
            offset = newOffset;
        }
        if (!resultList.isEmpty()){
            offset = nextOffset(resultList);
        } else {
            if (offset<string.length()){
                return nextResult();
            }
        }
        sortBy(resultList);
        return resultList.first().get();
    }

    private ZList<ZStrSearchResult> searchPatternList(ZList<String> patternList) {
        ZList<ZStrSearchResult> resultList = new ZList<>();
        for (String pattern:patternList){
            ZStrSearchResult result = searchPattern(pattern);
            if (result!=null) resultList.add(result);
        }
        return resultList;
    }

    private ZStrSearchResult searchPattern(String pattern) {
        int startIndex = indexOf(pattern);
        if (startIndex==-1) return null;
        int endIndex = startIndex + pattern.length();
        ZStrSearchResult result = new ZStrSearchResult();
        result.startIndex = startIndex;
        result.endIndex = endIndex;
        result.pattern = pattern;
        return result;
    }

    private int nextOffset(ZList<ZStrSearchResult> resultList) {
        if (ZUtil.hasContent(resultList)){
            if (backwards){
                return resultList.stream().map(this::getIndexToNextOffset).reduce(Math::max).get();
            } else {
                return resultList.stream().map(this::getIndexToNextOffset).reduce(Math::min).get();
            }
        } else {
            return offset;
        }
    }
    
    private int getIndexToNextOffset(ZStrSearchResult result){
        int index;
        if (compulsive){
            index = result.getStartIndex();
            if (backwards){
                index--;
            } else {
                index++;
            }
        } else {
            index = result.getEndIndex();
        }
        return index;
    }

    private void removePatternsToIgnore(ZList<ZStrSearchResult> resultList) {
        if (ZUtil.hasContent(patternListToIgnore)){
            ZList<ZStrSearchResult> resultListToIgnore = searchPatternList(patternListToIgnore);
            resultList.removeIf((result)->hasConflict(result, resultListToIgnore));
        }
    }

    private int indexOf(String pattern) {
        if (backwards){
            if (offset==null){
                return string.lastIndexOf(pattern);
            } else {
                return string.lastIndexOf(string, offset);
            }
        } else {
            if (offset==null){
                return string.indexOf(pattern);
            } else {
                return string.indexOf(pattern, offset);
            }
        }
    }

    private boolean hasConflict(ZStrSearchResult result, ZList<ZStrSearchResult> resultListToIgnore) {
        for (ZStrSearchResult resultToIgnore:resultListToIgnore){
            if (hasConflict(result, resultToIgnore)){
                return true;
            }
        }
        return false;
    }

    private boolean hasConflict(ZStrSearchResult result, ZStrSearchResult resultToIgnore) {
        if (result.startIndex<=resultToIgnore.startIndex&&result.endIndex>=resultToIgnore.startIndex){
            return true;
        }
        return result.startIndex<=resultToIgnore.endIndex&&result.endIndex>=resultToIgnore.endIndex;
    }

    private void sortBy(ZList<ZStrSearchResult> resultList) {
        if (backwards){
            resultList.sort((a,b)->b.getEndIndex()-a.getEndIndex());
        } else {
            resultList.sort((a,b)->a.getStartIndex() - b.getStartIndex());
        }
    }
    
}
