package cz.mtr.analyzaprodeju.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.datastructures.DisplayableArticle;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class DetailViewModel extends AndroidViewModel {

    public static final String TAG = DetailViewModel.class.getSimpleName();


    private MutableLiveData<DisplayableArticle> mArticleFromAnalysis = new MutableLiveData<>();


    public DetailViewModel(@NonNull Application application) {
        super(application);


    }


    public MutableLiveData<DisplayableArticle> getArticle(SharedArticle a) {
        DisplayableArticle article = new DisplayableArticle(a.getRanking(), a.getEan(), a.getName(), a.getSales1(), a.getSales2()
                , a.getRevenue(), a.getStored(), a.getDaysOfSupplies(), a.getLocation(), a.getPrice(), a.getSupplier(),
                a.getAuthor(), a.getDateOfLastSale(), a.getDateOfLastDelivery(), a.getReleaseDate(), a.getCommision(), a.getRankingEshop(),
                a.getSales1DateSince(), a.getSales1DateTo(), a.getSales1Days(), a.getSales2DateSince(),
                a.getSales2DateTo(), a.getSales2Days());
        mArticleFromAnalysis.setValue(article);
        return mArticleFromAnalysis;
//        mArticleFromAnalysis.postValue(article);
    }


}
