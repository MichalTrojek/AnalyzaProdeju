package cz.mtr.analyzaprodeju.repository.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface ArticleDao {


    @Query("SELECT * FROM articles WHERE ean= :ean LIMIT 1")
    public Article getArticle(String ean);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT ean, name FROM fts_books_names WHERE fts_books_names MATCH  :term ORDER BY name ASC")
    public List<ItemFts> searchByName(String term);


    @Query("DELETE FROM articles")
    public void nukeTable();

    @Query("DELETE FROM fts_books_names")
    public void nukeFtsTable();


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(Article... articles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Article article);

    @RawQuery
    int vacuumDb(SupportSQLiteQuery supportSQLiteQuery);


    @Query("INSERT INTO fts_books_names (ean, name, normalizedName)SELECT ean, name, normalizedName FROM articles")
    public void insertAllFts();
}