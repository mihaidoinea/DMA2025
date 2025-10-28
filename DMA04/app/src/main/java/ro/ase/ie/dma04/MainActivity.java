package ro.ase.ie.dma04;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ViewStub viewStub;
    View inflatedLayout;

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

        initializeControls();

        inflatedLayout = viewStub.inflate();
        Button button = inflatedLayout.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add new control ...")
                        .setMessage("Are you sure you want to add a new control?")
                        .setCancelable(true)
                        .setPositiveButton("Do it!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = addButton();
                                Snackbar.make(MainActivity.this, inflatedLayout, "Cancel previous action.", Snackbar.LENGTH_LONG)
                                        .setAction("Cancel", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                LinearLayout extra = inflatedLayout.findViewById(R.id.extraLayout);
                                                Button viewById = extra.findViewById(id);
                                                extra.removeView(viewById);
                                            }
                                        }).show();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,"Object creation cancelled", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.itAbout)
        {
            Snackbar.make(MainActivity.this, inflatedLayout, "Item selection", Snackbar.LENGTH_LONG)
                    .setAction("Cancel", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeControls() {
        viewStub = findViewById(R.id.viewStub);

    }

    public void showView(View view)
    {
        viewStub.setVisibility(View.VISIBLE);
    }

    public void hideView(View view)
    {
        viewStub.setVisibility(View.GONE);
    }

    int counter = 0;
    public int addButton()
    {
        LinearLayout extra = inflatedLayout.findViewById(R.id.extraLayout);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int hPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                displayMetrics);

        Button newBtn = new Button(MainActivity.this);
        newBtn.setText("Button_" + (++counter));
        newBtn.setId(counter);

        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        newBtn.setLayoutParams(layoutParams);

        extra.addView(newBtn);
        return counter;
    }
}