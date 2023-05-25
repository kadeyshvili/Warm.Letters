package DataBase;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CategoryAndCards {
    @Embedded
    public CategoryEntity category;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "userCardId",
            entity = UserCardEntity.class,
            associateBy = @Junction(
                    value = CategoryUserCardCrossRef.class,
                    parentColumn = "categoryId",
                    entityColumn = "userCardId")
    )
    public List<UserCardEntity> userCards;
}
