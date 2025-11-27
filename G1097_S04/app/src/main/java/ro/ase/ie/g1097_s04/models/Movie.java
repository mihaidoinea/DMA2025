package ro.ase.ie.g1097_s04.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Movie implements Parcelable {
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

    public Movie() {

    }

    protected Movie(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            budget = null;
        } else {
            budget = in.readDouble();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        byte tmpWatched = in.readByte();
        watched = tmpWatched == 0 ? null : tmpWatched == 1;
        posterUrl = in.readString();
        genre = GenreEnum.valueOf(in.readString());
        pGuidance = ParentalGuidanceEnum.valueOf(in.readString());
        if (in.readByte() == 0) {
            release = null;
        } else {
            release = new Date(in.readLong());
        }
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setpGuidance(ParentalGuidanceEnum pGuidance) {
        this.pGuidance = pGuidance;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        if (budget == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(budget);
        }
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(rating);
        }
        if (duration == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(duration);
        }
        parcel.writeByte((byte) (watched == null ? 0 : watched ? 1 : 2));
        parcel.writeString(posterUrl);

        parcel.writeString(genre.toString());
        parcel.writeString(pGuidance.toString());
        if(release == null)
        {
            parcel.writeByte((byte)0);
        }
        else {
            parcel.writeByte((byte)1);
            parcel.writeLong(release.getTime());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getTitle(), movie.getTitle()) && Objects.equals(getRelease(), movie.getRelease());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getRelease());
    }

    public String getTitle() {
        return title;
    }

    public Date getRelease() {
        return release;
    }

    public Double getBudget() {
        return budget;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public Float getRating() {
        return rating;
    }

    public Integer getDuration() {
        return duration;
    }

    public ParentalGuidanceEnum getpGuidance() {
        return pGuidance;
    }

    public Boolean getWatched() {
        return watched;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}
