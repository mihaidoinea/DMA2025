package ro.ase.ie.dma09;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("select * from movie")
    List<Movie> getAll();

    @Query("select * from movie where id=:movieId")
    Movie getMovieById(long movieId);

    @Insert
    long insert(Movie movie);

    @Update
    int update(Movie movie);

    @Update
    int update(List<Movie> movies);

    @Delete
    int delete(Movie movie);

    @Query("DELETE FROM movie WHERE id = :movieId")
    void deleteMovieById(int movieId);
}
