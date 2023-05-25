package DataBase;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserCardDao_Impl implements UserCardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserCardEntity> __insertionAdapterOfUserCardEntity;

  private final EntityDeletionOrUpdateAdapter<UserCardEntity> __deletionAdapterOfUserCardEntity;

  private final EntityDeletionOrUpdateAdapter<UserCardEntity> __updateAdapterOfUserCardEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public UserCardDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserCardEntity = new EntityInsertionAdapter<UserCardEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `UserCardEntity` (`userCardId`,`text`,`imagePath`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserCardEntity value) {
        stmt.bindLong(1, value.userCardId);
        if (value.text == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.text);
        }
        if (value.imagePath == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.imagePath);
        }
      }
    };
    this.__deletionAdapterOfUserCardEntity = new EntityDeletionOrUpdateAdapter<UserCardEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `UserCardEntity` WHERE `userCardId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserCardEntity value) {
        stmt.bindLong(1, value.userCardId);
      }
    };
    this.__updateAdapterOfUserCardEntity = new EntityDeletionOrUpdateAdapter<UserCardEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `UserCardEntity` SET `userCardId` = ?,`text` = ?,`imagePath` = ? WHERE `userCardId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserCardEntity value) {
        stmt.bindLong(1, value.userCardId);
        if (value.text == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.text);
        }
        if (value.imagePath == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.imagePath);
        }
        stmt.bindLong(4, value.userCardId);
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM UserCardEntity WHERE userCardId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final UserCardEntity userCardEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserCardEntity.insert(userCardEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final UserCardEntity userCardEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUserCardEntity.handle(userCardEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final UserCardEntity userCardEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUserCardEntity.handle(userCardEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final long id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public List<UserCardEntity> getAll() {
    final String _sql = "SELECT * FROM UserCardEntity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserCardId = CursorUtil.getColumnIndexOrThrow(_cursor, "userCardId");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final List<UserCardEntity> _result = new ArrayList<UserCardEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UserCardEntity _item;
        _item = new UserCardEntity();
        _item.userCardId = _cursor.getLong(_cursorIndexOfUserCardId);
        if (_cursor.isNull(_cursorIndexOfText)) {
          _item.text = null;
        } else {
          _item.text = _cursor.getString(_cursorIndexOfText);
        }
        if (_cursor.isNull(_cursorIndexOfImagePath)) {
          _item.imagePath = null;
        } else {
          _item.imagePath = _cursor.getString(_cursorIndexOfImagePath);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public UserCardEntity getById(final long id) {
    final String _sql = "SELECT * FROM UserCardEntity WHERE userCardId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserCardId = CursorUtil.getColumnIndexOrThrow(_cursor, "userCardId");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final UserCardEntity _result;
      if(_cursor.moveToFirst()) {
        _result = new UserCardEntity();
        _result.userCardId = _cursor.getLong(_cursorIndexOfUserCardId);
        if (_cursor.isNull(_cursorIndexOfText)) {
          _result.text = null;
        } else {
          _result.text = _cursor.getString(_cursorIndexOfText);
        }
        if (_cursor.isNull(_cursorIndexOfImagePath)) {
          _result.imagePath = null;
        } else {
          _result.imagePath = _cursor.getString(_cursorIndexOfImagePath);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
