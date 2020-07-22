package su.rbv.task02_mvp.ui.main.news;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.data.ConfInteract;
import su.rbv.task02_mvp.data.IConf;
import su.rbv.task02_mvp.data.IJSONLoader;
import su.rbv.task02_mvp.data.JSONCategory;
import su.rbv.task02_mvp.data.JSONEvent;

@InjectViewState
public class NewsPresenter extends MvpPresenter<INewsView> {

    private List<JSONEvent> events;         // all events
    private List<JSONCategory> categories;  // all categories

    private Integer currentListNewsPosition;

    private Disposable getEventsRX;
    private Disposable getCategoriesRX;
    private Disposable getSelectedCategoriesRX;

    @Inject
    IConf conf;

    @Inject
    ConfInteract confInteract;

    @Inject
    IJSONLoader jsonLoader;

    public NewsPresenter() {
        App.getComponent().inject(this);
    }

    protected void onFirstViewAttach() {

        // subscribe to selected categories list changes
        getSelectedCategoriesRX = confInteract.getObservableSelectedCategories().subscribe(this::setSelectedEvens);

        if (events != null && categories != null) {
            // data loaded already - set view data
            setSelectedEvens(conf.loadSelectedCategories());
        } else {
            // load events and categories data
            getViewState().showProgressBar();
            getViewState().disableFilterMenuItem();

            getEventsRX = jsonLoader.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(events -> {
                        this.events = events;
                        getCategoriesRX = jsonLoader.getCategories()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(categories -> {
                                    this.categories = categories;
                                    conf.saveAllCategories(createCategoriesStringList(categories));
                                    if (events.size() > 0 && categories.size() > 0) {
                                        getViewState().enableFilterMenuItem();
                                        ArrayList<String> selectedCategories = conf.loadSelectedCategories();
                                        if (selectedCategories == null) {
                                            // create default selected categories list
                                            // (send signal of change selected categories list inside)
                                            confInteract.saveSelectedCategories(createCategoriesStringList(categories));
                                        } else {
                                            setSelectedEvens(selectedCategories);
                                        }
                                    }
                                    currentListNewsPosition = 0;
                                    getViewState().hideProgressBar();
                                },
                                error -> {
                                    getViewState().hideProgressBar();
                                    getViewState().showError(error.toString());
                                });
                    },
                    error -> {
                        getViewState().hideProgressBar();
                        getViewState().showError(error.toString());
                    }
                );
        }
    }

    public void attachView(INewsView view) {
        super.attachView(view);
        if (currentListNewsPosition != null) {
            getViewState().setNewsListPosition(currentListNewsPosition);
        }
    }

    /**
     *
     * create list of category names from JSONCategory list
     *
     */
    private ArrayList<String> createCategoriesStringList(List<JSONCategory> categories) {
        ArrayList<String> stringCategories = new ArrayList<>();
        for (JSONCategory category : categories) {
            stringCategories.add(category.name);
        }
        return stringCategories;
    }

    /**
     *   find and return category id by name,
     *   or empty string if id not found
     *
     */
    private String getCategoryId(String categoryName) {
        for (JSONCategory category: categories) {
            if (category.name.equals(categoryName)) return category.id;
        }
        return "";
    }

    /**
     * genedate filtered list of events by selected categories
     *
     * @param selectedCategories - list with selected categories
     */
    private void setSelectedEvens(List<String> selectedCategories) {
        ArrayList<JSONEvent> selectedEvents = new ArrayList<>();

        for (JSONEvent event: events) {
            for (String category: selectedCategories) {
                if (event.category.equals(getCategoryId(category))) {
                    selectedEvents.add(event);
                    break;
                }
            }
        }
        getViewState().setEvents(selectedEvents);
    }

    void saveCurrentListPosition(int currentListNewsPosition) {
        this.currentListNewsPosition = currentListNewsPosition;
    }

    @Override
    public void onDestroy() {
        if (getEventsRX != null && !getEventsRX.isDisposed()) {
            getEventsRX.dispose();
        }
        if (getCategoriesRX != null && !getCategoriesRX.isDisposed()) {
            getCategoriesRX.dispose();
        }
        if (getSelectedCategoriesRX != null && !getSelectedCategoriesRX.isDisposed()) {
            getSelectedCategoriesRX.dispose();
        }
        super.onDestroy();
    }

    void openEventDetailFragment(JSONEvent event, String strDate) {
        getViewState().showEventDetails(event, strDate);
    }
}
