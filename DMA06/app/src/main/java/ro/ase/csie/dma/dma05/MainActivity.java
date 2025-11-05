package ro.ase.csie.dma.dma05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private RecyclerView recyclerView;
    final ArrayList<DataItem> alist = new ArrayList<DataItem>();
    private AutoCompleteTextView autoCompleteTextView;
    DataItemAdapter dataItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DataItem[] values = new DataItem[]{
                new DataItem(1L, "Android", true),
                new DataItem(2L, "iPhone", true),
                new DataItem(3L, "WindowsMobile", false),
                new DataItem(4L, "Blackberry", true),
                new DataItem(5L, "WebOS", true),
                new DataItem(6L, "Ubuntu", true),
                new DataItem(7L, "Windows7", true),
                new DataItem(8L, "Max OS X", true),
                new DataItem(9L, "Linux", true),
                new DataItem(10L, "OS/2", false),
                new DataItem(11L, "Ubuntu", true),
                new DataItem(12L, "Windows7SE", false),
                new DataItem(13L, "Max OS 8", false),
                new DataItem(14L, "Linux Cento", false),
                new DataItem(15L, "OS/2", true),
                new DataItem(16L, "Ubuntu", true),
                new DataItem(17L, "Windows7", false),
                new DataItem(18L, "Max OS X", true),
                new DataItem(19L, "Linux", false),
                new DataItem(20L, "OS/2", true),
                new DataItem(21L, "Android", true),
                new DataItem(22L, "iPhone", true),
                new DataItem(23L, "WindowsMobile", true)};

//        listView = findViewById(R.id.lvItems);
        recyclerView= findViewById(R.id.rvItems);
//
        alist.addAll(Arrays.asList(values));
//        listView.setAdapter(new ArrayAdapter<DataItem>(this, android.R.layout.simple_list_item_1, alist));

/*
        CustomAdapter myAdapter = new CustomAdapter(this, alist);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "onItemClick:" + alist.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
*/

        dataItemAdapter = new DataItemAdapter(this, alist);
        recyclerView.setAdapter(dataItemAdapter);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> actvAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                alist.stream().map(DataItem::getName).distinct().collect(Collectors.toList()));
        autoCompleteTextView.setAdapter(actvAdapter);

    }
}