package ro.ase.ie.dma09;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movie")
public class Movie implements Serializable {

    @Ignore
    private String movieFId;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long movieId;
    @ColumnInfo(name="title")
    private String movieTitle;
    @ColumnInfo(name="genre")
    private String movieGenre;
    @ColumnInfo(name="budget")
    private Double movieBudget;

    public String getMovieFId() {
        return movieFId;
    }

    public void setMovieFId(String movieFId) {
        this.movieFId = movieFId;
    }

    @Ignore
    public Movie() {
    }

    public Movie(long movieId, String movieTitle, String movieGenre, Double movieBudget) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.movieBudget = movieBudget;
    }

    @Ignore
    public Movie(String title, String genre, Double budget) {
        this.movieTitle = title;
        this.movieGenre = genre;
        this.movieBudget = budget;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieGenre='" + movieGenre + '\'' +
                ", movieBudget=" + movieBudget +
                '}';
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public Double getMovieBudget() {
        return movieBudget;
    }

    public void setMovieBudget(Double movieBudget) {
        this.movieBudget = movieBudget;
    }
}
