package ro.ase.ie.g1087_s04.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import ro.ase.ie.g1087_s04.R;
import ro.ase.ie.g1087_s04.activities.MainActivity;
import ro.ase.ie.g1087_s04.model.Movie;
import ro.ase.ie.g1087_s04.networking.DownloadTask;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private ArrayList<Movie> movieArrayList;
    private Context context;
    private HashMap<Integer,ArrayList<Movie>> options;

    public MovieAdapter(ArrayList<Movie> movieArrayList, Context context) {
        this.movieArrayList = movieArrayList;
        this.context = context;
        this.options = new HashMap<>();
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
        final Movie movie = movieArrayList.get(position);
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

        holder.movieOptions.setOnCheckedChangeListener(null);

        ArrayList<Movie> movies = null;
        movies = options.get(R.id.rbPersist);
        if(movies!= null && movies.contains(movie)) {
            Log.d("MovieAdapter", "Movie: " + movie.getTitle() +" with persist option.");
            holder.movieOptions.check(R.id.rbPersist);
        }
        movies=options.get(R.id.rbExport);
        if(movies!=null && movies.contains(movie)) {
            Log.d("MovieAdapter", "Movie: " + movie.getTitle() +" with export option.");
            holder.movieOptions.check(R.id.rbExport);
        }

        holder.movieOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int radioButtonId) {
                Log.d("MovieAdapter", "Movie: " + movie.getTitle() + "added for: " + radioButtonId);
                options.computeIfAbsent(radioButtonId, key->new ArrayList<>()).add(movie);
                if(radioButtonId==R.id.rbExport){
                    ArrayList<Movie> movies = options.get(R.id.rbPersist);

                    if(movies != null && movies.contains(movie)){
                        Log.d("MovieAdapter", "Movie: " + movie.getTitle() + " removed from persist list.");
                        movies.remove(movie);
                    }
                }
                else if(radioButtonId==R.id.rbPersist) {
                    ArrayList<Movie> movies = options.get(R.id.rbExport);

                    if(movies != null && movies.contains(movie)){
                        Log.d("MovieAdapter", "Movie: " + movie.getTitle() + " removed from export list.");
                        movies.remove(movie);
                    }
                }
            }
        });


        DownloadTask task = new DownloadTask(context, movie.getPosterUrl(), holder.moviePoster);
        Thread thread = new Thread(task);
        thread.start();
    }


    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }
}
