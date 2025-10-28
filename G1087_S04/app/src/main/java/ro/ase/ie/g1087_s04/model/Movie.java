package ro.ase.ie.g1087_s04.model;

import java.util.Date;

public class Movie {
    private String title; //EditText
    private Double budget; //EditText
    private Date release; //EditText
    private GenreEnum genre; //Spinner
    private Integer duration; //SeekBar
    private ParentalApprovalEnum pApproval; //RadioButtons
    private Float rating; //RatingBar
    private Boolean watched; //Switch
    private String posterUrl;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setpApproval(ParentalApprovalEnum pApproval) {
        this.pApproval = pApproval;
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

    public Movie() {
    }

    public Movie(String title, Double budget, Date release, GenreEnum genre, Integer duration, ParentalApprovalEnum pApproval, Float rating, Boolean watched, String posterUrl) {
        this.title = title;
        this.budget = budget;
        this.release = release;
        this.genre = genre;
        this.duration = duration;
        this.pApproval = pApproval;
        this.rating = rating;
        this.watched = watched;
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", budget=" + budget +
                ", release=" + release +
                ", genre=" + genre +
                ", duration=" + duration +
                ", pApproval=" + pApproval +
                ", rating=" + rating +
                ", watched=" + watched +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
