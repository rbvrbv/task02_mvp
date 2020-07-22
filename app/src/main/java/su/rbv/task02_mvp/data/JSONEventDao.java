package su.rbv.task02_mvp.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface JSONEventDao {

    @Query("SELECT * FROM JSONEvent")
    Single<List<JSONEvent>> getEvents();

    @Insert
    void insert(JSONEvent event);

    @Query("DELETE FROM JSONEvent")
    void flush();
}
