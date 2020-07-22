package su.rbv.task02_mvp.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.network.Web;

public class JSONLoader implements IJSONLoader {

    private static final String CATEGORIES_JSON = "categories.json";
    private static final String EVENTS_JSON = "events.json";

    private static final String SRC_SYMBOL = ",";
    private static final String REPLACE_SYMBOL = " ";
    private static final String SPLIT_REGEX = "\\s+";

    private Context ctx;

    @Inject
    JSONDatabase jsonDB;

    @Inject
    Web web;

    public JSONLoader(Context ctx) {
        App.getComponent().inject(this);
        this.ctx = ctx;
    }

    /**
     * Get observable with found events names
     * search is carried out in event names and bodies
     *
     * @param query - string with keywords, dividers "\s" and ","
     *
     * @result - observable with JSONEvent list
     *
     */
    @Override
    public Observable<List<JSONEvent>> searchEvents(String query) {
        if (query == null || query.isEmpty()) {
            return Observable.just(Collections.emptyList());
        } else {
            String[] words = query.replace(SRC_SYMBOL, REPLACE_SYMBOL).split(SPLIT_REGEX);
            return getEvents().map(eventList -> {
                ArrayList<JSONEvent> result = new ArrayList<>();
                for (JSONEvent event : eventList) {
                    for (String subStr : words) {
                        if (event.name != null && event.description != null &&
                              (event.name.toLowerCase().contains(subStr.toLowerCase()) ||
                               event.description.toLowerCase().contains(subStr.toLowerCase())
                              )
                        ) {
                            result.add(event);
                            break;
                        }
                    }
                }
                return (List<JSONEvent>) result;
            }).toObservable();
        }
    }

    private boolean isNeedToPutCategoriesToDB = true;
    private boolean isNeedToPutEventsToDB = true;

    /**
     * Get categories list
     *
     */
    @Override
    public Single<List<JSONCategory>> getCategories() {
        return jsonDB.jsonCategoryDao().getCategories()
                .flatMap(categoryList -> {
                    if (categoryList.size() == 0) {
                        return web.getAPI().getCategories();
                    } else {
                        return Single.just(categoryList);
                    }
                })
                .onErrorResumeNext(error -> Single.just(loadCategoriesFromFile()))
                .map(categoryList -> {
                    if (isNeedToPutCategoriesToDB) {
                        isNeedToPutCategoriesToDB = false;
                        for (JSONCategory category: categoryList) {
                            jsonDB.jsonCategoryDao().insert(category);
                        }
                    }
                    return categoryList;
                });
    }

    /**
     * Get events list
     *
     */
    @Override
    public Single<List<JSONEvent>> getEvents() {
        return jsonDB.jsonEventDao().getEvents()
                .flatMap(eventsList -> {
                    if (eventsList.size() == 0) {
                        return web.getAPI().getEvents();
                    } else {
                        return Single.just(eventsList);
                    }
                })
                .onErrorResumeNext(error -> Single.just(loadEventsFromFile()))
                .map(eventsList -> {
                    if (isNeedToPutEventsToDB) {
                        isNeedToPutEventsToDB = false;
                        for (JSONEvent event: eventsList) {
                            jsonDB.jsonEventDao().insert(event);
                        }
                    }
                    return eventsList;
                });
    }


    /**=====================================================================================*/
    /**
     * Load category from assets JSON file
     *
     */
    private List<JSONCategory> loadCategoriesFromFile() {
        return new Gson().fromJson(AssetJSONFile(CATEGORIES_JSON), new TypeToken<ArrayList<JSONCategory>>(){}.getType());
    }

    /**
     * Load events list from file
     *
     */
    private List<JSONEvent> loadEventsFromFile() {
        return new Gson().fromJson(AssetJSONFile(EVENTS_JSON), new TypeToken<ArrayList<JSONEvent>>(){}.getType());
    }

    /**
     * Load asset JSON file to string
     */
    private String AssetJSONFile(String filename) {
        String json;
        try {
            InputStream is = ctx.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
        return json;
    }

}
