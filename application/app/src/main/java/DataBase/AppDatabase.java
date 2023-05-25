package DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        UserCardEntity.class,
        CategoryEntity.class,
        CategoryUserCardCrossRef.class
}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract UserCardDao userCardDao();
    public abstract CategoryDao categoryDao();
}
