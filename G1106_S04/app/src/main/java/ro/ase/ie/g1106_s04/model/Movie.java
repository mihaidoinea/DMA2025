package ro.ase.ie.g1106_s04.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import ro.ase.ie.g1106_s04.database.DateTimeConverter;

@Entity(tableName = "MovieTable",
        primaryKeys = {"release", "title"},
        indices = {@Index("release"),@Index("title")}
)

public class Movie implements Parcelable {
    @ColumnInfo(name = "movieTitle")
    private String title; //EditText (PlainText)
    @ColumnInfo
    private Double budget; //EditText (Number)
    @ColumnInfo
    @TypeConverters(DateTimeConverter.class)
    private Date release; //EditText (Date)
    @ColumnInfo
    private Integer duration; //SeekBar
    @ColumnInfo
    private GenreEnum genre; // Spinner
    @Ignore
    private ParentalGuidanceEnum pGuidance; //RadioButtons with RadioGroup
    @ColumnInfo
    private Float rating; // RatingBar
    @ColumnInfo
    private Boolean watched; //Switch
    @ColumnInfo
    private String posterUrl; //EditText

    protected Movie(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            budget = null;
        } else {
            budget = in.readDouble();
        }
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        byte tmpWatched = in.readByte();
        watched = tmpWatched == 0 ? null : tmpWatched == 1;
        posterUrl = in.readString();
        genre = GenreEnum.valueOf(in.readString());
        pGuidance = ParentalGuidanceEnum.valueOf(in.readString());

        if(in.readByte() == 0)
            release = null;
        else
            release = new Date(in.readLong());
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

    @Override
    public int describeContents() {
        return 0;
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

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        if (budget == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(budget);
        }
        if (duration == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(duration);
        }
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(rating);
        }
        parcel.writeByte((byte) (watched == null ? 0 : watched ? 1 : 2));
        parcel.writeString(posterUrl);
        parcel.writeString(genre.toString());
        parcel.writeString(pGuidance.toString());

        if(release == null)
            parcel.writeByte((byte)0);
        else {
            parcel.writeByte((byte)1);
            parcel.writeLong(release.getTime());
        }
    }

    public String getTitle() {
        return title;
    }

    public Double getBudget() {
        return budget;
    }

    public Date getRelease() {
        return release;
    }

    public Integer getDuration() {
        return duration;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public ParentalGuidanceEnum getpGuidance() {
        return pGuidance;
    }

    public Float getRating() {
        return rating;
    }

    public Boolean getWatched() {
        return watched;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}
