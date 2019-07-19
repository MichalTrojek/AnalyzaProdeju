package cz.mtr.analyzaprodeju.repository.room;


import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ArticleDao {


    @Query("SELECT * FROM articles WHERE ean= :ean LIMIT 1")
    public Article getArticle(String ean);



    @Transaction
    @Query("SELECT ean, name FROM fts_books_names WHERE fts_books_names MATCH  :term ORDER BY name ASC")
    public List<ItemFts> searchByName(String term);


}