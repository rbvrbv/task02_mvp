package su.rbv.task02_mvp.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
    entities = { JSONCategory.class, JSONEvent.class},
    version = 2,
    exportSchema = false
)
public abstract class JSONDatabase extends RoomDatabase {
    public abstract JSONCategoryDao jsonCategoryDao();
    public abstract JSONEventDao jsonEventDao();
}
