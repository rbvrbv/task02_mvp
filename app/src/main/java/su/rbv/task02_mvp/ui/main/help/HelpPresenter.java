package su.rbv.task02_mvp.ui.main.help;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.Const;
import su.rbv.task02_mvp.data.IJSONLoader;

@InjectViewState
public class HelpPresenter extends MvpPresenter<IHelpView> {

    @Inject
    IJSONLoader jsonLoader;

    private Disposable getCategoriesRX;

    public HelpPresenter() {
        App.getComponent().inject(this);
    }

    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showProgressBar();
        getCategoriesRX = jsonLoader.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> {
                            getViewState().hideProgressBar();
                            getViewState().setCategoryData(categories);
                        },
                        error -> {
                            getViewState().hideProgressBar();
                            getViewState().showError(error.toString());
                        }
                );
    }

    @Override
    public void onDestroy() {
        if (getCategoriesRX != null && !getCategoriesRX.isDisposed()) {
            getCategoriesRX.dispose();
        }
        super.onDestroy();
    }

}
