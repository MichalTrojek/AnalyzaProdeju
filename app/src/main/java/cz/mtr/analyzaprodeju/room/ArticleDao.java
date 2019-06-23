package cz.mtr.analyzaprodeju.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;


@Dao
public interface ArticleDao {


    @Query("SELECT name FROM articles WHERE ean= :ean")
    public LiveData<String> selectNameByEan(String ean);

}
