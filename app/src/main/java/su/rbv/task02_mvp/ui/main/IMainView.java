package su.rbv.task02_mvp.ui.main;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import su.rbv.task02_mvp.data.JSONEvent;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IMainView extends MvpView {
    void disableMainNavigation();
    void enableMainNavigation();
    void selectFragment(int tagItemIdx);
    void selectBottomMenuItem(int menuItem);
    void openNewsFilterFragment();
    void closeNewsFilterFragment();
    void openEventDetailFragment(JSONEvent event, String strDate);
    void closeEventDetailFragment();
}
