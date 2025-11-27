package ro.ase.ie.g1107_s04.activities;

import android.content.Intent;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import ro.ase.ie.g1107_s04.R;
import ro.ase.ie.g1107_s04.model.GenreEnum;
import ro.ase.ie.g1107_s04.model.ParentalGuidanceEnum;
import ro.ase.ie.g1107_s04.model.Movie;

public class MovieActivity extends AppCompatActivity {

    private static final String MOVIE_ACTIVITY_TAG = MovieActivity.class.getName().toString();
    private EditText etTitle;
    private EditText etBudget;
    private EditText etRelease;
    private EditText etPosterURL;
    private Spinner spGenre;
    private SeekBar sbDuration;
    private RatingBar rbRating;
    private Switch swWatched;
    private RadioGroup rgGuidance;
    private Button btnMovieAction;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private Movie movie;
    private Calendar calendar = Calendar.getInstance();

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
        int operationCode = intent.getIntExtra("operation_code", 0);
        if(operationCode != 0)
        {
            String btnMessage = operationCode == 100?"Save Movie":"Update Movie";
            btnMovieAction.setText(btnMessage);
            if(operationCode == 100)
                movie = new Movie();
            else if (operationCode == 200){
                movie=intent.getParcelableExtra("movie");
               preFillForm(movie);
            }
        }
    }

    private void preFillForm(Movie movie) {

        etTitle.setText(movie.getTitle());
        spGenre.setSelection(movie.getGenre().ordinal());
        etRelease.setText(df.format(movie.getRelease()));
        etPosterURL.setText(movie.getPosterUrl());
        etBudget.setText(movie.getBudget().toString());
        rbRating.setRating(movie.getRating());
        sbDuration.setProgress(movie.getDuration());
        swWatched.setChecked(movie.getWatched());
        for(var i=0;i<rgGuidance.getChildCount();i++) {
            RadioButton rb =  (RadioButton) rgGuidance.getChildAt(i);
            if (rb.getText().toString().equalsIgnoreCase(movie.getpGuidance().toString())) {
                rb.setChecked(true);
            }
        }

    }

    private void initializeEvents() {

        btnMovieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setTitle(etTitle.getText().toString());

                ValidationResult result = validateAndBuildMovie();
                if(result.ok == true) {

                    Intent intent = new Intent();
                    //set the movie as payload for this intent
                    intent.putExtra("movie", movie);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                if (!result.ok) {
                    // show first error inline (and you can accumulate more)
                    if (result.field == Field.TITLE) etTitle.setError(result.message);
                    else if (result.field == Field.BUDGET) etBudget.setError(result.message);
                    else if (result.field == Field.RELEASE) etRelease.setError(result.message);
                    else if (result.field == Field.POSTER) etPosterURL.setError(result.message);
                    Toast.makeText(MovieActivity.this, result.message, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void initializeControls() {
        etTitle = findViewById(R.id.etTitle);
        etBudget = findViewById(R.id.etBudget);
        etRelease = findViewById(R.id.etRelease);
        etPosterURL = findViewById(R.id.etPoster);
        spGenre = findViewById(R.id.spGenre);
        sbDuration = findViewById(R.id.sbDuration);
        rbRating = findViewById(R.id.rbRating);
        swWatched = findViewById(R.id.swWatched);
        rgGuidance = findViewById(R.id.rgApproval);
        btnMovieAction = findViewById(R.id.btnMovieAction);
    }

    private ValidationResult validateAndBuildMovie() {
        String title = etTitle.getText().toString().trim();
        if (title.isEmpty())
            return ValidationResult.error(Field.TITLE, "Movie title is required.");

        String budgetStr = etBudget.getText().toString().trim();
        double budget = 0;
        if (!budgetStr.isEmpty()) {
            try {
                budget = Double.parseDouble(budgetStr);
                if (budget < 0) return ValidationResult.error(Field.BUDGET, "Budget cannot be negative.");
            } catch (NumberFormatException e) {
                return ValidationResult.error(Field.BUDGET, "Budget must be a number.");
            }
        }
        else
            return ValidationResult.error(Field.BUDGET, "Movie budget is required.");

        String releaseStr = etRelease.getText().toString().trim();
        Date release = null;
        if (!releaseStr.isEmpty()) {
            try { release = df.parse(releaseStr); }
            catch (ParseException e) { return ValidationResult.error(Field.RELEASE, "Use date format yyyy-MM-dd."); }
        }
        else
            return ValidationResult.error(Field.RELEASE, "Release date is mandatory.");

        String genre = String.valueOf(spGenre.getSelectedItem());
        int duration = sbDuration.getProgress(); // minutes
        float rating = rbRating.getRating();
        boolean watched = swWatched.isChecked();
        int checkedId = rgGuidance.getCheckedRadioButtonId();
        String guidance = null;
        if (checkedId != -1) {
            RadioButton rb = findViewById(checkedId);
            guidance = rb.getText().toString();
        }

        String poster = etPosterURL.getText().toString().trim();
        if(poster.isEmpty())
        {
            return ValidationResult.error(Field.POSTER, "Poster URL is required.");
        }else if (!Patterns.WEB_URL.matcher(poster).matches()) {
            return ValidationResult.error(Field.POSTER, "Poster must be a valid URL.");
        }

        // Assign to the existing movie (create or update):
        if (movie == null) movie = new Movie();
        movie.setTitle(title);
        movie.setRelease(release);
        movie.setBudget(budget);
        movie.setPosterUrl(poster.isEmpty() ? null : poster);
        movie.setGenre(GenreEnum.valueOf(genre));
        movie.setDuration(duration);
        movie.setRating(rating);
        movie.setWatched(watched);
        movie.setpGuidance(ParentalGuidanceEnum.valueOf(guidance));

        Log.i(MOVIE_ACTIVITY_TAG, movie.toString());
        return ValidationResult.ok();
    }

    private enum Field { TITLE, RELEASE, BUDGET, POSTER, GENERIC }
    private static class ValidationResult {
        final boolean ok; final Field field; final String message;
        private ValidationResult(boolean ok, Field field, String message) {
            this.ok = ok; this.field = field; this.message = message;
        }
        static ValidationResult ok() { return new ValidationResult(true, Field.GENERIC, null); }
        static ValidationResult error(Field f, String msg) { return new ValidationResult(false, f, msg); }
    }

}