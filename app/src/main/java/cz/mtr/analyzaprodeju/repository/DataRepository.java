package cz.mtr.analyzaprodeju.repository;

import android.app.Application;

import cz.mtr.analyzaprodeju.repository.room.Article;
import cz.mtr.analyzaprodeju.repository.room.ArticleDao;
import cz.mtr.analyzaprodeju.repository.room.ArticleRoomDatabase;

public class DataRepository {

    public static final String TAG = DataRepository.class.getSimpleName();

    private ArticleDao mArticleDao; // is used to get access to database operations
    private Article mArticle;

    public DataRepository(Application app) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getInstance(app);
        mArticleDao = db.articleDao();
    }

    public Article getArticle(String ean) {
        mArticle = mArticleDao.getArticle(ean);
        return mArticle;
    }


}
