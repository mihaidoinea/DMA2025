package ro.ase.ie.g1091_s04.models;

import java.util.Date;

public class Movie {
    private String title; //EditText (PlainText)
    private Double budget; //EditText (Numeric)
    private Date release; //EditText (Date)
    private GenreEnum genre; //Spinner
    private Float rating; //RatingBar
    private Integer duration; //SeekBar
    private ParentalGuidanceEnum pGuidance; //RadioButton with RadioGroup
    private Boolean watched; //Switch
    private String posterUrl;

    public Movie(String title, Double budget, Date release, GenreEnum genre, Float rating, Integer duration, ParentalGuidanceEnum pGuidance, Boolean watched, String posterUrl) {
        this.title = title;
        this.budget = budget;
        this.release = release;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
        this.pGuidance = pGuidance;
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
                ", rating=" + rating +
                ", duration=" + duration +
                ", pGuidance=" + pGuidance +
                ", watched=" + watched +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
