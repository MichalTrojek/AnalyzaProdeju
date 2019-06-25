package cz.mtr.analyzaprodeju.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;


@Dao
public interface ArticleDao {


    @Query("SELECT * FROM articles WHERE ean= :ean")
    public LiveData<Article> getArticle(String ean);


}
