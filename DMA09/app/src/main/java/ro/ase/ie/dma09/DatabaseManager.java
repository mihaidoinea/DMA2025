package ro.ase.ie.dma09;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Movie.class}, exportSchema = false, version = 1)
public abstract class DatabaseManager extends RoomDatabase {

    private static final String DB_NAME = "dma10_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context)
    {
        if(databaseManager == null)
        {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();


                    Room.databaseBuilder(context, DatabaseManager.class, "app.db")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.execSQL("INSERT INTO users (id, name) VALUES (1, 'Admin')");
                                    });
                                }
                            })
                            .build();

                }
            }
        }
        return databaseManager;
    }

    public abstract MovieDao getMovieDao();
}
