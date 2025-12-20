package ro.ase.ie.g1106_s04.database;

import androidx.room.TypeConverter;
import java.util.Date;

public class DateTimeConverter {
    @TypeConverter
    public Long DateToLong(Date date)
    {
        if(date == null)
        {
            return null;
        }
        else
        {
            return date.getTime();
        }
    }
    @TypeConverter
    public Date longToDate(Long value)
    {
        if(value == null)
        {
            return null;
        }
        else {
            Date date = new Date(value);
            return date;
        }
    }



}
