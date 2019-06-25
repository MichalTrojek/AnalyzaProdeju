package cz.mtr.analyzaprodeju.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import cz.mtr.analyzaprodeju.room.Article;
import cz.mtr.analyzaprodeju.room.ArticleDao;
import cz.mtr.analyzaprodeju.room.ArticleRoomDatabase;

public class DataRepository {

    private ArticleDao mArticleDao; // is used to get access to database operations
    private LiveData<Article> mArticle;

    public DataRepository(Application app) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getInstance(app);
        mArticleDao = db.articleDao();
    }


    public LiveData<Article> getArticle(String ean) {
        mArticle = mArticleDao.getArticle(ean);
        return mArticle;
    }


}
