package su.rbv.task02_mvp.ui.main.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.ui.main.IMainView;
import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.JSONEvent;


public class NewsFragment extends MvpAppCompatFragment implements INewsView {

    private View progressBar;
    private RecyclerView listNews;

    private MenuItem filterMenuItem;

    private LinearLayoutManager listNewsLayoutManager;

    @Inject
    @InjectPresenter
    NewsPresenter newsPresenter;

    @ProvidePresenter
    NewsPresenter providePresenter() {
        App.getComponent().inject(this);
        return newsPresenter;
    }

    public NewsFragment() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        progressBar = view.findViewById(R.id.news_progressbar);
        listNews = view.findViewById(R.id.news_list);

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.news_toolbar);
        toolbar.inflateMenu(R.menu.news_menu);
        filterMenuItem = toolbar.getMenu().findItem(R.id.news_menu_item);

        listNewsLayoutManager = new LinearLayoutManager(getContext());
        listNews.setLayoutManager(listNewsLayoutManager);

        filterMenuItem.setOnMenuItemClickListener((m) -> {
            ((IMainView) requireActivity()).openNewsFilterFragment();
            return true;
        });

        return view;
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
    public void showError(String message) {
        Toast.makeText(requireContext(), getResources().getString(R.string.categories_json_error) + ": " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEventDetails(JSONEvent event, String strDate) {
        ((IMainView) requireActivity()).openEventDetailFragment(event, strDate);
    }

    @Override
    public void enableFilterMenuItem() {
        filterMenuItem.setEnabled(true);
    }

    @Override
    public void disableFilterMenuItem() {
        filterMenuItem.setEnabled(false);
    }

    @Override
    public void setNewsListPosition(int listPosition) {
        if (listNews != null) {
            listNews.scrollToPosition(listPosition);
        }
    }


    @Override
    public void onDestroyView() {
        newsPresenter.saveCurrentListPosition(listNewsLayoutManager.findFirstVisibleItemPosition());
        super.onDestroyView();
    }

    /**
     * set new adapter, if it is not initialized
     * or set new adapter data set
     *
     */
    @Override
    public void setEvents(List<JSONEvent> eventsList) {
        NewsListAdapter adapter = (NewsListAdapter) listNews.getAdapter();
        if (adapter == null) {
            listNews.setAdapter(new NewsListAdapter(getContext(), eventsList, newsPresenter));
        } else {
            DiffUtil.DiffResult newsDiffResult = DiffUtil.calculateDiff(new NewsDiffUtil(adapter.getData(), eventsList));
            adapter.setData(eventsList);
            newsDiffResult.dispatchUpdatesTo(adapter);
        }
    }

}