package DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity")
    List<CategoryEntity> getAll();

    @Query("SELECT * FROM CategoryEntity WHERE categoryId = :id")
    CategoryEntity getById(long id);

    @Insert
    void insert(CategoryEntity categoryEntity);

    @Update
    void update(CategoryEntity categoryEntity);

    @Delete
    void delete(CategoryEntity categoryEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoryUserCardCrossRef(CategoryUserCardCrossRef categoryUserCardCrossRef);

    @Transaction
    @Query("SELECT * FROM CategoryEntity")
    List<CategoryAndCards> getCategoriesAndCards();

    @Transaction
    @Query("SELECT * FROM CategoryEntity WHERE categoryId = :id")
    CategoryAndCards getCategoryAndCards(long id);

    @Query("DELETE FROM CategoryUserCardCrossRef WHERE categoryId = :categoryId AND userCardId = :userCardId")
    void deleteCategoryUserCardCrossRefById(long categoryId, long userCardId);

    @Query("DELETE FROM CategoryUserCardCrossRef WHERE userCardId = :userCardId")
    void deleteUserCardFromAllCategoriesById(long userCardId);

}
