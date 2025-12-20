package ro.ase.ie.g1087_s04.database;

import androidx.room.Delete;
import androidx.room.Insert;

import ro.ase.ie.g1087_s04.model.Movie;

public interface MovieDao {
    @Insert
    public Long insertMovie(Movie movie);
    @Delete
    public Long deleteMovie(Movie movie);
    
}
