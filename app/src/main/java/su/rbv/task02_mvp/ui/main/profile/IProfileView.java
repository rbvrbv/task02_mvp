package su.rbv.task02_mvp.ui.main.profile;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IProfileView extends MvpView {
    void setMainImagePlaceholder();
    void showMainPicture(String photoPath);
}
