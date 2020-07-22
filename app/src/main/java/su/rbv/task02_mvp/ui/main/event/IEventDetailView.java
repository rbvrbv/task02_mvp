package su.rbv.task02_mvp.ui.main.event;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(SkipStrategy.class)
public interface IEventDetailView extends MvpView {
    void sendMail(String mailTo);
    void openSite(String siteURL);
}
