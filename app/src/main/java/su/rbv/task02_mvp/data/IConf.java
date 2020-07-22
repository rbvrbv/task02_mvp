package su.rbv.task02_mvp.data;

import java.util.ArrayList;
import io.reactivex.Observable;

public interface IConf {
    void savePictureFilename(String fileName);
    void saveAllCategories(ArrayList<String> allCategories);
    boolean saveSelectedCategories(ArrayList<String> selectedCategories);
    String loadPictureFilename();
    ArrayList<String> loadSelectedCategories();
    ArrayList<String> loadAllCategories();
}
