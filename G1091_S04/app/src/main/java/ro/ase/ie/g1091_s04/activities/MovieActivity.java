package ro.ase.ie.g1091_s04.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ro.ase.ie.g1091_s04.R;
import ro.ase.ie.g1091_s04.models.GenreEnum;
import ro.ase.ie.g1091_s04.models.Movie;
import ro.ase.ie.g1091_s04.models.ParentalGuidanceEnum;

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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

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
                    intent.putExtra("movie", movie);
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

        String budgetStr = etBudget.getText().toString();
        Double budget = 0.0;
        if(!budgetStr.isEmpty()) {
            try {
                budget = Double.parseDouble(budgetStr);
            } catch (NumberFormatException e) {
                return ValidationResult.error(Field.BUDGET, "Movie budget needs to be a valid number!");
            }
        }
        else
            return ValidationResult.error(Field.BUDGET, "Movie budget is mandatory!");

        String releaseStr = etRelease.getText().toString().trim();
        Date release = null;
        if(releaseStr.isEmpty())
        {
            return ValidationResult.error(Field.RELEASE, "Release date is required!");
        }
        else {
            try {
                release = sdf.parse(releaseStr);
            } catch (ParseException e) {
                return ValidationResult.error(Field.RELEASE, "Must be a valid date (yyyy-MM-dd)");
            }
        }

        sbDuration.getProgressDrawable().setTint(Color.BLACK);

        int duration = sbDuration.getProgress();
        if(duration == 0)
        {
            return ValidationResult.error(Field.DURATION, "Duration must be greater than 0!");
        }

        String poster = etPoster.getText().toString().trim();
        if(poster.isEmpty())
        {
            return ValidationResult.error(Field.POSTER, "Poster URL is required!");
        }
        else {
            if(!Patterns.WEB_URL.matcher(poster).matches())
            {
                return ValidationResult.error(Field.POSTER, "Must be a valid URL!");
            }
        }

        int checkedRadioButtonId = rgGuidance.getCheckedRadioButtonId();
        RadioButton rb = findViewById(checkedRadioButtonId);
        String guidance = rb.getText().toString();

        movie.setTitle(title);
        movie.setBudget(budget);
        movie.setDuration(duration);
        movie.setRelease(release);
        movie.setPosterUrl(poster);
        movie.setGenre(GenreEnum.valueOf(spGenre.getSelectedItem().toString()));
        movie.setRating(rbRating.getRating());
        movie.setWatched(swWatched.isChecked());
        movie.setpGuidance(ParentalGuidanceEnum.valueOf(guidance));

        Log.i("MovieActivityTag", movie.toString());
        return ValidationResult.valid();
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
            movie=intent.getParcelableExtra("movie");

            etTitle.setActivated(false);
            etRelease.setActivated(false);
            completeForm(movie);
        }
        else {
            finish();
        }
    }

    private void completeForm(Movie movie) {
        etTitle.setText(movie.getTitle());
        etBudget.setText(movie.getBudget().toString());
        etRelease.setText(sdf.format(movie.getRelease()));
        spGenre.setSelection(movie.getGenre().ordinal());
        sbDuration.setProgress(movie.getDuration());
        rbRating.setRating(movie.getRating());
        etPoster.setText(movie.getPosterUrl());
        swWatched.setChecked(movie.getWatched());
        for(int i=0;i<rgGuidance.getChildCount();i++){
            RadioButton rb=(RadioButton)rgGuidance.getChildAt(i);
            if(rb.getText().toString().equalsIgnoreCase(movie.getpGuidance().toString())){
                rb.setChecked(true);
            }
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