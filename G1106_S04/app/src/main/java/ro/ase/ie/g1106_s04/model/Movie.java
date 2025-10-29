package ro.ase.ie.g1106_s04.model;

import java.util.Date;

public class Movie {
    private String title; //EditText (PlainText)
    private Double budget; //EditText (Number)
    private Date release; //EditText (Date)
    private Integer duration; //SeekBar
    private GenreEnum genre; // Spinner
    private ParentalGuidanceEnum pGuidance; //RadioButtons with RadioGroup
    private Float rating; // RatingBar
    private Boolean watched; //Switch
    private String posterUrl; //EditText

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    public void setpGuidance(ParentalGuidanceEnum pGuidance) {
        this.pGuidance = pGuidance;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Movie(String title, Double budget, Date release, Integer duration, GenreEnum genre, ParentalGuidanceEnum pGuidance, Float rating, Boolean watched, String posterUrl) {
        this.title = title;
        this.budget = budget;
        this.release = release;
        this.duration = duration;
        this.genre = genre;
        this.pGuidance = pGuidance;
        this.rating = rating;
        this.watched = watched;
        this.posterUrl = posterUrl;
    }

    public Movie() {

    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", budget=" + budget +
                ", release=" + release +
                ", duration=" + duration +
                ", genre=" + genre +
                ", pGuidance=" + pGuidance +
                ", rating=" + rating +
                ", watched=" + watched +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
