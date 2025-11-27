package ro.ase.ie.g1091_s04.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ro.ase.ie.g1091_s04.R;

public class MovieHolder extends RecyclerView.ViewHolder {
    protected TextView movieTitle;
    protected TextView movieRelease;
    protected RatingBar movieRating;
    protected Button deleteMovie;
    protected ImageView moviePoster;
    protected RadioGroup movieOptions;
    public MovieHolder(@NonNull View itemView) {
        super(itemView);
        movieTitle=itemView.findViewById(R.id.movieTitle);
        movieRelease=itemView.findViewById(R.id.movieRelease);
        movieRating=itemView.findViewById(R.id.movieRating);
        deleteMovie=itemView.findViewById(R.id.movieDelete);
        moviePoster=itemView.findViewById(R.id.moviePoster);
        movieOptions=itemView.findViewById(R.id.movieOptions);


        persist=itemView.findViewById(R.id.rbPersist);


    }

}


