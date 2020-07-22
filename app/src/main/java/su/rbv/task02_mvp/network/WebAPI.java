package su.rbv.task02_mvp.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import su.rbv.task02_mvp.data.JSONCategory;
import su.rbv.task02_mvp.data.JSONEvent;

public interface WebAPI {

    @GET("categories.json")
    Single<List<JSONCategory>> getCategories();

    @GET("events.json")
    Single<List<JSONEvent>> getEvents();

}
