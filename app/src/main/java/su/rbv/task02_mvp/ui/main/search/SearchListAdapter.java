package su.rbv.task02_mvp.ui.main.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.JSONEvent;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder> {

    public static class SearchListViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public View divider;

        SearchListViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.search_pager_list_item_text);
            divider = v.findViewById(R.id.search_pager_list_item_divider);
        }
    }

    private List<JSONEvent> listSearchItems;
    private Context ctx;

    SearchListAdapter(Context ctx, List<JSONEvent> listSearchItems) {
        super();
        this.ctx = ctx;
        this.listSearchItems = listSearchItems;
    }

    @NonNull
    @Override
    public SearchListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new SearchListViewHolder(LayoutInflater.from(ctx).inflate(R.layout.search_pager_list_item, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return listSearchItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListViewHolder viewHolder, final int position) {
        viewHolder.text.setText(listSearchItems.get(position).name);
        // if last item don't show divider
        if (position == getItemCount() - 1) {
            viewHolder.divider.setVisibility(View.GONE);
        } else {
            viewHolder.divider.setVisibility(View.VISIBLE);
        }
    }

}
