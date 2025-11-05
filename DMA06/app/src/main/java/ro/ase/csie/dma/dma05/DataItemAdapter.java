package ro.ase.csie.dma.dma05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    private static List<DataItem> mItems;
    private Context mContext;

    public DataItemAdapter(Context context, List<DataItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataItem item = mItems.get(position);
        holder.itemPosition = mItems.indexOf(item);
        holder.tvId.setText("Id:" + item.getId());
        holder.tvName.setText(item.getName());
        holder.cbActive.setChecked(item.getActive());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Clicked: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int itemPosition;
        public TextView tvId;
        public TextView tvName;
        public CheckBox cbActive;

        public Button btnDelete;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvId = itemView.findViewById(R.id.txtId);
            tvName = itemView.findViewById(R.id.txtName);
            cbActive = itemView.findViewById(R.id.active);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            cbActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     mItems.get(itemPosition).setActive(isChecked);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItems.remove(itemPosition);
                    notifyDataSetChanged();
                }
            });
        }
    }

}
