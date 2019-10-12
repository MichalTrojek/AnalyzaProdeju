package cz.mtr.analyzaprodeju.repository.room.ArticlesDatabase;

import android.app.Application;
import android.util.Log;

import java.util.List;

import cz.mtr.analyzaprodeju.repository.room.ItemFts;

public class DataRepository {

    public static final String TAG = DataRepository.class.getSimpleName();

    private ArticleDao mArticleDao; // is used to get access to database operations
    private Article mArticle;
    private ArticleRoomDatabase db;

    public DataRepository(Application app) {
        db = ArticleRoomDatabase.getInstance(app);
        mArticleDao = db.articleDao();

    }

    public Article getArticle(String ean) {
        mArticle = mArticleDao.getArticle(ean);
        return mArticle;
    }

    public void deleteAll() {
        mArticleDao.nukeTable();
        mArticleDao.nukeFtsTable();
    }


    public void insertAll(List<Article> items) {
        long startTime = System.currentTimeMillis();
        db.clearAllTables();
        db.runInTransaction(new Task(items));
        mArticleDao.insertAllFts();
        long endTime = System.currentTimeMillis();
        long MethodeDuration = (endTime - startTime) / 1000;
        Log.d(TAG, "TIME " + MethodeDuration);
    }

    class Task implements Runnable {
        Article[] array;

        Task(List<Article> items) {
            array = items.toArray(new Article[items.size()]);
        }

        @Override
        public void run() {
            mArticleDao.insertAll(array);
        }
    }


    public List<ItemFts> searchByName(String name) {
        return mArticleDao.searchByName("^" + name);
    }


}
