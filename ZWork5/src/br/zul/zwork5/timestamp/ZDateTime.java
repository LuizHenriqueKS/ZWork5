package br.zul.zwork5.timestamp;

import br.zul.zwork5.exception.ZParseException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Luiz Henrique
 */
public class ZDateTime extends ZTimeStamp {
   
    //==========================================================================
    //CONSTANTES
    //==========================================================================
    public static final int ERA = 0;
    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int WEEK_OF_YEAR = 3;
    public static final int WEEK_OF_MONTH = 4;
    public static final int DATE = 5;
    public static final int DAY_OF_MONTH = 5;
    public static final int DAY_OF_YEAR = 6;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    public static final int AM_PM = 9;
    public static final int HOUR = 10;
    public static final int HOUR_OF_DAY = 11;
    public static final int MINUTE = 12;
    public static final int SECOND = 13;
    public static final int MILLISECOND = 14;
    public static final int ZONE_OFFSET = 15;
    public static final int DST_OFFSET = 16;
    public static final int FIELD_COUNT = 17;
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    public static final int JANUARY = 0;
    public static final int FEBRUARY = 1;
    public static final int MARCH = 2;
    public static final int APRIL = 3;
    public static final int MAY = 4;
    public static final int JUNE = 5;
    public static final int JULY = 6;
    public static final int AUGUST = 7;
    public static final int SEPTEMBER = 8;
    public static final int OCTOBER = 9;
    public static final int NOVEMBER = 10;
    public static final int DECEMBER = 11;
    public static final int UNDECIMBER = 12;
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
    
    public static String STANDART_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDateTime(){    
        super();
    }
    
    public ZDateTime(ZTimeStamp timeStamp){
        super(timeStamp.calendar);
    }

    public ZDateTime(Calendar calendar) {
        super(calendar);
    }

    public ZDateTime(Date date) {
        super(date);
    }

    public ZDateTime(long time) {
        super(time);
    }
 
    public ZDateTime(String format, String value) throws ZParseException {
        super(format, value);
    }
    
    public ZDateTime(String value) throws ZParseException {
        super(STANDART_FORMAT, value);
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZDateTime add(int field, int number) {
        Calendar cal = asCalendar();
        cal.add(field, number);
        return new ZDateTime(cal);
    }

    @Override
    public ZDateTime set(int field, int number) {
        Calendar cal = asCalendar();
        cal.set(field, number);
        return new ZDateTime(cal);
    }
    
    public ZDateTime minHour(){
        ZDateTime result = copy();
        result.calendar.set(Calendar.HOUR_OF_DAY, 0);
        result.calendar.set(Calendar.MINUTE, 0);
        result.calendar.set(Calendar.SECOND, 0);
        result.calendar.set(Calendar.MILLISECOND, 0);
        return result;
    }
    
    public ZDateTime maxHour(){
        ZDateTime result = copy();
        result.calendar.set(Calendar.HOUR_OF_DAY, getActualMaximum(Calendar.HOUR_OF_DAY));
        result.calendar.set(Calendar.MINUTE, getActualMaximum(Calendar.MINUTE));
        result.calendar.set(Calendar.SECOND, getActualMaximum(Calendar.SECOND));
        result.calendar.set(Calendar.MILLISECOND, getActualMaximum(Calendar.MILLISECOND));
        return result;
    }
    
    @Override
    public ZDateTime copy(){
        return new ZDateTime(getTimeInMillis());
    }
    
    public static ZDateTime random(ZTimeStamp min, ZTimeStamp max){
        return new ZDateTime(ZTimeStamp.random(min,max));
    }
    
}
