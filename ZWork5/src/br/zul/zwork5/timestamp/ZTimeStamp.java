package br.zul.zwork5.timestamp;

import br.zul.zwork5.exception.ZInvalidValueException;
import br.zul.zwork5.exception.ZParseException;
import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Luiz Henrique
 */
public class ZTimeStamp implements Serializable {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    protected final Calendar calendar;

    //==========================================================================
    //CONSTRUÇÃ0
    //==========================================================================
    public ZTimeStamp() {
        this.calendar = Calendar.getInstance();
        init();
    }

    public ZTimeStamp(Calendar calendar) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(calendar.getTime());
        init();
    }

    public ZTimeStamp(Date date) {
        this.calendar = Calendar.getInstance();
        calendar.setTime(date);
        init();
    }

    public ZTimeStamp(long time) {
        this(new Date(time));
    }

    public ZTimeStamp(String format, String value) throws ZParseException {
        Calendar cal = null;
        try {
            cal = tryParseCalendar(format, value, null);
        } catch (ZParseException ex){
            try {
                cal = tryParseCalendar(format, value, Locale.ENGLISH);
            } catch (ZParseException ex2){
                try {
                    cal = tryParseCalendar(format, value, new Locale("PT-BR"));
                } catch (ZParseException ex3){
                    cal = tryParseCalendar("yyyy-MM-dd HH:mm:ss.SSS", value, null);
                }
            } 
        }
        calendar = cal;
        init();
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(){
        applyMod(this);
    }
    
    private Calendar tryParseCalendar(String format, String value, Locale locale) throws ZParseException{
        try {
            SimpleDateFormat sdf;
            if (locale==null){
                sdf = new SimpleDateFormat(format);
            } else {
                sdf = new SimpleDateFormat(format, locale);
            }
            Date date = sdf.parse(value.replace("�", "á"));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException e) {
            throw new ZParseException("[{0}]: {1}", format, value);
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public boolean equals(Object o) {
        if (o==null){
            return false;
        }
        if (o instanceof ZTimeStamp){
            return ((ZTimeStamp) o).calendar.compareTo(calendar)==0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.calendar);
        return hash;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ESTATICA
    //==========================================================================
    public static boolean isValid(String format, String value) {
        ZTimeStamp z;
        try {
            z = new ZTimeStamp(format, value);
        } catch (Exception e) {
            z = null;
        }
        return z != null;
    }
    
    public static ZTimeStamp random(ZTimeStamp min, ZTimeStamp max){
        long minMiliseconds = min.calendar.getTime().getTime();
        long maxMilisencods = max.calendar.getTime().getTime();
        if (minMiliseconds>=maxMilisencods){
            throw new ZInvalidValueException("O valor minimo ({0}) não pode ser superior ou igual ao valor maximo ({1}).", min, max);
        }
        long diff = maxMilisencods-minMiliseconds;
        Random random = new Random();
        long randomMiliseconds = Math.abs(random.nextLong()%(diff+1));
        long result = minMiliseconds+randomMiliseconds;
        return new ZTimeStamp(result);
    }
    
    //==========================================================================
    //MÉTODOS PROTEGIDOS
    //==========================================================================
    protected void applyMod(ZTimeStamp timeStamp){
        
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZTimeStamp add(int field, int number) {
        Calendar cal = asCalendar();
        cal.add(field, number);
        return new ZTimeStamp(cal);
    }

    public ZTimeStamp set(int field, int number) {
        Calendar cal = asCalendar();
        cal.set(field, number);
        return new ZTimeStamp(cal);
    }
    
    public ZTimeStamp mod(){
        ZTimeStamp result = copy();
        applyMod(result);
        return result;
    }
    
    public int get(int field){
        return calendar.get(field);
    }
    
    public int getMinimum(int field){
        return calendar.getMinimum(field);
    }
    
    public int getMaximum(int field){
        return calendar.getMaximum(field);
    }
    
    public int getActualMinimum(int field){
        return calendar.getActualMinimum(field);
    }

    public int getActualMaximum(int field){
        return calendar.getActualMaximum(field);
    }
    
    /**
        G	Era designator	Text	AD
        y	Year	Year	1996; 96
        Y	Week year	Year	2009; 09
        M	Month in year	Month	July; Jul; 07
        w	Week in year	Number	27
        W	Week in month	Number	2
        D	Day in year	Number	189
        d	Day in month	Number	10
        F	Day of week in month	Number	2
        E	Day name in week	Text	Tuesday; Tue
        u	Day number of week (1 = Monday, ..., 7 = Sunday)	Number	1
        a	Am/pm marker	Text	PM
        H	Hour in day (0-23)	Number	0
        k	Hour in day (1-24)	Number	24
        K	Hour in am/pm (0-11)	Number	0
        h	Hour in am/pm (1-12)	Number	12
        m	Minute in hour	Number	30
        s	Second in minute	Number	55
        S	Millisecond	Number	978
        z	Time zone	General time zone	Pacific Standard Time; PST; GMT-08:00
        Z	Time zone	RFC 822 time zone	-0800
        X	Time zone	ISO 8601 time zone	-08; -0800; -08:00
     * 
        Date and Time Pattern	Result
        "yyyy.MM.dd G 'at' HH:mm:ss z"	2001.07.04 AD at 12:08:56 PDT
        "EEE, MMM d, ''yy"	        Wed, Jul 4, '01
        "h:mm a"	                12:08 PM
        "hh 'o''clock' a, zzzz"	        12 o'clock PM, Pacific Daylight Time
        "K:mm a, z"	                0:08 PM, PDT
        "yyyyy.MMMMM.dd GGG hh:mm aaa"	02001.July.04 AD 12:08 PM
        "EEE, d MMM yyyy HH:mm:ss Z"	Wed, 4 Jul 2001 12:08:56 -0700
        "yyMMddHHmmssZ"	                010704120856-0700
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ"	2001-07-04T12:08:56.235-0700
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"	2001-07-04T12:08:56.235-07:00
        "YYYY-'W'ww-u"	2001-W27-3
     * 
     * @param format
     * @return 
     */
    public String format(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(asDate());
    }
    
    public long getTimeInMillis(){
        return calendar.getTimeInMillis();
    }

    @Override
    public String toString() {
        return format("EEE, d MMM yyyy HH:mm:ss Z");
    }

    public Calendar asCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.calendar.getTime());
        return cal;
    }

    public java.sql.Date asSqlDate(){
        return new java.sql.Date(asDate().getTime());
    }
    
    public java.sql.Timestamp asSqlTimestamp(){
        return new java.sql.Timestamp(asDate().getTime());
    }
    
    public java.sql.Time asSqlTime(){
        return new java.sql.Time(asDate().getTime());
    }
    
    public Date asDate() {
        return calendar.getTime();
    }
    
    public Time asTime(){
        Time time = new Time(asDate().getTime());
        return time;
    }
    
    public LocalDate asLocalDate(){
        return LocalDate.of(get(Calendar.YEAR), get(Calendar.MONTH)+1, get(Calendar.DAY_OF_MONTH));
    }
    
    public int compareTo(ZTimeStamp timeStamp){
        return calendar.compareTo(timeStamp.calendar);
    }
    
    public ZTimeStamp copy(){
        return new ZTimeStamp(calendar);
    }

}
