package ro.ase.ie.g1107_s04.model;

import java.util.Date;

public class Movie {


    //RUSU MIHNEA
    //TANISLAV ALIN
    //TUDOR DANIEL
    
    private String title;
    private Float budget;
    private Date release;
    private EnumGenre genre;
    private EnumParentalApproval pApproval;
    private Integer duration;
    private Float rating;
    private Boolean recommended;

    public Movie(String title, Float budget, Date release, EnumGenre genre, EnumParentalApproval pApproval, Integer duration, Float rating, Boolean recommended) {
        this.title = title;
        this.budget = budget;
        this.release = release;
        this.genre = genre;
        this.pApproval = pApproval;
        this.duration = duration;
        this.rating = rating;
        this.recommended = recommended;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", budget=" + budget +
                ", release=" + release +
                ", genre=" + genre +
                ", pApproval=" + pApproval +
                ", duration=" + duration +
                ", rating=" + rating +
                ", recommended=" + recommended +
                '}';
    }
}
