package cz.mtr.analyzaprodeju.repository.room.linkDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LinkDao {

    @Query("SELECT link FROM LinkEntity WHERE ean=:ean")
    String getLink(String ean);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLink(LinkEntity linkEntity);

}
