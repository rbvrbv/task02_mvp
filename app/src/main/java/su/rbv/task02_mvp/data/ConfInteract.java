package su.rbv.task02_mvp.data;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import su.rbv.task02_mvp.App;

public class ConfInteract {

    private PublishSubject<ArrayList<String>> publishSubjectSelectedCategory;   // changes selected category observable

    @Inject
    IConf conf;

    public ConfInteract() {
        App.getComponent().inject(this);
        publishSubjectSelectedCategory = PublishSubject.create();
    }

    public Observable<ArrayList<String>> getObservableSelectedCategories() {
        return publishSubjectSelectedCategory;
    }

    public void saveSelectedCategories(ArrayList<String> selectedCategories) {
        if (conf.saveSelectedCategories(selectedCategories)) {
            publishSubjectSelectedCategory.onNext(selectedCategories);
        }
    }
}
