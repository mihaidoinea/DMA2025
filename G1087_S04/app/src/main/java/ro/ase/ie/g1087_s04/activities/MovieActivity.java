package ro.ase.ie.g1087_s04.activities;

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

import ro.ase.ie.g1087_s04.R;
import ro.ase.ie.g1087_s04.model.Movie;

public class MovieActivity extends AppCompatActivity
//        implements View.OnClickListener
{

    private EditText etTitle;
    private EditText etRelease;
    private EditText etBudget;
    private EditText etPosterUrl;
    private Spinner spGenre;
    private SeekBar sbDuration;
    private RatingBar rbRating;
    private RadioGroup rgGuidance;
    private Switch swWatched;
    private Button btnMovieAction;

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

        Intent intent = getIntent();
        int actionCode = intent.getIntExtra("action_code", 0);
        if(actionCode == 100)
        {
            //add new movie
            btnMovieAction.setText("Save Movie");
            movie = new Movie();
        }
        else if(actionCode == 200)
        {
            //update existing movie
            btnMovieAction.setText("Update Movie");
        }
    }

    private void initializeControls() {
        etTitle = findViewById(R.id.etTitle);
        etRelease = findViewById(R.id.etRelease);
        etBudget = findViewById(R.id.etBudget);
        etPosterUrl = findViewById(R.id.etPoster);
        swWatched = findViewById(R.id.swWatched);
        spGenre = findViewById(R.id.spGenre);
        sbDuration = findViewById(R.id.sbDuration);
        rgGuidance = findViewById(R.id.rgApproval);
        rbRating = findViewById(R.id.rbRating);
        btnMovieAction = findViewById(R.id.btnMovieAction);
    }

    private ValidationResult validateAndCreateMovie() {
        String title = etTitle.getText().toString().trim();
        if(title.isEmpty())
        {
            return ValidationResult.Error( "Movie Title is mandatory", FieldEnum.TITLE);
        }
        //all other validations here

        movie.setTitle(title);
        return ValidationResult.Valid();
    }

    private void initializeEvents() {
        btnMovieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidationResult results = validateAndCreateMovie();

                if(results.ok) {
                    Intent intent = new Intent();
                    //set the movie object as Intent data
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    if(results.field == FieldEnum.TITLE) etTitle.setError(results.message);
                    else if(results.field == FieldEnum.RELEASE) etRelease.setError(results.message);
                    else if(results.field == FieldEnum.BUDGET) etBudget.setError(results.message);
                    else if(results.field == FieldEnum.POSTER) etPosterUrl.setError(results.message);

                    Toast.makeText(MovieActivity.this, results.message, Toast.LENGTH_LONG).show();
                }
            }
        });
//        btnMovieAction.setOnClickListener(this);
    }



/*    @Override
    public void onClick(View view) {

    }*/

    private enum FieldEnum {TITLE, RELEASE, BUDGET, POSTER, GENERIC};

    private static class ValidationResult
    {
        final boolean ok;
        final String message;
        final FieldEnum field;

        private ValidationResult(boolean ok, String message, FieldEnum field)
        {
            this.ok = ok;
            this.message = message;
            this.field = field;
        }
        static ValidationResult Valid()
        {
            return new ValidationResult(true, null, FieldEnum.GENERIC);
        }
        static ValidationResult Error(String message, FieldEnum field)
        {
            return new ValidationResult(false, message, field);
        }
    }

}