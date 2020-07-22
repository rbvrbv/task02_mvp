package su.rbv.task02_mvp.ui.main.help;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import su.rbv.task02_mvp.R;

public class HelpListAdapter extends RecyclerView.Adapter<HelpListAdapter.HelpListViewHolder> {

    public static class HelpListViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;

        HelpListViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.list_help_item_image);
            name = v.findViewById(R.id.list_help_item_name);
        }
    }

    private List<HelpListItem> listHelpItems;
    private Context ctx;

    HelpListAdapter(Context ctx, List<HelpListItem> listHelpItems) {
        super();
        this.ctx = ctx;
        this.listHelpItems = listHelpItems;
    }

    @NonNull
    @Override
    public HelpListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new HelpListViewHolder(LayoutInflater.from(ctx).inflate(R.layout.list_help_item, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return listHelpItems.size();
    }

    @Override
    public void onBindViewHolder(HelpListViewHolder viewHolder, final int position) {
        HelpListItem item = listHelpItems.get(position);
        viewHolder.image.setImageResource(item.getImageId());
        viewHolder.name.setText(item.getName());
    }

}
