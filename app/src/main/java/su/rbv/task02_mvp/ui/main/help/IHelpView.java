package su.rbv.task02_mvp.ui.main.help;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import su.rbv.task02_mvp.data.JSONCategory;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IHelpView extends MvpView {
    void showProgressBar();
    void hideProgressBar();
    void showError(String message);
    void setCategoryData(List<JSONCategory> categories);
}
