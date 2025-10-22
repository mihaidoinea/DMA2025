package ro.ase.ie.g1097_s04.models;

import java.util.Date;

public class Movie {
    private String title; //EditText (PlainText)
    private Date release; //EditText (Date)
    private Double budget; //EditText (Decimal)
    private GenreEnum genre; //Spinner
    private Float rating; //RatingBar
    private Integer duration; //SeekBar
    private ParentalGuidanceEnum pGuidance; //RadioButton with RadioGroup
    private Boolean watched; //Switch
    private String posterUrl;

    public Movie(String title, Date release, Double budget, GenreEnum genre, Float rating, Integer duration, ParentalGuidanceEnum pGuidance, Boolean watched, String posterUrl) {
        this.title = title;
        this.release = release;
        this.budget = budget;
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
                ", release=" + release +
                ", budget=" + budget +
                ", genre=" + genre +
                ", rating=" + rating +
                ", duration=" + duration +
                ", pGuidance=" + pGuidance +
                ", watched=" + watched +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
