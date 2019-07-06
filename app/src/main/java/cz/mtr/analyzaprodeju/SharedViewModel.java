package cz.mtr.analyzaprodeju;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.repository.room.Article;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class SharedViewModel extends AndroidViewModel {

    public enum Status {
        NOT_FOUND, ANALYSIS, DATABASE
    }

    private MutableLiveData<SharedViewModel.Status> mStatus = new MutableLiveData<>();
    private DataRepository mRepository;
    private SharedArticle mArticleDB;
    private SharedArticle mArticleAnalysis;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    public MutableLiveData<SharedViewModel.Status> getStatus() {
        return mStatus;
    }

    public SharedArticle getArticleDb() {
        return mArticleDB;
    }

    public SharedArticle getArticleAnalysis() {
        return mArticleAnalysis;
    }


    public void findArticle(String ean) {
        if (Model.getInstance().getAnalysis().containsKey(ean)) {
            mArticleAnalysis = Model.getInstance().getAnalysis().get(ean);
            mStatus.setValue(SharedViewModel.Status.ANALYSIS);
        } else {
            Article article = mRepository.getArticle(ean);
            if (article != null) {
                mArticleDB = new SharedArticle("", article.getEan(), article.getName(), "", "", "", "",
                        "", "", article.getPrice(), "", "", "",
                        "", "", "", "",
                        "", "", "", "", "",
                        "");
                mStatus.setValue(SharedViewModel.Status.DATABASE);
            } else {
                mStatus.setValue(SharedViewModel.Status.NOT_FOUND);
            }
        }
    }

}
