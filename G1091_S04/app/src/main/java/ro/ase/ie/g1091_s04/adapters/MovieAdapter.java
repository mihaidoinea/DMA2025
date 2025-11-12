package ro.ase.ie.g1091_s04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import ro.ase.ie.g1091_s04.R;
import ro.ase.ie.g1091_s04.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    public ArrayList<Movie> collection;
    public Context context;

    public MovieAdapter(ArrayList<Movie> collection, Context context) {
        this.collection = collection;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = collection.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieRelease.setText(movie.getRelease().toString());
        holder.movieRating.setRating(movie.getRating());
    }



    @Override
    public int getItemCount() {
        return collection.size();
    }
}
