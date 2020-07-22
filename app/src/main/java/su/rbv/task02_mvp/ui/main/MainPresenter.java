package su.rbv.task02_mvp.ui.main;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import su.rbv.task02_mvp.R;

@InjectViewState
public class MainPresenter extends MvpPresenter<IMainView> {

    private Integer currentPage;

    MainPresenter() { }

    void selectPage(int pageId) {
        currentPage = pageId;
        getViewState().selectBottomMenuItem(pageId);
        getViewState().selectFragment(pageId);

    }

    protected void onFirstViewAttach() {
        if (currentPage == null) {
            currentPage = R.id.profile_bottom_navigation_item_help;
            getViewState().selectBottomMenuItem(currentPage);
            selectPage(currentPage);
        }
    }

}
