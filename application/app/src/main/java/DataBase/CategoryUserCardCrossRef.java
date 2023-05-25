package DataBase;

import androidx.room.Entity;

@Entity(primaryKeys = {"categoryId", "userCardId"})
public class CategoryUserCardCrossRef {
    long categoryId;
    long userCardId;

    public CategoryUserCardCrossRef() {
    }
    public CategoryUserCardCrossRef(long categoryId, long userCardId) {
        this.categoryId = categoryId;
        this.userCardId = userCardId;
    }
}