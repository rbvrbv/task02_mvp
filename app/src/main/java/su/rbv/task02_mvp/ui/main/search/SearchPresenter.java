package su.rbv.task02_mvp.ui.main.search;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.Const;
import su.rbv.task02_mvp.data.IJSONLoader;
import su.rbv.task02_mvp.ui.main.search.ISearchView;

@InjectViewState
public class SearchPresenter extends MvpPresenter<ISearchView> {

    private static final int TIMEOUT_COLLECTING_KEYWORDS = 500;

    private Disposable searchDisposable;

    private String currentQuery;
    private int currentPage;

    private String savedQuery;
    private int savedPage;

    @Inject
    IJSONLoader jsonLoader;

    public SearchPresenter() {
        App.getComponent().inject(this);
    }

    public void attachView(ISearchView view) {
        super.attachView(view);
        getViewState().setPage(savedPage);
        getViewState().setQuery(savedQuery);
    }

    public void subscribeSearch(Observable<CharSequence> searchObservable) {
        searchDisposable = searchObservable
                .map(charSequence -> {
                    if (!charSequence.toString().isEmpty()) {
                        getViewState().showProgressBar();
                    }
                    return charSequence;
                })
                .debounce(TIMEOUT_COLLECTING_KEYWORDS, TimeUnit.MILLISECONDS)
                .switchMap(query -> {
                    currentQuery = query.toString();
                    return jsonLoader.searchEvents(currentQuery);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(events -> {
                    if (currentPage == Const.SEARCH_PAGE_EVENTS) {
                        getViewState().showEvents(events, currentQuery);
                    }
                    getViewState().hideProgressBar();
                });
    }

    public void unsubscribeSearch() {
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }
    }

    public void setPage(int pageNumber) {
        currentPage = pageNumber;
        if (pageNumber == Const.SEARCH_PAGE_EVENTS) {
            getViewState().enableSearchItem();
        } else {
            getViewState().disableSearchItem();
            getViewState().hideProgressBar();
        }

    }

    public void saveQueryParameters(String query, int pageNumber) {
        savedQuery = query;
        savedPage = pageNumber;
    }

}
