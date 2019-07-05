package cz.mtr.analyzaprodeju.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Article.class, ItemFts.class}, version = 1, exportSchema = false)
public abstract class ArticleRoomDatabase extends RoomDatabase {
    private static final String TAG = ArticleRoomDatabase.class.getSimpleName();


    public abstract ArticleDao articleDao();

    private static volatile ArticleRoomDatabase INSTANCE;


    public static ArticleRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ArticleRoomDatabase.class, "BooksDatabase.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }


}
