package ro.ase.ie.g1106_s04.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import ro.ase.ie.g1106_s04.model.Movie;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);

    @Delete
    int deleteMovie(Movie movie);






}
