package su.rbv.task02_mvp.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface JSONCategoryDao {

    @Query("SELECT * FROM JSONCategory")
    Single<List<JSONCategory>> getCategories();

    @Insert
    void insert(JSONCategory category);

    @Query("DELETE FROM JSONCategory")
    void flush();
}
