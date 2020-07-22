package su.rbv.task02_mvp;

import android.util.Log;

public class Const {
    public static final String BUNDLE_EVENT = "BUNDLE_EVENT";
    public static final String BUNDLE_EVENT_STR_DATE = "BUNDLE_EVENT_STR_DATE";

    public static final int SEARCH_PAGE_EVENTS = 0;
    public static final int SEARCH_PAGE_NKO = 1;

    public static final String CATEGORY_CHILDREN = "-1";
    public static final String CATEGORY_ADULTS = "0";
    public static final String CATEGORY_SENIORS = "1";
    public static final String CATEGORY_ANIMALS = "2";
    public static final String CATEGORY_EVENTS = "3";

    public static final String[] months = {
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    };

    public static final String INCORRECT_DATE_FORMAT = "Некорректная дата";

    public static final String INTENT_EMAIL_SCHEME = "mailto";
    public static final String INTENT_EMAIL_TITLE = "Написать письмо";

    public static void log(String s) {
        if (BuildConfig.DEBUG) Log.e("###", "===" + s + "===");
    }

}
