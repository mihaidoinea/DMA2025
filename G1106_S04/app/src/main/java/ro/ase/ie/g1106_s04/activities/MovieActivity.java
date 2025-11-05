package ro.ase.ie.g1106_s04.activities;

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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ro.ase.ie.g1106_s04.R;
import ro.ase.ie.g1106_s04.model.Movie;

public class MovieActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etRelease;
    private EditText etBudget;
    private EditText etPoster;
    private RatingBar rbRating;
    private SeekBar sbDuration;
    private RadioGroup rgGuidance;
    private Switch swWatched;
    private Button btnMovieAction;
    private Spinner spGenre;

    private Movie movie;
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

    private void handleIntent() {
        Intent intent = getIntent();
        int actionCode = intent.getIntExtra("action_code", 0);
        if(actionCode == 100)
        {
            //add a new movie
            btnMovieAction.setText("Save Movie");
            movie = new Movie();
        }
        else if(actionCode == 200)
        {
            //update an existing movie
            btnMovieAction.setText("Update Movie");
        }
        else {
            finish();
        }
    }

    private void initializeControls() {
        etTitle = findViewById(R.id.etTitle);
        etBudget = findViewById(R.id.etBudget);
        etRelease = findViewById(R.id.etRelease);
        etPoster = findViewById(R.id.etPoster);
        spGenre = findViewById(R.id.spGenre);
        sbDuration = findViewById(R.id.sbDuration);
        rgGuidance = findViewById(R.id.rgApproval);
        swWatched = findViewById(R.id.swWatched);
        rbRating = findViewById(R.id.rbRating);
        btnMovieAction = findViewById(R.id.btnMovieAction);
    }

    private void initializeEvents() {
        btnMovieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidationResult result = validateFormAndBuildMovie();

                if(result.validForm == true) {

                    Intent intent = new Intent();
                    //set the movie as payload for this intent
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    switch (result.field)
                    {
                        case TITLE:
                            etTitle.setError(result.message);
                            break;
                        case RELEASE:
                            etRelease.setError(result.message);
                            break;
                        case BUDGET:
                            etBudget.setError(result.message);
                            break;
                        case POSTER:
                            etPoster.setError(result.message);
                            break;
                    }
                    Toast.makeText(MovieActivity.this, result.message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private ValidationResult validateFormAndBuildMovie() {
        String title = etTitle.getText().toString().trim();
        if(title.isEmpty())
        {
            return ValidationResult.error(Field.TITLE, "Movie title is mandatory!");
        }

        movie.setTitle(title);
    }

    private enum Field { TITLE, RELEASE, BUDGET, POSTER, DURATION, GENERIC };
    private static class ValidationResult
    {
        final boolean validForm;
        final Field field;
        final String message;

        private ValidationResult(boolean ok, Field field, String message)
        {
            this.validForm = ok;
            this.field = field;
            this.message = message;
        }

        static ValidationResult ok()
        {
            return new ValidationResult(true, Field.GENERIC, null);
        }
        static ValidationResult error(Field field, String message)
        {
            return new ValidationResult(false,field, message);
        }
    }

}