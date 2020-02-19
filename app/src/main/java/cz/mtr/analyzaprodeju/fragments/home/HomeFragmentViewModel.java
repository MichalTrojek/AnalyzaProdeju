package cz.mtr.analyzaprodeju.fragments.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.fragments.barcode.BarcodeViewModel;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class HomeFragmentViewModel extends AndroidViewModel {

    private SearchByEan mSearchByEan;
    private MutableLiveData<BarcodeViewModel.Status> mStatus = new MutableLiveData<>();

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        mSearchByEan = new SearchByEan(application);
    }

    public MutableLiveData<BarcodeViewModel.Status> getStatus() {
        return mStatus;
    }


    public void findArticle(String ean) {
        mStatus.setValue(mSearchByEan.findArticle(ean));
    }

    public SharedArticle getArticleDb() {
        return mSearchByEan.getArticleDb();
    }

    public SharedArticle getArticleAnalysis() {
        return mSearchByEan.getArticleAnalysis();
    }


}
