package su.rbv.task02_mvp.ui.main.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.JSONEvent;

public class SearchPagerAdapter extends PagerAdapter {

    private List<String> pages;
    private Context ctx;

    public SearchPagerAdapter(Context ctx, List<String> pages) {
        this.pages = pages;
        this.ctx = ctx;
    }

    @Override
    public int getCount(){
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup group, int position) {
        View view = ((LayoutInflater) Objects.requireNonNull(group.getContext().getSystemService(group.getContext().LAYOUT_INFLATER_SERVICE)))
                        .inflate(R.layout.search_pager_item, null);
        ((RecyclerView) view.findViewById(R.id.search_list)).setLayoutManager(new LinearLayoutManager(ctx));
        setList(view, null, null);
        group.addView(view);
        return view;
    }

    /**
     *  create RecyclerList content on pager's page
     *
     * @param searchPagerLayout - page with RecyclerView to create content
     * @param list - strings list with found events
     * @param keywords - search keywords
     */
    public void setList(View searchPagerLayout, List<JSONEvent> list, String keywords) {
        if (list != null && searchPagerLayout != null) {
            searchPagerLayout.post(() -> {
                ((RecyclerView) searchPagerLayout.findViewById(R.id.search_list)).setAdapter(new SearchListAdapter(ctx, list));
                if (keywords == null || keywords.isEmpty()) {
                    // show placeholder, hide search layout
                    searchPagerLayout.findViewById(R.id.search_list_layout).setVisibility(View.GONE);
                    searchPagerLayout.findViewById(R.id.search_placeholder_layout).setVisibility(View.VISIBLE);
                } else {
                    // hide placeholder, show search layout
                    searchPagerLayout.findViewById(R.id.search_list_layout).setVisibility(View.VISIBLE);
                    searchPagerLayout.findViewById(R.id.search_placeholder_layout).setVisibility(View.GONE);
                    ((TextView) searchPagerLayout.findViewById(R.id.search_keywords)).setText(keywords);
                    ((TextView) searchPagerLayout.findViewById(R.id.search_results_count)).setText(list.size() + "");
                    String event_str = ctx.getResources().getQuantityString(R.plurals.events, list.size() % 10, list.size());
                    ((TextView) searchPagerLayout.findViewById(R.id.search_results_events_string)).setText(event_str);
                }
            });
        }
    }

}
