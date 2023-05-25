package DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserCardDao {

    @Query("SELECT * FROM UserCardEntity")
    List<UserCardEntity> getAll();

    @Query("SELECT * FROM UserCardEntity WHERE userCardId = :id")
    UserCardEntity getById(long id);

    @Insert
    void insert(UserCardEntity userCardEntity);

    @Update
    void update(UserCardEntity userCardEntity);

    @Delete
    void delete(UserCardEntity userCardEntity);

    @Query("DELETE FROM UserCardEntity WHERE userCardId = :id")
    void deleteById(long id);
}






