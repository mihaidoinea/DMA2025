package ro.ase.ie.g1106_s03;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private final String MainActivityTag = getClass().getName().toLowerCase();

    public MainActivity() {
        Log.d(MainActivityTag, "MainActivity constructor");
    }

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
        Log.d(MainActivityTag, "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivityTag, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivityTag, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivityTag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivityTag, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivityTag, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(MainActivityTag, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(MainActivityTag, "onRestoreInstanceState");
    }
}