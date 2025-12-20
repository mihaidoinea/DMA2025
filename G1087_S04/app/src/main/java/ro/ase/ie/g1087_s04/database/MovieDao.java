package ro.ase.ie.g1087_s04.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import ro.ase.ie.g1087_s04.model.Movie;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertMovie(Movie movie);
    @Delete
    public int deleteMovie(Movie movie);
    
}
