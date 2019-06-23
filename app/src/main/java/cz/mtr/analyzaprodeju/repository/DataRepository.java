package cz.mtr.analyzaprodeju.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import cz.mtr.analyzaprodeju.room.ArticleDao;
import cz.mtr.analyzaprodeju.room.ArticleRoomDatabase;

public class DataRepository {

    private ArticleDao mArticleDao;
    private LiveData<String> mName;


    public DataRepository(Application app) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getDatabase(app);
        mArticleDao = db.articleDao();
        mName = mArticleDao.selectNameByEan("1");
    }

    public LiveData<String> getName() {
        return this.mName;
    }




}
