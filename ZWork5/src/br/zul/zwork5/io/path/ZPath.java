package br.zul.zwork5.io.path;

/**
 *
 * @author Luiz Henrique
 */
public interface ZPath extends Iterable<String>{

    public int size();
    public int firstIndex();
    public int lastIndex();
    public String get(int index) throws IndexOutOfBoundsException;
    public String set(int index, String value);
    public String getExtension();
    public ZPath fromIndex(int index);
    public ZPath tillIndex(int index);
    public ZPath removeFirst();
    public ZPath removeLast();
    public ZPath getChild(String path);
    public ZPath getParent();
    public String getName();
    
    @Override
    public String toString();
    
    default public String first() throws IndexOutOfBoundsException{
        return get(firstIndex());
    }
    
    default public String last()  throws IndexOutOfBoundsException{
        return get(lastIndex());
    }
    
}
