package ro.ase.ie.g1097_s04.database;

import androidx.room.TypeConverter;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Date longToDate(Long time){
        if(time != null) {
            return new Date(time);
        }
        return null;
    }
    @TypeConverter
    public Long dateToLong(Date date){
        if(date != null) {
            return date.getTime();
        }
        return null;
    }
}
