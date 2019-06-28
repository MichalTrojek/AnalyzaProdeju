package cz.mtr.analyzaprodeju.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class NotFoundViewModel extends ViewModel {

    public static final String TAG = NotFoundViewModel.class.getSimpleName();
    private MutableLiveData<SharedArticle> mArticleDb = new MutableLiveData<>();

    public MutableLiveData<SharedArticle> getArticleDb(SharedArticle article) {
        mArticleDb.setValue(article);
        return mArticleDb;
    }

}
