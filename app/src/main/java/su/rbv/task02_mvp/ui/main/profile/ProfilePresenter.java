package su.rbv.task02_mvp.ui.main.profile;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.data.IConf;

@InjectViewState
public class ProfilePresenter extends MvpPresenter<IProfileView> {

    @Inject
    IConf conf;

    ProfilePresenter() {
        App.getComponent().inject(this);

        String photoPath = conf.loadPictureFilename();
        if (photoPath == null) {
            getViewState().setMainImagePlaceholder();
        } else {
            getViewState().showMainPicture(photoPath);
        }
    }

    void deletePhoto() {
        conf.savePictureFilename(null);       // null filename - photo deleted
        getViewState().setMainImagePlaceholder();
    }

    void savePictureFileName(String pictureFileName) {
        conf.savePictureFilename(pictureFileName);
        getViewState().showMainPicture(pictureFileName);
    }

}
