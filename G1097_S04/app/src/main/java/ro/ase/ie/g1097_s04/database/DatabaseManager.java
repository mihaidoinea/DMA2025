package ro.ase.ie.g1097_s04.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ro.ase.ie.g1097_s04.models.Movie;

@Database(entities = {Movie.class},version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase{
    static final String DatabaseName="movieDB";
    static volatile DatabaseManager databaseInstance=null;

    //design patter SingleTone
    public static DatabaseManager getInstance(Context context){
        if(databaseInstance == null){
            synchronized (DatabaseManager.class){
                if(databaseInstance==null){
                    databaseInstance= Room.databaseBuilder(context
                            ,DatabaseManager.class
                            ,DatabaseName).allowMainThreadQueries().build();
                }
            }
        }
        return databaseInstance;
    }
    public abstract MovieDao getMovieDao();
}
