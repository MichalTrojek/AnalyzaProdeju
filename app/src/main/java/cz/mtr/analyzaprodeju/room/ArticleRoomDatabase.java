package cz.mtr.analyzaprodeju.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = Article.class, version = 1, exportSchema = false)
public abstract class ArticleRoomDatabase extends RoomDatabase {
    private static final String TAG = ArticleRoomDatabase.class.getSimpleName();


    public abstract ArticleDao articleDao();

    private static volatile ArticleRoomDatabase INSTANCE;


    public static ArticleRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ArticleRoomDatabase.class, "BooksDatabase.db").build();
                }
            }
        }
        return INSTANCE;
    }


//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            new PopulateDbAsync(INSTANCE).execute();
//        }
//    };
//
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final ArticleDao mDao;
//
//        PopulateDbAsync(ArticleRoomDatabase db) {
//            mDao = db.articleDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
////            mDao.deleteAll();
////            Word word = new Word("Hello");
////            mDao.insert(word);
////            word = new Word("World");
////            mDao.insert(word);
//            String msg = mDao.selectNameByEan("1");
//
//
//            Log.i(TAG, msg);
//            return null;
//        }
//    }


}


