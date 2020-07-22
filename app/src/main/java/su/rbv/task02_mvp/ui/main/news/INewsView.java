package su.rbv.task02_mvp.ui.main.news;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import su.rbv.task02_mvp.data.JSONEvent;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface INewsView extends MvpView {
    void showProgressBar();
    void hideProgressBar();
    void showError(String message);
    void enableFilterMenuItem();
    void disableFilterMenuItem();
    void setEvents(List<JSONEvent> eventsList);
    void setNewsListPosition(int listPosition);

    @StateStrategyType(SkipStrategy.class)
    void showEventDetails(JSONEvent event, String strDate);
}
