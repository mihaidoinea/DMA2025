package ro.ase.ie.g1087_s04.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Movie implements Parcelable {
    private String title; //EditText
    private Double budget; //EditText
    private Date release; //EditText
    private GenreEnum genre; //Spinner
    private Integer duration; //SeekBar
    private ParentalApprovalEnum pApproval; //RadioButtons
    private Float rating; //RatingBar
    private Boolean watched; //Switch
    private String posterUrl;

    //
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
    }
}
