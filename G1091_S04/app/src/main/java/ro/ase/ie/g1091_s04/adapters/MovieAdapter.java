package ro.ase.ie.g1091_s04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import ro.ase.ie.g1091_s04.R;
import ro.ase.ie.g1091_s04.activities.MainActivity;
import ro.ase.ie.g1091_s04.models.Movie;
import ro.ase.ie.g1091_s04.networking.DownloadTask;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    public ArrayList<Movie> collection;
    public Context context;
    public HashMap<Movie,Integer> options;

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
        holder.movieOptions.setOnCheckedChangeListener(null);
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

        Integer rbOption = options.get(movie);
        holder.movieOptions.check(rbOption==null?-1:rbOption);

        holder.movieOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                int value=radioGroup.getCheckedRadioButtonId();
                if(value==R.id.rbPersist)
                    options.put(movie,R.id.rbPersist);
                else
                    options.put(movie,R.id.rbExport);
            }
        });

        DownloadTask downloadTask = new DownloadTask(movie.getPosterUrl(),holder.moviePoster);
        Thread thread = new Thread(downloadTask);
        thread.start();
    }



    @Override
    public int getItemCount() {
        return collection.size();
    }
}
