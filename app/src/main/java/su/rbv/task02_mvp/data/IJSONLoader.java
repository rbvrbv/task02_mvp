package su.rbv.task02_mvp.data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import su.rbv.task02_mvp.data.JSONCategory;
import su.rbv.task02_mvp.data.JSONEvent;

public interface IJSONLoader {
    Observable<List<JSONEvent>> searchEvents(String query);
    Single<List<JSONCategory>> getCategories();
    Single<List<JSONEvent>> getEvents();
}
