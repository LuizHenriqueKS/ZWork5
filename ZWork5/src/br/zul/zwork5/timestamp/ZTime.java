package br.zul.zwork5.timestamp;

import br.zul.zwork5.exception.ZParseException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Luiz Henrique
 */
public class ZTime extends ZTimeStamp {
    
    //==========================================================================
    //VARIÁVEIS ESTÁTICA PÚBLICAS
    //==========================================================================
    public static final int AM_PM = 9;
    public static final int HOUR = 10;
    public static final int HOUR_OF_DAY = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int MILLISECOND = 14;
    public static final int ZONE_OFFSET = 15;
    public static final int DST_OFFSET = 16;
    public static final int FIELD_COUNT = 17;
    public static final int AM = 0;
    public static final int PM = 1;
    public static final int ALL_STYLES = 0;
    static final int STANDALONE_MASK = 32768;
    public static final int SHORT = 1;
    public static final int LONG = 2;
    public static final int NARROW_FORMAT = 4;
    public static final int NARROW_STANDALONE = 32772;
    public static final int SHORT_FORMAT = 1;
    public static final int LONG_FORMAT = 2;
    public static final int SHORT_STANDALONE = 32769;
    public static final int LONG_STANDALONE = 32770;
    
    public static String STANDART_FORMAT_1 = "HH:mm:ss Z";
    public static String STANDART_FORMAT_2 = "HH:mm:ss";
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZTime(){    
        super();
    }
    
    public ZTime(ZTimeStamp timeStamp){
        super(timeStamp.calendar);
    }

    public ZTime(Calendar calendar) {
        super(calendar);
    }

    public ZTime(Date date) {
        super(date);
    }

    public ZTime(long time) {
        super(time);
    }
 
    public ZTime(String format, String value) throws ZParseException {
        super(format, value);
    }

    public ZTime(String value) throws ZParseException {
        super(value.contains(" ")?STANDART_FORMAT_1:STANDART_FORMAT_2, value);
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString() {
        return format("HH:mm:ss Z");
    }
    
    @Override
    public ZTime add(int field, int number) {
        Calendar cal = asCalendar();
        cal.add(field, number);
        return new ZTime(cal);
    }

    @Override
    public ZTime set(int field, int number) {
        Calendar cal = asCalendar();
        cal.set(field, number);
        return new ZTime(cal);
    }
    
    public ZTime minHour(){
        ZTime result = copy();
        result.calendar.set(Calendar.HOUR_OF_DAY, 0);
        result.calendar.set(Calendar.MINUTE, 0);
        result.calendar.set(Calendar.SECOND, 0);
        result.calendar.set(Calendar.MILLISECOND, 0);
        return result;
    }
    
    public ZTime maxHour(){
        ZTime result = copy();
        result.calendar.set(Calendar.HOUR_OF_DAY, getActualMaximum(Calendar.HOUR_OF_DAY));
        result.calendar.set(Calendar.MINUTE, getActualMaximum(Calendar.MINUTE));
        result.calendar.set(Calendar.SECOND, getActualMaximum(Calendar.SECOND));
        result.calendar.set(Calendar.MILLISECOND, getActualMaximum(Calendar.MILLISECOND));
        return result;
    }
    
    @Override
    protected void applyMod(ZTimeStamp timeStamp){
        int hours = timeStamp.calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = timeStamp.calendar.get(Calendar.MINUTE);
        int seconds = timeStamp.calendar.get(Calendar.SECOND);
        int miliseconds = timeStamp.calendar.get(Calendar.MILLISECOND);
        
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, miliseconds);
    }
    
    @Override
    public ZTime copy(){
        return new ZTime(getTimeInMillis());
    }
    
    public static ZTime random(ZTimeStamp min,ZTimeStamp max){
        return new ZTime(ZTimeStamp.random(min,max));
    }
    
}
