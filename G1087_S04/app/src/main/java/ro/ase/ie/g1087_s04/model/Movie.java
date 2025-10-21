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
