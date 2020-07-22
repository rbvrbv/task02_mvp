package su.rbv.task02_mvp.ui.main.news;

import java.util.ArrayList;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.data.ConfInteract;

@InjectViewState
public class NewsFilterPresenter extends MvpPresenter<INewsFilterView> {

    @Inject
    ConfInteract confInteract;

    NewsFilterPresenter() {
        App.getComponent().inject(this);
    }

    void onClickOkButton(ArrayList<String> currentSelectedCategories) {
        // save selected categories (send signal to refresh events list inside)
        confInteract.saveSelectedCategories(currentSelectedCategories);
    }

}
