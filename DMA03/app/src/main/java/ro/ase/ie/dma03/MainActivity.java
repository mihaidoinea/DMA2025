package ro.ase.ie.dma03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 100;
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            String response = data.getStringExtra("response");
            Log.d("MainActivity", response);
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Log.d("MainActivity", "Second Activity was cancelled!");
        }

    }

    public void btnClick(View view)
    {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("key1", "Hello from MainActivity!");
        intent.putExtras(bundle);
//        intent.putExtra("key1", "Hello from MainActivity!");
//        startActivity(intent);
//        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
//        launcher.launch(intent);

        Intent callIntent = new Intent(Intent.ACTION_SEND);
        callIntent.putExtra(Intent.EXTRA_TEXT,"Hello from an Android app");
        callIntent.setType("text/plain");

        callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel: 0740111222"));
        callIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivity(callIntent);

    }

    public MainActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(new MyApp());
        }
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

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == RESULT_OK)
                        {
                            Intent data = o.getData();
                            if(data != null)
                                Log.i("MainActivity", data.getStringExtra("response"));
                        }
                    }
                });

        Log.d("MainActivity","onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("key","Value to be saved!");

        super.onSaveInstanceState(outState);
        Log.d("MainActivity","onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MainActivity","onRestoreInstanceState");
    }
}