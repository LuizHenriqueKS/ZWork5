package br.zul.zwork5.random;

import br.zul.zwork5.currency.ZCurrency;
import br.zul.zwork5.exception.ZParseException;
import br.zul.zwork5.exception.ZUnexpectedException;
import br.zul.zwork5.timestamp.ZDate;
import br.zul.zwork5.timestamp.ZDateTime;
import br.zul.zwork5.timestamp.ZTime;
import br.zul.zwork5.util.ZUtil;
import java.math.BigDecimal;
import java.rmi.UnexpectedException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author Luiz Henrique
 */
public class ZRandom {
    
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final Random random;
    private String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZRandom(){
        this.random = new SecureRandom();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public int nextInt(){
        return this.random.nextInt();
    }
    
    public int nextInt(int bound){
        return this.random.nextInt(bound);
    }
    
    /**
     * Ex: nextInt(10, 15) -> 10, 11, 12, 13, 14, 15
     * 
     * 
     * @param min
     * @param max
     * @return 
     */
    public int nextInt(int min, int max){
        return nextInt(max-min+1) + min;
    }
    
    public long nextLong(){
        return this.random.nextLong();
    }
    
    public long nextLong(long min, long max){
        long randomLong = Math.abs(nextLong()) % (max-min+1);
        return  randomLong + min;
    }
    
    public boolean nextBool(){
        return this.random.nextBoolean();
    }
    
    public String nextStr(int min, int max){
        int bound = min==max?min:nextInt(min, max);
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<bound;i++){
            int index = nextInt(alphabet.length());
            String ch = alphabet.substring(index, index+1);
            builder.append(ch);
        }
        if (builder.length()>max){
            return builder.toString().substring(0, max);
        }
        return builder.toString();
    }
    
    public String nextStr(){
        return nextStr(10, 255);
    }
    
    public String nextStr(int length){
        return nextStr(length, length);
    }

    public Float nextFloat() {
        return this.random.nextFloat();
    }

    public Double nextDouble() {
        return this.random.nextDouble();
    }

    public ZDate nextDate() {
        ZDate min = new ZDate().add(ZDate.YEAR, -10);
        ZDate max = new ZDate().add(ZDate.YEAR, 10);
        return ZDate.random(min, max);
    }

    public ZDate nextDate(ZDate min, ZDate max) {
        return ZDate.random(min, max);
    }

    public ZDateTime nextDateTime() {
        ZDateTime min = new ZDateTime().add(ZDateTime.YEAR, -10);
        ZDateTime max = new ZDateTime().add(ZDateTime.YEAR, 10);
        return ZDateTime.random(min, max);
    }
    
    public ZDateTime nextDateTime(ZDateTime min, ZDateTime max){
        return ZDateTime.random(min, max);
    }

    public ZTime nextTime() {
        try {
            ZTime min = new ZTime("00:00:00");
            ZTime max = new ZTime("23:59:59");
            return nextTime(min, max);
        } catch (ZParseException ex){
            throw new ZUnexpectedException(ex);
        }
    }
    
    public ZTime nextTime(ZTime min, ZTime max){
        return ZTime.random(min, max);
    }
    
    public BigDecimal nextBigDecimal(){
        return new BigDecimal(nextDouble());
    }
    
    public ZCurrency nextCurrency(){
        return new ZCurrency(nextBigDecimal());
    }
    
    public <T> T nextObj(Collection<T> collection) {
        if (ZUtil.hasContent(collection)){
            if (collection.size()>1){
                int index = nextInt(collection.size());
                return collection.stream().skip(index).findFirst().get();
            } else if (collection.size()==1){
                return collection.iterator().next();
            }
        }
        return null;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getAlphabet() {
        return alphabet;
    }
    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }
    
}
