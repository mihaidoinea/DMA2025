package ro.ase.ie.g1107_s04.activities;

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

import ro.ase.ie.g1107_s04.R;
import ro.ase.ie.g1107_s04.model.Movie;

public class MovieActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etBudget;
    private EditText etReleaseDate;
    private EditText etPosterURL;
    private Spinner spGenre;
    private SeekBar sbDuration;
    private RatingBar rbRating;
    private Switch swWatched;
    private RadioGroup rgGuidance;
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
        int operationCode = intent.getIntExtra("operation_code", 0);
        if(operationCode != 0)
        {
            String btnMessage = operationCode == 100?"Save Movie":"Update Movie";
            btnMovieAction.setText(btnMessage);
            if(operationCode == 100)
                movie = new Movie();
            else {
                //get the movie from the intent
            }
        }
    }

    private void initializeEvents() {
        btnMovieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setTitle(etTitle.getText().toString());
            }
        });
    }

    private void initializeControls() {
        etTitle = findViewById(R.id.etTitle);
        etBudget = findViewById(R.id.etBudget);
        etReleaseDate = findViewById(R.id.etRelease);
        etPosterURL = findViewById(R.id.etPoster);
        spGenre = findViewById(R.id.spGenre);
        sbDuration = findViewById(R.id.sbDuration);
        rbRating = findViewById(R.id.rbRating);
        swWatched = findViewById(R.id.swWatched);
        rgGuidance = findViewById(R.id.rgApproval);
        btnMovieAction = findViewById(R.id.btnMovieAction);
    }
}