package ro.ase.ie.g1087_s04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import ro.ase.ie.g1087_s04.R;
import ro.ase.ie.g1087_s04.adapters.MovieAdapter;
import ro.ase.ie.g1087_s04.database.DatabaseManager;
import ro.ase.ie.g1087_s04.database.MovieDao;
import ro.ase.ie.g1087_s04.model.Movie;

public class MainActivity extends AppCompatActivity implements IMovieClickListener {

    private static final int MOVIE_ADD = 100;
    private static final int MOVIE_UPDATE = 200;
    private ActivityResultLauncher<Intent> launcher;

    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;
    private ArrayList<Movie> movieArrayList;
    private MovieAdapter movieAdapter;
    private MovieDao movieTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        movieArrayList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieArrayList, this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(movieAdapter);
        databaseManager=DatabaseManager.getInstance(getApplicationContext());
        movieTable = databaseManager.getMovieDao();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == RESULT_OK)
                        {
                            Intent data = o.getData();
                            Movie movie = data.getParcelableExtra("movie");
                            Log.d("MainActivityTag", movie.toString());
                            if(movieArrayList.contains(movie)) {
                                int position =movieArrayList.indexOf(movie);
                                movieArrayList.set(position,movie);
                            } else {
                                movieArrayList.add(movie);
                            }
                            movieTable.insertMovie(movie);
                            movieAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_movie_menu_item)
        {
            //add a new movie
            //explicit intent
            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
          /*  Bundle bundle = new Bundle();
            bundle.putInt("action_code", MOVIE_ADD);
            intent.putExtras(bundle);*/
            intent.putExtra("action_code", MOVIE_ADD);
            launcher.launch(intent);
        }
        else if(item.getItemId() == R.id.about_menu_item)
        {
            Toast.makeText(MainActivity.this,"DAM2025 - G1087", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieCLick(int position) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra("action_code", MOVIE_UPDATE);
        intent.putExtra("movie", movieArrayList.get(position));
        launcher.launch(intent);

    }

    @Override
    public void onMovieDelete(int position) {
        movieTable.deleteMovie(movieArrayList.get(position));
        movieArrayList.remove(position);
        movieAdapter.notifyItemRemoved(position);
    }
}


