package cz.mtr.analyzaprodeju.room;


import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface ArticleDao {


    @Query("SELECT * FROM articles WHERE ean= :ean LIMIT 1")
    public Article getArticle(String ean);


}