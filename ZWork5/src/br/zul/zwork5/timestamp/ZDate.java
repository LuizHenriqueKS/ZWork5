package br.zul.zwork5.timestamp;

import br.zul.zwork5.exception.ZParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Luiz Henrique
 */
public class ZDate extends ZTimeStamp {
      
    //==========================================================================
    //VARIÁVEIS PÚBLICAS
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
    
    public static String STANDART_FORMAT = "EEE, d MMM yyyy";
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDate(){    
        super();
    }
    
    public ZDate(ZTimeStamp timeStamp){
        super(timeStamp.calendar);
    }

    public ZDate(Calendar calendar) {
        super(calendar);
    }

    public ZDate(Date date) {
        super(date);
    }

    public ZDate(long time) {
        super(time);
    }
 
    public ZDate(String format, String str) throws ZParseException {
        super(format, str);
    }
    
    public ZDate(String str) throws ZParseException{
        this(STANDART_FORMAT,str);
    }
    
    public ZDate(LocalDate localDate) throws ZParseException{
        this(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
    }
    
    public ZDate(int year,int month,int dayOfMonth) throws ZParseException{
        this("yyyy/MM/dd",String.format("%d/%d/%d",year,month,dayOfMonth));
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================    
    @Override
    public String toString() {
        return format(STANDART_FORMAT);
    }
    
    @Override
    public ZDate add(int field, int number) {
        Calendar cal = asCalendar();
        cal.add(field, number);
        return new ZDate(cal);
    }

    @Override
    public ZDate set(int field, int number) {
        Calendar cal = asCalendar();
        cal.set(field, number);
        return new ZDate(cal);
    }
    
    public static ZDate random(ZTimeStamp min,ZTimeStamp max){
        return new ZDate(ZTimeStamp.random(min,max));
    }
        
    @Override
    protected void applyMod(ZTimeStamp timeStamp){
        timeStamp.calendar.set(Calendar.HOUR_OF_DAY, 0);
        timeStamp.calendar.set(Calendar.MINUTE, 0);
        timeStamp.calendar.set(Calendar.SECOND, 0);
        timeStamp.calendar.set(Calendar.MILLISECOND, 0);
    }
    
    @Override
    public ZDate copy(){
        return new ZDate(getTimeInMillis());
    }
    
}
