package ro.ase.ie.g1087_s04.database;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;
public class DateConverter {
@TypeConverter
    public Date longToDate(Long time){
        if(time != null) {
            Date date;
            date = new Date(time);
        }
        return null;
    }
    @TypeConverter
    public Long dateToLong(Date date){
        if(date != null) {
            Long time = date.getTime();
            return time;
        }
        return null;
    }
}
