package ro.ase.ie.g1097_s04.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import ro.ase.ie.g1097_s04.models.Movie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long insertMovie(Movie movie);

    @Delete
    public int deleteMovie(Movie movie);

}
