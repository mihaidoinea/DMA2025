package ro.ase.ie.g1107_s04.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Long dateToLong(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
    @TypeConverter
    public static Date longToDate(Long lg) {
        if (lg == null) {
            return null;
        } else {
            return new Date(lg);
        }
    }

}
