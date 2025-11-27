package ro.ase.ie.g1107_s04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.ase.ie.g1107_s04.R;
import ro.ase.ie.g1107_s04.adapters.MovieAdapter;
import ro.ase.ie.g1107_s04.model.Movie;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

    private static final int ADD_MOVIE = 100;
    private static final int UPDATE_MOVIE = 200;
    private ActivityResultLauncher<Intent> launcher;
    private final ArrayList<Movie> movieList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter=new MovieAdapter(this,movieList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(movieAdapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        //process the movie addition or update
                        if(o.getResultCode() == RESULT_OK)
                        {
                            Intent data = o.getData();
                            Movie movie = data.getParcelableExtra("movie");
                            if(!movieList.contains(movie))
                                movieList.add(movie);
                            else {
                                movieList.set(movieList.indexOf(movie) ,movie);
                            }
                            Log.d("MainActivityTag", movie.toString());
                            movieAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.add_movie_menu)
        {
            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
            intent.putExtra("operation_code", ADD_MOVIE);
            launcher.launch(intent);
        }
        else if(item.getItemId() == R.id.about_menu)
        {
            Toast.makeText(getApplicationContext(), "DAM2025 - G1107", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra("operation_code", UPDATE_MOVIE);
        Movie editMovie = movieList.get(position);
        intent.putExtra("movie", editMovie);
        launcher.launch(intent);
    }
}