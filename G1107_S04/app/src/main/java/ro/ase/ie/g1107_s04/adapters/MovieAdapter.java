package ro.ase.ie.g1107_s04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ro.ase.ie.g1107_s04.R;
import ro.ase.ie.g1107_s04.activities.MainActivity;
import ro.ase.ie.g1107_s04.model.Movie;
import ro.ase.ie.g1107_s04.networking.DownloadPoster;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private Context context;
    private ArrayList<Movie> movieArrayList;
    private Map<Integer, ArrayList<Movie>> options;
    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
        this.options = new HashMap<>();
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

        DownloadPoster downloadPoster = new DownloadPoster(holder.moviePoster, movie.getPosterUrl());
        Thread thread = new Thread(downloadPoster);
        thread.start();

        holder.movieTitle.setText(movie.getTitle());
        holder.movieRating.setRating(movie.getRating());
        holder.movieRelease.setText(movie.getRelease().toString());
        holder.movieOptions.setOnCheckedChangeListener(null);

        ArrayList<Movie> persistedMovies = options.get(R.id.rbPersist);
        ArrayList<Movie> exportedMovies = options.get(R.id.rbExport);
        if ( persistedMovies!= null &&  persistedMovies.contains(movie)  )
        {
            holder.movieOptions.check(R.id.rbPersist);
        }
        else if ( exportedMovies!= null &&  exportedMovies.contains(movie)  )
        {
            holder.movieOptions.check(R.id.rbExport);
        }
        else
        {
            holder.movieOptions.check(-1);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) context;
                activity.onMovieClick(holder.getBindingAdapterPosition());
            }

        });

        holder.movieOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int rbId) {
                options.computeIfAbsent(rbId, integer -> new ArrayList<>()).add(movie);
                if (rbId == R.id.rbExport) {
                    ArrayList<Movie> movies = options.get(R.id.rbPersist);
                    if (movies != null && movies.contains(movie))
                        movies.remove(movie);
                }else if (rbId == R.id.rbPersist) {
                    ArrayList<Movie> movies = options.get(R.id.rbExport);
                    if (movies != null && movies.contains(movie))
                        movies.remove(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }
}
