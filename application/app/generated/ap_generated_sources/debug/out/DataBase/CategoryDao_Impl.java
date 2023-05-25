package DataBase;

import android.database.Cursor;
import androidx.collection.LongSparseArray;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CategoryDao_Impl implements CategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CategoryEntity> __insertionAdapterOfCategoryEntity;

  private final EntityInsertionAdapter<CategoryUserCardCrossRef> __insertionAdapterOfCategoryUserCardCrossRef;

  private final EntityDeletionOrUpdateAdapter<CategoryEntity> __deletionAdapterOfCategoryEntity;

  private final EntityDeletionOrUpdateAdapter<CategoryEntity> __updateAdapterOfCategoryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCategoryUserCardCrossRefById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUserCardFromAllCategoriesById;

  public CategoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategoryEntity = new EntityInsertionAdapter<CategoryEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `CategoryEntity` (`categoryId`,`name`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CategoryEntity value) {
        stmt.bindLong(1, value.categoryId);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
      }
    };
    this.__insertionAdapterOfCategoryUserCardCrossRef = new EntityInsertionAdapter<CategoryUserCardCrossRef>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `CategoryUserCardCrossRef` (`categoryId`,`userCardId`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CategoryUserCardCrossRef value) {
        stmt.bindLong(1, value.categoryId);
        stmt.bindLong(2, value.userCardId);
      }
    };
    this.__deletionAdapterOfCategoryEntity = new EntityDeletionOrUpdateAdapter<CategoryEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `CategoryEntity` WHERE `categoryId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CategoryEntity value) {
        stmt.bindLong(1, value.categoryId);
      }
    };
    this.__updateAdapterOfCategoryEntity = new EntityDeletionOrUpdateAdapter<CategoryEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `CategoryEntity` SET `categoryId` = ?,`name` = ? WHERE `categoryId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CategoryEntity value) {
        stmt.bindLong(1, value.categoryId);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        stmt.bindLong(3, value.categoryId);
      }
    };
    this.__preparedStmtOfDeleteCategoryUserCardCrossRefById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM CategoryUserCardCrossRef WHERE categoryId = ? AND userCardId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteUserCardFromAllCategoriesById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM CategoryUserCardCrossRef WHERE userCardId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final CategoryEntity categoryEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCategoryEntity.insert(categoryEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertCategoryUserCardCrossRef(
      final CategoryUserCardCrossRef categoryUserCardCrossRef) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCategoryUserCardCrossRef.insert(categoryUserCardCrossRef);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final CategoryEntity categoryEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCategoryEntity.handle(categoryEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final CategoryEntity categoryEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCategoryEntity.handle(categoryEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCategoryUserCardCrossRefById(final long categoryId, final long userCardId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCategoryUserCardCrossRefById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, categoryId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, userCardId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteCategoryUserCardCrossRefById.release(_stmt);
    }
  }

  @Override
  public void deleteUserCardFromAllCategoriesById(final long userCardId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUserCardFromAllCategoriesById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, userCardId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteUserCardFromAllCategoriesById.release(_stmt);
    }
  }

  @Override
  public List<CategoryEntity> getAll() {
    final String _sql = "SELECT * FROM CategoryEntity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final List<CategoryEntity> _result = new ArrayList<CategoryEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CategoryEntity _item;
        _item = new CategoryEntity();
        _item.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _item.name = null;
        } else {
          _item.name = _cursor.getString(_cursorIndexOfName);
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
  public CategoryEntity getById(final long id) {
    final String _sql = "SELECT * FROM CategoryEntity WHERE categoryId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final CategoryEntity _result;
      if(_cursor.moveToFirst()) {
        _result = new CategoryEntity();
        _result.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
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

  @Override
  public List<CategoryAndCards> getCategoriesAndCards() {
    final String _sql = "SELECT * FROM CategoryEntity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
        final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
        final LongSparseArray<ArrayList<UserCardEntity>> _collectionUserCards = new LongSparseArray<ArrayList<UserCardEntity>>();
        while (_cursor.moveToNext()) {
          if (!_cursor.isNull(_cursorIndexOfCategoryId)) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfCategoryId);
            ArrayList<UserCardEntity> _tmpUserCardsCollection = _collectionUserCards.get(_tmpKey);
            if (_tmpUserCardsCollection == null) {
              _tmpUserCardsCollection = new ArrayList<UserCardEntity>();
              _collectionUserCards.put(_tmpKey, _tmpUserCardsCollection);
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipUserCardEntityAsDataBaseUserCardEntity(_collectionUserCards);
        final List<CategoryAndCards> _result = new ArrayList<CategoryAndCards>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final CategoryAndCards _item;
          final CategoryEntity _tmpCategory;
          if (!(_cursor.isNull(_cursorIndexOfCategoryId) && _cursor.isNull(_cursorIndexOfName))) {
            _tmpCategory = new CategoryEntity();
            _tmpCategory.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpCategory.name = null;
            } else {
              _tmpCategory.name = _cursor.getString(_cursorIndexOfName);
            }
          } else {
            _tmpCategory = null;
          }
          ArrayList<UserCardEntity> _tmpUserCardsCollection_1 = null;
          if (!_cursor.isNull(_cursorIndexOfCategoryId)) {
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfCategoryId);
            _tmpUserCardsCollection_1 = _collectionUserCards.get(_tmpKey_1);
          }
          if (_tmpUserCardsCollection_1 == null) {
            _tmpUserCardsCollection_1 = new ArrayList<UserCardEntity>();
          }
          _item = new CategoryAndCards();
          _item.category = _tmpCategory;
          _item.userCards = _tmpUserCardsCollection_1;
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public CategoryAndCards getCategoryAndCards(final long id) {
    final String _sql = "SELECT * FROM CategoryEntity WHERE categoryId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
        final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
        final LongSparseArray<ArrayList<UserCardEntity>> _collectionUserCards = new LongSparseArray<ArrayList<UserCardEntity>>();
        while (_cursor.moveToNext()) {
          if (!_cursor.isNull(_cursorIndexOfCategoryId)) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfCategoryId);
            ArrayList<UserCardEntity> _tmpUserCardsCollection = _collectionUserCards.get(_tmpKey);
            if (_tmpUserCardsCollection == null) {
              _tmpUserCardsCollection = new ArrayList<UserCardEntity>();
              _collectionUserCards.put(_tmpKey, _tmpUserCardsCollection);
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipUserCardEntityAsDataBaseUserCardEntity(_collectionUserCards);
        final CategoryAndCards _result;
        if(_cursor.moveToFirst()) {
          final CategoryEntity _tmpCategory;
          if (!(_cursor.isNull(_cursorIndexOfCategoryId) && _cursor.isNull(_cursorIndexOfName))) {
            _tmpCategory = new CategoryEntity();
            _tmpCategory.categoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpCategory.name = null;
            } else {
              _tmpCategory.name = _cursor.getString(_cursorIndexOfName);
            }
          } else {
            _tmpCategory = null;
          }
          ArrayList<UserCardEntity> _tmpUserCardsCollection_1 = null;
          if (!_cursor.isNull(_cursorIndexOfCategoryId)) {
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfCategoryId);
            _tmpUserCardsCollection_1 = _collectionUserCards.get(_tmpKey_1);
          }
          if (_tmpUserCardsCollection_1 == null) {
            _tmpUserCardsCollection_1 = new ArrayList<UserCardEntity>();
          }
          _result = new CategoryAndCards();
          _result.category = _tmpCategory;
          _result.userCards = _tmpUserCardsCollection_1;
        } else {
          _result = null;
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipUserCardEntityAsDataBaseUserCardEntity(
      final LongSparseArray<ArrayList<UserCardEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<UserCardEntity>> _tmpInnerMap = new LongSparseArray<ArrayList<UserCardEntity>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipUserCardEntityAsDataBaseUserCardEntity(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<UserCardEntity>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipUserCardEntityAsDataBaseUserCardEntity(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `UserCardEntity`.`userCardId` AS `userCardId`,`UserCardEntity`.`text` AS `text`,`UserCardEntity`.`imagePath` AS `imagePath`,_junction.`categoryId` FROM `CategoryUserCardCrossRef` AS _junction INNER JOIN `UserCardEntity` ON (_junction.`userCardId` = `UserCardEntity`.`userCardId`) WHERE _junction.`categoryId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = 3; // _junction.categoryId;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfUserCardId = 0;
      final int _cursorIndexOfText = 1;
      final int _cursorIndexOfImagePath = 2;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          ArrayList<UserCardEntity> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final UserCardEntity _item_1;
            _item_1 = new UserCardEntity();
            _item_1.userCardId = _cursor.getLong(_cursorIndexOfUserCardId);
            if (_cursor.isNull(_cursorIndexOfText)) {
              _item_1.text = null;
            } else {
              _item_1.text = _cursor.getString(_cursorIndexOfText);
            }
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _item_1.imagePath = null;
            } else {
              _item_1.imagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
