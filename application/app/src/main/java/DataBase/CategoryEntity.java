package DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategoryEntity {
    @PrimaryKey(autoGenerate = true)
    public long categoryId;

    public String name;

    public CategoryEntity() {
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    public CategoryEntity(long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}
