package ro.ase.ie.g1097_s04.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.ase.ie.g1097_s04.models.Movie;

public  class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    ArrayList<Movie> moviesList;
    Context context;

    public MovieAdapter(ArrayList<Movie> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
