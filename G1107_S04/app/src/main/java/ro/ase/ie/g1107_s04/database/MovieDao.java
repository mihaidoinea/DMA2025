package ro.ase.ie.g1107_s04.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import ro.ase.ie.g1107_s04.model.Movie;

@Dao
public interface MovieDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);
}
