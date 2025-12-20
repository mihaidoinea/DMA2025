package ro.ase.ie.g1106_s04.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import ro.ase.ie.g1106_s04.model.Movie;

@Dao()

public interface MovieDAO {
    @Insert
    long insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);






}
