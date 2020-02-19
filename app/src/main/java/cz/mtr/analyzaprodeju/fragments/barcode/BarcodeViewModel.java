package cz.mtr.analyzaprodeju.fragments.barcode;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.fragments.home.SearchByEan;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class BarcodeViewModel extends AndroidViewModel {

    public enum Status {
        NOT_FOUND, ANALYSIS, DATABASE
    }

    private MutableLiveData<Status> mStatus = new MutableLiveData<>();


    private SearchByEan mSearchByEan;

    public BarcodeViewModel(@NonNull Application application) {
        super(application);
        mSearchByEan = new SearchByEan(application);
    }


    public MutableLiveData<Status> getStatus() {
        return mStatus;
    }

    public SharedArticle getArticleDb() {
        return mSearchByEan.getArticleDb();
    }

    public SharedArticle getArticleAnalysis() {
        return mSearchByEan.getArticleAnalysis();
    }


    public void saveScannedEan(String displayValue) {
        String mEan = displayValue;
        findArticle(mEan);
    }


    public void findArticle(String ean) {
        mStatus.setValue(mSearchByEan.findArticle(ean));
    }


}