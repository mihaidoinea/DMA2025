package ro.ase.ie.g1091_s04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ro.ase.ie.g1091_s04.R;
import ro.ase.ie.g1091_s04.models.Movie;

public class MovieActivity extends AppCompatActivity
//    implements View.OnClickListener
{

    private Movie movie;

    private EditText etTitle;
    private EditText etRelease;
    private EditText etBudget;
    private EditText etPoster;
    private Spinner spGenre;
    private SeekBar sbDuration;
    private RatingBar rbRating;
    private Switch swWatched;
    private RadioGroup rgGuidance;
    private Button btnMovieAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeControls();
        initializeEvents();
        handleIntent();
    }

/*    @Override
    public void onClick(View view) {

    }*/

    private void initializeEvents() {
        btnMovieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                movie.setTitle(etTitle.getText().toString());
                movie.setRating(rbRating.getRating());
                movie.setDuration(sbDuration.getProgress());

                Intent intent = new Intent();
                //place the movie instance inside the Bundle
                setResult(RESULT_OK, intent);
                finish();
            }
        });
//        btnMovieAction.setOnClickListener(this);
    }

    private void initializeControls() {
        etTitle = findViewById(R.id.etTitle);
        etRelease = findViewById(R.id.etRelease);
        etBudget = findViewById(R.id.etBudget);
        etPoster = findViewById(R.id.etPoster);
        swWatched = findViewById(R.id.swWatched);
        spGenre = findViewById(R.id.spGenre);
        sbDuration = findViewById(R.id.sbDuration);
        rgGuidance = findViewById(R.id.rgApproval);
        btnMovieAction = findViewById(R.id.btnMovieAction);
        rbRating = findViewById(R.id.rbRating);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        int actionCode = intent.getIntExtra("action_code", 0);
        if(actionCode == MainActivity.ADD_MOVIE)
        {
            //add new movie
            movie = new Movie();
        }
        else if(actionCode == MainActivity.UPDATE_MOVIE)
        {
            //update existing movie
            //get the existing movie from Intent
            //fill in the controls with movie data
        }
        else {
            finish();
        }
    }


}