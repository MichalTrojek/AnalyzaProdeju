package cz.mtr.analyzaprodeju.repository.room.linkDatabase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cz.mtr.analyzaprodeju.repository.room.ArticlesDatabase.ArticleRoomDatabase;

@Database(entities = {LinkEntity.class}, version = 1,exportSchema = false)
public abstract class LinksDatabase extends RoomDatabase {
    public abstract LinkDao linkDao();

    private static volatile LinksDatabase INSTANCE;


    public static LinksDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LinksDatabase.class, "LinksDatabase.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
