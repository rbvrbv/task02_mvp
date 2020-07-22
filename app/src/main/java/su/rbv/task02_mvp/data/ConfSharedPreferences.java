package su.rbv.task02_mvp.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ConfSharedPreferences implements IConf {

    private static final String CFG_FILENAME = "preferences.cfg";
    private static final String CFG_SELECTED_CATEGORIES = "selected_categories";
    private static final String CFG_ALL_CATEGORIES = "all_categories";

    private SharedPreferences cfgFile;

    private ArrayList<String> selectedCategories;
    private ArrayList<String> allCategories;

    public ConfSharedPreferences(Context ctx) {
        cfgFile = ctx.getSharedPreferences(CFG_FILENAME, Context.MODE_PRIVATE);
    }

    @Override
    public void savePictureFilename(String fileName) {
        android.content.SharedPreferences.Editor editor = cfgFile.edit();
        editor.putString(CFG_FILENAME, fileName);
        editor.apply();
    }

    /**
     *   save selected categories list, if it has changed
     *   and put event to observable
     */
    @Override
    public boolean saveSelectedCategories(ArrayList<String> selectedCategories) {
        if (!selectedCategories.equals(this.selectedCategories)) {
            this.selectedCategories = selectedCategories;
            SharedPreferences.Editor editor = cfgFile.edit();
            editor.putStringSet(CFG_SELECTED_CATEGORIES, new HashSet<>(selectedCategories));
            editor.apply();
            return true;
        }
        return false;
    }

    /**
     *   save all categories list
     */
    @Override
    public void saveAllCategories(ArrayList<String> allCategories) {
        if (!allCategories.equals(this.allCategories)) {
            this.allCategories = allCategories;
            SharedPreferences.Editor editor = cfgFile.edit();
            editor.putStringSet(CFG_ALL_CATEGORIES, new HashSet<>(allCategories));
            editor.apply();
        }
    }

    @Override
    public String loadPictureFilename() {
        return cfgFile.getString(CFG_FILENAME, null);
    }

    @Override
    public ArrayList<String> loadSelectedCategories() {
        if (selectedCategories == null) {
            Set<String> set = cfgFile.getStringSet(CFG_SELECTED_CATEGORIES, null);
            if (set != null) {
                selectedCategories = new ArrayList<>(set);
            }
        }
        return selectedCategories;
    }

    @Override
    public ArrayList<String> loadAllCategories() {
        if (allCategories == null) {
            Set<String> set = cfgFile.getStringSet(CFG_ALL_CATEGORIES, null);
            if (set != null) {
                allCategories = new ArrayList<>(set);
            }
        }
        return allCategories;
    }

}
