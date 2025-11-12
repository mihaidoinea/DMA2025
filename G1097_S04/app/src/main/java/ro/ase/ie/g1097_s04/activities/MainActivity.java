package ro.ase.ie.g1097_s04.activities;

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

import java.util.ArrayList;

import ro.ase.ie.g1097_s04.R;
import ro.ase.ie.g1097_s04.models.Movie;

public class MainActivity extends AppCompatActivity {

    protected static final int ADD_MOVIE = 100;
    protected static final int UPDATE_MOVIE = 200;
    ActivityResultLauncher<Intent> launcher;
    final ArrayList<Movie> movies = new ArrayList<>();

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

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        //handler for the movie returned from MovieActivity
                        if(o.getResultCode() ==RESULT_OK)
                        {
                            Intent intent = o.getData();
                            Movie movie = intent.getParcelableExtra("movie");
                            movies.add(movie);
                            Log.i("main_activity_tag",movie.toString());
                        }
                        else {
                            Toast.makeText(MainActivity.this,
                                    "MovieActivity cancelled",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_movie_menu_item)
        {
            //add new movie
            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
            intent.putExtra("action_code", ADD_MOVIE);
            launcher.launch(intent);

        }
        else if(item.getItemId() == R.id.about_menu_item)
        {
            Toast.makeText(MainActivity.this,
                    "DAM2025 - G1097",
                    Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}