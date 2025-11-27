package ro.ase.ie.g1091_s04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import ro.ase.ie.g1091_s04.R;
import ro.ase.ie.g1091_s04.activities.MainActivity;
import ro.ase.ie.g1091_s04.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    public ArrayList<Movie> collection;
    public Context context;
    public HashMap<Movie,Boolean> options;

    public MovieAdapter(ArrayList<Movie> collection, Context context) {
        this.collection = collection;
        this.context = context;
        this.options = new HashMap<>();
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
        holder.itemView.setOnClickListener(view -> {
            ((MainActivity) context).onMovieItemClick(holder.getBindingAdapterPosition());
        });
        holder.deleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).onMovieDelete(holder.getBindingAdapterPosition());
            }
        });

        holder.movieOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {

               /* Boolean option;
                if(radioGroup.getChildAt(i).toString() == "Export"){
                    option = false;
                }
                options.put(movie, option);*/
            }
        });
    }



    @Override
    public int getItemCount() {
        return collection.size();
    }
}
