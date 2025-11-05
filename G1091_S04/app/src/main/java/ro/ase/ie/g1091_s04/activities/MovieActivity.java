package ro.ase.ie.g1091_s04.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

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

                ValidationResult result = validateAndBuildMovie();

                if(result.isValid) {
                    Intent intent = new Intent();
                    //place the movie instance inside the Bundle
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    switch (result.field)
                    {
                        case TITLE:
                            etTitle.setError(result.message);
                            break;
                        case BUDGET:
                            etBudget.setError(result.message);
                            break;
                        case DURATION:
                            sbDuration.getProgressDrawable().setTint(Color.RED);
                            break;
                        case RELEASE:
                            etRelease.setError(result.message);
                            break;
                        case POSTER:
                            etPoster.setError(result.message);
                            break;
                    }
                    Toast.makeText(MovieActivity.this, result.message, Toast.LENGTH_LONG).show();
                }
            }
        });
//        btnMovieAction.setOnClickListener(this);
    }

    private ValidationResult validateAndBuildMovie() {
        String title = etTitle.getText().toString().trim();
        if(title.isEmpty())
        {
            return ValidationResult.error(Field.TITLE, "Movie title is mandatory!");
        }

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

    private enum Field {TITLE, BUDGET, DURATION, RELEASE, POSTER, GENERIC};
    //Helper class for validating UI
    private static class ValidationResult
    {
        final boolean isValid;
        final Field field;
        final String message;

        private ValidationResult(boolean valid, Field field, String message)
        {
            this.isValid = valid;
            this.field = field;
            this.message = message;
        }

        static ValidationResult valid()
        {
            return new ValidationResult(true, Field.GENERIC, null);
        }
        static ValidationResult error(Field field, String message)
        {
            return new ValidationResult(false, field, message);
        }

    }
}