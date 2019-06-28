package cz.mtr.analyzaprodeju.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.room.Article;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class BarcodeViewModel extends AndroidViewModel {

    public enum Status {
        NOT_FOUND, ANALYSIS, DATABASE
    }

    private MutableLiveData<Status> mStatus = new MutableLiveData<>();
    private DataRepository mRepository;
    private Article articleDB;
    private SharedArticle articleAnalysis;


    public BarcodeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    public MutableLiveData<Status> getStatus() {
        return mStatus;
    }

    public Article getArticleDb() {
        return articleDB;
    }

    public SharedArticle getArticleAnalysis() {
        return articleAnalysis;
    }


    public void findArticle(String ean) {
        if (Model.getInstance().getAnalysis().containsKey(ean)) {
            articleAnalysis = Model.getInstance().getAnalysis().get(ean);
            mStatus.setValue(Status.ANALYSIS);

        } else {
            articleDB = mRepository.getArticle(ean);
            if (articleDB != null) {
                mStatus.setValue(Status.DATABASE);
            } else {
                mStatus.setValue(Status.NOT_FOUND);
            }
        }
    }
}