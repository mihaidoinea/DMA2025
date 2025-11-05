package ro.ase.ie.g1087_s04.activities;

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
import java.util.Date;
import java.util.Locale;

import ro.ase.ie.g1087_s04.R;
import ro.ase.ie.g1087_s04.model.GenreEnum;
import ro.ase.ie.g1087_s04.model.Movie;
import ro.ase.ie.g1087_s04.model.ParentalApprovalEnum;

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
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private Movie movie;
    private String MovieActivityTag = MovieActivity.class.getName().toString();

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

        String releaseStr = etRelease.getText().toString().trim();
        Date release = null;
        if (!releaseStr.isEmpty()) {
            try { release = df.parse(releaseStr); }
            catch (ParseException e) { return ValidationResult.Error("Use date format yyyy-MM-dd.", FieldEnum.RELEASE); }
        }
        else
            return ValidationResult.Error( "Release date is required.", FieldEnum.RELEASE);

        String budgetStr = etBudget.getText().toString().trim();
        double budget = 0;
        if (!budgetStr.isEmpty()) {
            try {
                budget = Double.parseDouble(budgetStr);
                if (budget < 0)
                    return ValidationResult.Error("Budget cannot be negative.", FieldEnum.BUDGET);
            } catch (NumberFormatException e) {
                return ValidationResult.Error("Budget must be a number.", FieldEnum.BUDGET);
            }
        }
        else
            return ValidationResult.Error( "Budget is required.", FieldEnum.BUDGET);

        String poster = etPosterUrl.getText().toString().trim();
        if (!poster.isEmpty() && !Patterns.WEB_URL.matcher(poster).matches()) {
            return ValidationResult.Error("Poster must be a valid URL.",FieldEnum.POSTER);
        }
        else if(!poster.isEmpty())
            return ValidationResult.Error( "Poster is required.", FieldEnum.POSTER);

        movie.setTitle(title);
        movie.setRelease(release);
        movie.setBudget(budget);
        movie.setPosterUrl(poster);
        movie.setGenre(GenreEnum.valueOf(spGenre.getSelectedItem().toString()));
        movie.setDuration(sbDuration.getProgress());
        movie.setRating(rbRating.getRating());
        movie.setWatched(swWatched.isChecked());

        int checkedId = rgGuidance.getCheckedRadioButtonId();
        String guidance = null;
        if (checkedId != -1) {
            RadioButton rb = findViewById(checkedId);
            guidance = rb.getText().toString();
        }
        movie.setpApproval(ParentalApprovalEnum.valueOf(guidance));

        Log.i(MovieActivityTag, movie.toString());

        return ValidationResult.Valid();
    }

    private void initializeEvents() {
        btnMovieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidationResult results = validateAndCreateMovie();

                if(results.ok) {
                    Intent intent = new Intent();
                    intent.putExtra("movie", movie);
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