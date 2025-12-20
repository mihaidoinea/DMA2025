package ro.ase.ie.g1106_s04.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ro.ase.ie.g1106_s04.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase{
    private final static String databaseName = "database";
    private static volatile DatabaseManager databaseInstance;

    public static DatabaseManager getInstance(Context context){
        if(databaseInstance == null){
            synchronized (DatabaseManager.class){
                if(databaseInstance == null){
                    databaseInstance = Room.databaseBuilder(context,
                            DatabaseManager.class, databaseName)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return databaseInstance;
    }
    public abstract MovieDAO getMovieDao();
}
