package su.rbv.task02_mvp.ui.main.news;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(SkipStrategy.class)
public interface INewsFilterView extends MvpView {
}
