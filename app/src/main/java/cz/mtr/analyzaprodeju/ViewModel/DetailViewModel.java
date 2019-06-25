package cz.mtr.analyzaprodeju.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.room.Article;

public class DetailViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<Article> mArticle;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    public LiveData<Article> getArticle(String ean) {
        mArticle = mRepository.getArticle(ean);
        return mArticle;
    }


}
