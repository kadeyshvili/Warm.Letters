package DataBase;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserCardDao _userCardDao;

  private volatile CategoryDao _categoryDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UserCardEntity` (`userCardId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `text` TEXT, `imagePath` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CategoryEntity` (`categoryId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CategoryUserCardCrossRef` (`categoryId` INTEGER NOT NULL, `userCardId` INTEGER NOT NULL, PRIMARY KEY(`categoryId`, `userCardId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1aaf489b7ba2dc9e7b62bde3568cde8e')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `UserCardEntity`");
        _db.execSQL("DROP TABLE IF EXISTS `CategoryEntity`");
        _db.execSQL("DROP TABLE IF EXISTS `CategoryUserCardCrossRef`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsUserCardEntity = new HashMap<String, TableInfo.Column>(3);
        _columnsUserCardEntity.put("userCardId", new TableInfo.Column("userCardId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCardEntity.put("text", new TableInfo.Column("text", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserCardEntity.put("imagePath", new TableInfo.Column("imagePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserCardEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserCardEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserCardEntity = new TableInfo("UserCardEntity", _columnsUserCardEntity, _foreignKeysUserCardEntity, _indicesUserCardEntity);
        final TableInfo _existingUserCardEntity = TableInfo.read(_db, "UserCardEntity");
        if (! _infoUserCardEntity.equals(_existingUserCardEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "UserCardEntity(DataBase.UserCardEntity).\n"
                  + " Expected:\n" + _infoUserCardEntity + "\n"
                  + " Found:\n" + _existingUserCardEntity);
        }
        final HashMap<String, TableInfo.Column> _columnsCategoryEntity = new HashMap<String, TableInfo.Column>(2);
        _columnsCategoryEntity.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategoryEntity.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategoryEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategoryEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategoryEntity = new TableInfo("CategoryEntity", _columnsCategoryEntity, _foreignKeysCategoryEntity, _indicesCategoryEntity);
        final TableInfo _existingCategoryEntity = TableInfo.read(_db, "CategoryEntity");
        if (! _infoCategoryEntity.equals(_existingCategoryEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "CategoryEntity(DataBase.CategoryEntity).\n"
                  + " Expected:\n" + _infoCategoryEntity + "\n"
                  + " Found:\n" + _existingCategoryEntity);
        }
        final HashMap<String, TableInfo.Column> _columnsCategoryUserCardCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsCategoryUserCardCrossRef.put("categoryId", new TableInfo.Column("categoryId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategoryUserCardCrossRef.put("userCardId", new TableInfo.Column("userCardId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategoryUserCardCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategoryUserCardCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategoryUserCardCrossRef = new TableInfo("CategoryUserCardCrossRef", _columnsCategoryUserCardCrossRef, _foreignKeysCategoryUserCardCrossRef, _indicesCategoryUserCardCrossRef);
        final TableInfo _existingCategoryUserCardCrossRef = TableInfo.read(_db, "CategoryUserCardCrossRef");
        if (! _infoCategoryUserCardCrossRef.equals(_existingCategoryUserCardCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "CategoryUserCardCrossRef(DataBase.CategoryUserCardCrossRef).\n"
                  + " Expected:\n" + _infoCategoryUserCardCrossRef + "\n"
                  + " Found:\n" + _existingCategoryUserCardCrossRef);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1aaf489b7ba2dc9e7b62bde3568cde8e", "9123b38959c6ac1eece54a420bd76fcc");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "UserCardEntity","CategoryEntity","CategoryUserCardCrossRef");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `UserCardEntity`");
      _db.execSQL("DELETE FROM `CategoryEntity`");
      _db.execSQL("DELETE FROM `CategoryUserCardCrossRef`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserCardDao.class, UserCardDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public UserCardDao userCardDao() {
    if (_userCardDao != null) {
      return _userCardDao;
    } else {
      synchronized(this) {
        if(_userCardDao == null) {
          _userCardDao = new UserCardDao_Impl(this);
        }
        return _userCardDao;
      }
    }
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }
}
