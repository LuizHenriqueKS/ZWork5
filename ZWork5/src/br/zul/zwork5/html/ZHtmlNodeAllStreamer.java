package br.zul.zwork5.html;

import br.zul.zwork4.util.ZStreamUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

/**
 *
 * @author luizh
 */
class ZHtmlNodeAllStreamer {

    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZHtmlNode node;
    private final Queue<Iterator<ZHtmlNode>> iteratorQueue;
    
    private boolean goToNext;
    private boolean closed;
    private ZHtmlNode next;
    private Iterator<ZHtmlNode> currentIterator;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlNodeAllStreamer(ZHtmlNode node) {
        this.node = node;
        this.iteratorQueue = new LinkedList<>();
        this.goToNext = true;
        this.currentIterator = node.streamNodes().iterator();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Stream<ZHtmlNode> stream() {
        return ZStreamUtils.fromIterator(new Iterator<ZHtmlNode>(){
            @Override
            public boolean hasNext() {
                return ZHtmlNodeAllStreamer.this.hasNext();
            }
            @Override
            public ZHtmlNode next() {
                return ZHtmlNodeAllStreamer.this.next();
            }
        });
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private boolean hasNext(){
        goToNextIfNeed();
        return next!=null;
    }
    
    private ZHtmlNode next(){
        if (hasNext()){
            return removeNext();
        } else {
            return null;
        }
    }
    
    private ZHtmlNode removeNext(){
        ZHtmlNode result = next;
        next = null;
        goToNext = true;
        return result;
    }
    
    private void goToNextIfNeed(){
        if (goToNext){
            goToNext();
        }
    }
    
    private void goToNext(){
        if (closed){
            goToNext = false;
            next = null;
        } else if (currentIterator.hasNext()){
            goToNext = false;
            next = currentIterator.next();
            if (next==null) {
                goToNext();
            } else {
                iteratorQueue.add(next.streamNodes().iterator());
            }
        } else if (iteratorQueue.isEmpty()) {
            closed = true;
            goToNext();
        } else {
            currentIterator = iteratorQueue.remove();
            goToNext();
        }
    }
    
}
