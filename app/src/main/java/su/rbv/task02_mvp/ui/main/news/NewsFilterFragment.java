package su.rbv.task02_mvp.ui.main.news;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.ui.main.IMainView;
import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.IConf;


public class NewsFilterFragment extends MvpAppCompatFragment implements INewsFilterView {

    private RecyclerView newsFilterList;

    private ArrayList<String> currentSelectedCategories;
    private ArrayList<String> categories;

    @InjectPresenter
    NewsFilterPresenter newsFilterPresenter;

    @Inject
    IConf conf;

    public NewsFilterFragment() {
        App.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_filter, container, false);

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.news_filter_toolbar);
        toolbar.inflateMenu(R.menu.news_filter_menu);

        //if "ok" menu item clicked, set selectedCategory list in NewsFragment
        toolbar.getMenu().findItem(R.id.news_filter_menu_item_ok).setOnMenuItemClickListener((m) -> {
            newsFilterPresenter.onClickOkButton(currentSelectedCategories);
            close();
            return true;
        });

        // if "back arrow" click in toolbar, close NewsFilterFragment without selected category saving
        view.findViewById(R.id.news_filter_back).setOnClickListener((v) -> close());

        newsFilterList = view.findViewById(R.id.news_filter_list);
        newsFilterList.setLayoutManager(new LinearLayoutManager(getContext()));

        currentSelectedCategories = new ArrayList<>(conf.loadSelectedCategories());
        categories = conf.loadAllCategories();
        newsFilterList.setAdapter(new NewsFilterListAdapter(getContext(), categories, currentSelectedCategories));

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                close();
                return true;
            }
            return false;
        });

        return view;
    }

    private void close() {
        ((IMainView) requireActivity()).closeNewsFilterFragment();
    }

}