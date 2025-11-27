package ro.ase.ie.g1107_s04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import ro.ase.ie.g1107_s04.R;
import ro.ase.ie.g1107_s04.activities.MainActivity;
import ro.ase.ie.g1107_s04.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private Context context;
    private ArrayList<Movie> movieArrayList;

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context) ;
        View itemView = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie=movieArrayList.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieRating.setRating(movie.getRating());
        holder.movieRelease.setText(movie.getRelease().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.onMovieClick(holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }
}
