package ro.ase.ie.g1087_s04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ro.ase.ie.g1087_s04.R;
import ro.ase.ie.g1087_s04.activities.MainActivity;
import ro.ase.ie.g1087_s04.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private ArrayList<Movie> movieArrayList;
    private Context context;

    public MovieAdapter(ArrayList<Movie> movieArrayList, Context context) {
        this.movieArrayList = movieArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = movieArrayList.get(position);
        holder.movieTitle.setText(movie.getTitle() + "(" + movie.getGenre() + ")");
        holder.movieRating.setRating(movie.getRating());
        holder.movieRelease.setText(new SimpleDateFormat().format(movie.getRelease()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                ((MainActivity)context).onMovieCLick(position);
            }
        });

        holder.movieDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getBindingAdapterPosition();
                ((MainActivity)context).onMovieDelete(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }
}
