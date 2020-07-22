package su.rbv.task02_mvp.ui.main.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import su.rbv.task02_mvp.R;

public class NewsFilterListAdapter extends RecyclerView.Adapter<NewsFilterListAdapter.NewsFilterListViewHolder> {

    public static class NewsFilterListViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public SwitchCompat switchCategory;
        public View divider;

        NewsFilterListViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.news_filter_list_item_text);
            divider = v.findViewById(R.id.news_filter_list_item_divider);
            switchCategory = v.findViewById(R.id.news_filter_list_item_switch);
        }
    }

    private List<String> categories;
    private List<String> selectedCategories;
    private Context ctx;

    NewsFilterListAdapter(Context ctx, List<String> categories, List<String> selectedCategories) {
        super();
        this.ctx = ctx;
        this.categories = categories;
        this.selectedCategories = selectedCategories;
    }

    @NonNull
    @Override
    public NewsFilterListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new NewsFilterListViewHolder(LayoutInflater.from(ctx).inflate(R.layout.news_filter_list_item, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFilterListViewHolder viewHolder, final int position) {
        viewHolder.text.setText(categories.get(position));
        SwitchCompat switchCategory = viewHolder.switchCategory;
        // set checked if current name is selected
        switchCategory.setChecked(selectedCategories.contains(categories.get(position)));
        switchCategory.setOnCheckedChangeListener((b, isChecked) -> {
            // add or remove selected name to (from) selectedCategory list
            if (isChecked) {
                selectedCategories.add(categories.get(position));
            } else {
                selectedCategories.remove(categories.get(position));
            }
        });
        // if last item don't show divider
        if (position == getItemCount() - 1) {
            viewHolder.divider.setVisibility(View.GONE);
        } else {
            viewHolder.divider.setVisibility(View.VISIBLE);
        }
    }

}
