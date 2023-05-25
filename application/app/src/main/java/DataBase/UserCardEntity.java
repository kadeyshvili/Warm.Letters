package DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserCardEntity {

    @PrimaryKey(autoGenerate = true)
    public long userCardId;

    public String text;
    public String imagePath;

    public UserCardEntity() {
    }

    public UserCardEntity(String text, String imagePath) {
        this.text = text;
        this.imagePath = imagePath;
    }
}

