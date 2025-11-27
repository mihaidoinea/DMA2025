package ro.ase.ie.g1097_s04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import ro.ase.ie.g1097_s04.R;
import ro.ase.ie.g1097_s04.activities.MainActivity;
import ro.ase.ie.g1097_s04.models.Movie;

public  class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    ArrayList<Movie> moviesList;
    Context context;
    HashMap<Movie,String> options;

    public MovieAdapter(ArrayList<Movie> moviesList, Context context) {
        this.moviesList = moviesList;
        this.context = context;
        this.options=new HashMap<>();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View itemView=layoutInflater.inflate(R.layout.movie_item,parent,false);
        return new MovieHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie=moviesList.get(position);
        holder.movieTitle.setText(movie.getTitle() + " (" + movie.getGenre().toString() + ")");
        holder.movieRelease.setText(movie.getRelease().toString());
        holder.movieRating.setRating(movie.getRating());
        holder.itemView.setOnClickListener(view -> {
            ((MainActivity)context).onMovieItemClick(holder.getBindingAdapterPosition());
        });

        holder.movieOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                int value=radioGroup.getCheckedRadioButtonId();
                if(value==R.id.rbPersist)
                options.put(movie,"Persist");
                else options.put(movie,"Export");
            }
        });
    }

    @Override
    public int getItemCount() {

        return moviesList.size();
    }
}
