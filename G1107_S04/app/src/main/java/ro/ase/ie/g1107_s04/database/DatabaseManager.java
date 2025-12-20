package ro.ase.ie.g1107_s04.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ro.ase.ie.g1107_s04.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    private static final String databaseName="movieDB";
    private static volatile DatabaseManager instance = null;

    public static DatabaseManager getDatabaseInstance(Context ctx) {
        if(DatabaseManager.instance == null) {
            synchronized (DatabaseManager.class) {
                if(DatabaseManager.instance ==null) {
                    DatabaseManager.instance = Room.databaseBuilder(ctx, DatabaseManager.class, databaseName)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return DatabaseManager.instance;
    }
    public abstract MovieDao getMovieDao();
}
