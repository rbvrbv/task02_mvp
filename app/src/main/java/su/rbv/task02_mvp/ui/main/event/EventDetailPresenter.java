package su.rbv.task02_mvp.ui.main.event;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EventDetailPresenter extends MvpPresenter<IEventDetailView> {

    EventDetailPresenter() {  }

    void onSendMailClick(String mailTo) {
        getViewState().sendMail(mailTo);
    }

    void onOpenSiteClick(String siteURL) {
        getViewState().openSite(siteURL);
    }

}
