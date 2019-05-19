package lizw.springboot.traceSourceMain;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {
    public static ZonedDateTime changeDateTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("Asia/Shanghai"));
        if(StringUtils.isBlank(time)){
            return null;
        }
        ZonedDateTime dateTime = ZonedDateTime.parse(time, formatter);
        return dateTime.withZoneSameInstant(ZoneId.of("UTC"));
    }

    public static Date changeDate(String time)throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = sdf.parse(time);

        return date;
    }

    public static Date changeDate2(String time)throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = sdf.parse(time);

        return date;
    }

    public static String dateChangeString(Date time) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String dtime = sdf.format(time);

        return  dtime;
    }

    public static String dateChangeString2(Date time) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String dtime = sdf.format(time);

        return  dtime;
    }
}
