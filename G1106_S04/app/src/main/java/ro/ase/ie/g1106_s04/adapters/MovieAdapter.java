package ro.ase.ie.g1106_s04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import ro.ase.ie.g1106_s04.networking.DownloadTask;
import ro.ase.ie.g1106_s04.R;
import ro.ase.ie.g1106_s04.activities.IMovieEventListener;
import ro.ase.ie.g1106_s04.activities.MainActivity;
import ro.ase.ie.g1106_s04.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private Context context;
    private ArrayList<Movie> movieArrayList;
    private HashMap<Movie,Integer> options;

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
        holder.movieTitle.setText(movie.getTitle());
        holder.movieRating.setRating(movie.getRating());
        holder.movieRelease.setText(movie.getRelease().toString());
        holder.movieOptions.setOnCheckedChangeListener(null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMovieEventListener listener = (IMovieEventListener)context;
                listener.onMovieClick(holder.getBindingAdapterPosition());

            }
        });
        Integer rbOption = options.get(movie);
        holder.movieOptions.check(rbOption==null?-1:rbOption);

        holder.movieDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).onMovieDelete(holder.getBindingAdapterPosition());
            }
        });

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

        holder.movieDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IMovieEventListener listener = (IMovieEventListener) context;
                listener.onMovieDelete(holder.getBindingAdapterPosition());
            }
        });

        DownloadTask downloadTask = new DownloadTask(movie.getPosterUrl(),holder.moviePoster);
        Thread thread = new Thread(downloadTask);
        thread.start();

    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }
}
