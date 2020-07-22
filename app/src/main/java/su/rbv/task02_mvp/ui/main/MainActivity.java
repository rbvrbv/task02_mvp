package su.rbv.task02_mvp.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import moxy.MvpAppCompatActivity;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import su.rbv.task02_mvp.Const;
import su.rbv.task02_mvp.ui.main.event.EventDetailFragment;
import su.rbv.task02_mvp.ui.main.help.HelpFragment;
import su.rbv.task02_mvp.ui.main.news.NewsFilterFragment;
import su.rbv.task02_mvp.ui.main.news.NewsFragment;
import su.rbv.task02_mvp.ui.main.profile.ProfileFragment;
import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.ui.main.search.SearchFragment;
import su.rbv.task02_mvp.data.JSONEvent;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends MvpAppCompatActivity implements IMainView {

    private static final String MAIN_FRAGMENT_TAG = "main_fragment_tag";
    private static final String NEWS_FILTER_FRAGMENT_TAG = "news_filter_fragment_tag";
    private static final String EVENT_DETAIL_FRAGMENT_TAG = "event_detail_fragment_tag";

    private FragmentManager fragmentManager;

    private BottomNavigationView bottomNavigationView;

    @InjectPresenter
    MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            mainPresenter.selectPage(item.getItemId());
            return true;
        });
    }

    @Override
    public void selectBottomMenuItem(int menuItem) {
        bottomNavigationView.getMenu().findItem(menuItem).setChecked(true);
    }

    /**
     * Show selected fragment, hide all others
     *
     * @param tagItemIdx - index of fragment to select into fragments array
     */
    @Override
    public void selectFragment(int tagItemIdx) {
        MvpAppCompatFragment fragment = null;
        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        switch (tagItemIdx) {
            case R.id.profile_bottom_navigation_item_profile:
                if (!(currentFragment instanceof ProfileFragment)) {
                    fragment = new ProfileFragment();
                }
                break;
            case R.id.profile_bottom_navigation_item_help:
                if (!(currentFragment instanceof HelpFragment)) {
                    fragment = new HelpFragment();
                }
                break;
            case R.id.profile_bottom_navigation_item_search:
                if (!(currentFragment instanceof SearchFragment)) {
                    fragment = new SearchFragment();
                }
                break;
            case R.id.profile_bottom_navigation_item_news:
                if (!(currentFragment instanceof NewsFragment)) {
                    fragment = new NewsFragment();
                }
                break;
        }
        if (fragment != null) {
            if (currentFragment == null)
                fragmentManager
                        .beginTransaction()
                        .add(R.id.main_fragment_container, fragment, MAIN_FRAGMENT_TAG)
                        .show(fragment)
                        .commit();
            else
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, fragment, MAIN_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void disableMainNavigation() {
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).setEnabled(false);
        }
    }

    @Override
    public void enableMainNavigation() {
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).setEnabled(true);
        }
    }

    @Override
    public void openNewsFilterFragment() {
        Fragment filterDialog = new NewsFilterFragment();
        fragmentManager
            .beginTransaction()
            .add(R.id.main_fragment_container, filterDialog, NEWS_FILTER_FRAGMENT_TAG)
            .commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void closeNewsFilterFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(NEWS_FILTER_FRAGMENT_TAG);
        if (fragment != null) {
            fragmentManager
                .beginTransaction()
                .remove(fragment)
                .commit();
        }
    }

    @Override
    public void openEventDetailFragment(JSONEvent event, String strDate) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BUNDLE_EVENT, event);
        bundle.putString(Const.BUNDLE_EVENT_STR_DATE, strDate);
        fragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .add(R.id.main_full_fragment_container, fragment, EVENT_DETAIL_FRAGMENT_TAG)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void closeEventDetailFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(EVENT_DETAIL_FRAGMENT_TAG);
        if (fragment != null) {
            fragmentManager
                .beginTransaction()
                .remove(fragment)
                .commit();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /// pass voice search string to searchView
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_SEARCH)) {
            Fragment fragment = fragmentManager.findFragmentByTag(MAIN_FRAGMENT_TAG);
            if (fragment instanceof SearchFragment) {
                ((SearchFragment) fragment).setVoiceQueryString(intent.getStringExtra(SearchManager.QUERY));
            }
        }
    }


}
