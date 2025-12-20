package ro.ase.ie.g1091_s04.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import ro.ase.ie.g1091_s04.models.Movie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);

    @Delete
    int deleteMovie(Movie movie);

}

