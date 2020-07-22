package su.rbv.task02_mvp.ui.main.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding3.appcompat.RxSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.Const;
import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.JSONEvent;

public class SearchFragment extends MvpAppCompatFragment implements ISearchView, VoiceSearchListener {

    private ViewPager viewPager;
    private View progressBar;
    private SearchView searchView;

    private MenuItem item;

    @Inject
    @InjectPresenter
    SearchPresenter searchPresenter;

    @ProvidePresenter
    SearchPresenter providePresenter() {
        App.getComponent().inject(this);
        return searchPresenter;
    }

    public SearchFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Toolbar toolbar = view.findViewById(R.id.search_toolbar);
        toolbar.inflateMenu(R.menu.search_menu);
        item = toolbar.getMenu().findItem(R.id.search_menu_item);
        progressBar = view.findViewById(R.id.search_progressbar);
        searchView = (SearchView) item.getActionView();
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        // init help pager pages
        viewPager = view.findViewById(R.id.search_pager);
        List<String> pages = new ArrayList<>();
        pages.add(getContext().getResources().getString(R.string.search_tab_1));
        pages.add(getContext().getResources().getString(R.string.search_tab_2));
        viewPager.setAdapter(new SearchPagerAdapter(getContext(), pages));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                searchPresenter.setPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        TabLayout tabLayout = view.findViewById(R.id.search_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        searchPresenter.subscribeSearch(RxSearchView.queryTextChanges(searchView));

        return view;
    }

    @Override
    public void setQuery(String query) {
        if (query != null && !query.isEmpty()) {
            item.expandActionView();
            searchView.setQuery(query, false);
            searchView.clearFocus();
        }
    }

    @Override
    public void setVoiceQueryString(String voiceQuery) {
        searchView.setQuery(voiceQuery, false);
    }

    @Override
    public void setPage(int pageNumber) {
        viewPager.setCurrentItem(pageNumber);
    }

    /**
     *
     * load found events list and keywords to page
     *
     */
    @Override
    public void showEvents(List<JSONEvent> events, String keywords) {
        View page = viewPager.getChildAt(Const.SEARCH_PAGE_EVENTS);
        SearchPagerAdapter adapter = (SearchPagerAdapter) viewPager.getAdapter();
        if (adapter != null) {
            adapter.setList(page, events, keywords);
        }
    }

    @Override
    public void onDestroyView() {
        searchPresenter.unsubscribeSearch();
        searchPresenter.saveQueryParameters(searchView.getQuery().toString(), viewPager.getCurrentItem());
        super.onDestroyView();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void enableSearchItem() {
        item.setEnabled(true);
    }

    @Override
    public void disableSearchItem() {
        item.collapseActionView();
        item.setEnabled(false);
    }

}