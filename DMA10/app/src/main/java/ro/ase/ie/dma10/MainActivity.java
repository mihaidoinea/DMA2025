package ro.ase.ie.dma10;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getName();
    private static final int REQUEST_PERMISSION_WRITE = 1001;
    Button btnOpenSharedPrefs;
    EditText etUserName;
    SharedPreferences sharedPreferences;

    SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener =
            (sharedPreferences, key) -> {
                String value = sharedPreferences.getString(key,"Default");
                Log.d(TAG,"Value for key '" + key + "' was changed with value: " + value);
            };
    private boolean permissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenSharedPrefs = findViewById(R.id.button2);
        btnOpenSharedPrefs.setOnClickListener(this);
        etUserName = findViewById(R.id.editTextTextPersonName);
        //get handler to a custom shared preferences file
        sharedPreferences = this.getSharedPreferences("custom_prefs", MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

    }

    public void writeCustomSharedPrefs(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", etUserName.getText().toString());
        editor.commit();

        String value = sharedPreferences.getString("username", "Default");
        Log.d(TAG, "Value: " + value);
    }

    public void writeActivitySharedPrefs(View view) {

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit1 = preferences.edit();
        edit1.putString("activityKey", "Activity preference value");
        edit1.commit();

    }

    public void writePreferenceManagerSharedPrefs(View view) {
        SharedPreferences general = PreferenceManager.getDefaultSharedPreferences(this);
        general.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        SharedPreferences.Editor edit = general.edit();
        edit.putString("signature", "Doinea Mihai");
        edit.commit();

        writePreferenceToFile(general);

    }

    public void writePreferenceToFile(SharedPreferences general)
    {
        String signature = general.getString("signature", "Default");
        Log.d(TAG, "Signature: " + signature);

        FileOutputStream fos = null;
        try {
            fos = this.openFileOutput("SharedPreferenceValues.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(signature);
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void readFile(View view) throws IOException {

        try(InputStream inputStream = getResources().openRawResource(R.raw.movies))
        {
            String fileContent = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining("\n"));
            Log.d(TAG, "File content: " + fileContent);
        }
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private File getFile()
    {
        return new File(Environment.getExternalStorageDirectory(), "new_file");
        Environment.D
    }

    public void writeFile(View view) {
        if(!permissionGranted)
        {
            permissionGranted = checkPermissions();
            if(!permissionGranted)
                return;
        }
        File file = getFile();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write("New Value to be written");
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}