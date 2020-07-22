package su.rbv.task02_mvp.ui.main.search;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import su.rbv.task02_mvp.data.JSONEvent;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ISearchView extends MvpView {
    void showProgressBar();
    void hideProgressBar();
    void showEvents(List<JSONEvent> events, String keywords);
    void setQuery(String query);
    void setPage(int pageNumber);
    void enableSearchItem();
    void disableSearchItem();
}
