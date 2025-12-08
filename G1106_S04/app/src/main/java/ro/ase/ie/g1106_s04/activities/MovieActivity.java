package ro.ase.ie.g1106_s04.activities;

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

import ro.ase.ie.g1106_s04.R;
import ro.ase.ie.g1106_s04.model.GenreEnum;
import ro.ase.ie.g1106_s04.model.Movie;
import ro.ase.ie.g1106_s04.model.ParentalGuidanceEnum;

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
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

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

            movie = intent.getParcelableExtra("movie");
            completeForm(movie);
        }
        else {
            finish();
        }
    }

    private void completeForm(Movie movie) {
        etTitle.setText(movie.getTitle());
        etTitle.setEnabled(false);
        etBudget.setText(movie.getBudget().toString());
        etRelease.setText(sdf.format(movie.getRelease()));
        etRelease.setEnabled(false);
        etPoster.setText(movie.getPosterUrl());
        spGenre.setSelection(movie.getGenre().ordinal());
        swWatched.setChecked(movie.getWatched());
        sbDuration.setProgress(movie.getDuration());
        rbRating.setRating(movie.getRating());
        for(int i=0; i < rgGuidance.getChildCount(); i++){
            RadioButton childAt = (RadioButton) rgGuidance.getChildAt(i);
            if (childAt.getText().toString().equalsIgnoreCase(movie.getpGuidance().toString())){
                childAt.setChecked(true);
            }
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
                    intent.putExtra("movie", movie);
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
                        case DURATION:
                            sbDuration.getProgressDrawable().setTint(Color.RED);
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
        String budgetStr = etBudget.getText().toString();
        double budget = 0.0;
        if(!budgetStr.isEmpty()) {
            try {
                budget = Double.parseDouble(budgetStr);
                if(budget <= 0)
                    return ValidationResult.error(Field.BUDGET, "Budget must be greater than 0!");
            } catch (NumberFormatException e) {
                return ValidationResult.error(Field.BUDGET, "Budget must be a valid number!");
            }
        }
        else
            return ValidationResult.error(Field.BUDGET, "Movie budget is required!");

        sbDuration.getProgressDrawable().setTint(Color.BLACK);
        int duration = sbDuration.getProgress();
        if(duration ==0)
        {
            return ValidationResult.error(Field.DURATION, "Movie duration should be greater than 0!");
        }

        String releaseDateStr = etRelease.getText().toString().trim();
        Date release = null;
        if(!releaseDateStr.isEmpty())
        {
            try {
                release = sdf.parse(releaseDateStr);
            } catch (ParseException e) {
                return ValidationResult.error(Field.RELEASE, "Data not in the correct format: yyyy-MM-dd");
            }
        }
        else
        {
            return ValidationResult.error(Field.RELEASE, "Release date is required!");
        }

        String poster = etPoster.getText().toString().trim();
        if(poster.isEmpty())
        {
            return ValidationResult.error(Field.POSTER, "Poster URL is required!");
        }
        else {
            if(!Patterns.WEB_URL.matcher(poster).matches())
                return ValidationResult.error(Field.POSTER, "Poster URL has incorrect format!");
        }

        int id = rgGuidance.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(id);
        String guidance = radioButton.getText().toString();

        movie.setTitle(title);
        movie.setBudget(budget);
        movie.setRelease(release);
        movie.setRating(rbRating.getRating());
        movie.setPosterUrl(poster);
        movie.setDuration(duration);
        movie.setGenre(GenreEnum.valueOf(spGenre.getSelectedItem().toString()));
        movie.setWatched(swWatched.isChecked());
        movie.setpGuidance(ParentalGuidanceEnum.valueOf(guidance));

        Log.i("MovieActivityTag", movie.toString());

        return ValidationResult.ok();
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