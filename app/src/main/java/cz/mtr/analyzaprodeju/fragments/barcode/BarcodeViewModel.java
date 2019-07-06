package cz.mtr.analyzaprodeju.fragments.barcode;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.repository.room.Article;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class BarcodeViewModel extends AndroidViewModel {

    private String mEan;


    public enum Status {
        NOT_FOUND, ANALYSIS, DATABASE
    }

    private MutableLiveData<Status> mStatus = new MutableLiveData<>();
    private DataRepository mRepository;
    private SharedArticle mArticleDB;
    private SharedArticle mArticleAnalysis;


    public BarcodeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }


    public MutableLiveData<Status> getStatus() {
        return mStatus;
    }

    public SharedArticle getArticleDb() {
        return mArticleDB;
    }

    public SharedArticle getArticleAnalysis() {
        return mArticleAnalysis;
    }


    public void saveScannedEan(String displayValue) {
        mEan = displayValue;
        findArticle(mEan);
    }


    public void findArticle(String ean) {
        if (Model.getInstance().getAnalysis().containsKey(ean)) {
            mArticleAnalysis = Model.getInstance().getAnalysis().get(ean);
            mStatus.setValue(Status.ANALYSIS);
        } else {
            Article article = mRepository.getArticle(ean);
            if (article != null) {
                mArticleDB = convertArticleToSharedArticle(article);
                mStatus.setValue(Status.DATABASE);
            } else {
                mStatus.setValue(Status.NOT_FOUND);
            }
        }
    }

    private SharedArticle convertArticleToSharedArticle(Article article) {
        SharedArticle shared = new SharedArticle();
        shared.setEan(article.getEan());
        shared.setName(article.getName());
        shared.setPrice(article.getPrice());
        return shared;
    }
}