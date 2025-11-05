package ro.ase.csie.dma.dma05;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<DataItem> itemArray;
    static int counter = 0;

    public CustomAdapter(Context mContext, ArrayList<DataItem> items) {
        this.mContext = mContext;
        this.itemArray = items;
    }
    @Override
    public int getCount() {
        return itemArray.size();
    }

    @Override
    public Object getItem(int position) {
        return itemArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemArray.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomHolder customHolder;

        Log.d("CustomAdapter", "getView_" + counter++);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            customHolder = new CustomHolder(convertView);
            convertView.setTag(customHolder);
        } else {
            customHolder = (CustomHolder) convertView.getTag();
        }

        DataItem item = (DataItem) getItem(position);
        customHolder.txtId.setText(item.getId().toString());
        customHolder.txtName.setText(item.getName());
        customHolder.checkBox.setChecked(item.getActive());

        return convertView;
    }



  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext, "BaseAdapter: " + itemArray.get(position).toString(), Toast.LENGTH_SHORT).show();
    }*/

    private class CustomHolder {
        public TextView txtId;
        public TextView txtName;
        public CheckBox checkBox;

        CustomHolder(View convertView) {
            txtId = convertView.findViewById(R.id.txtId);
            txtName = convertView.findViewById(R.id.txtName);
            checkBox = convertView.findViewById(R.id.active);
        }
    }
}
