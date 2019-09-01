package cz.mtr.analyzaprodeju.fragments.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.services.StoreItem;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.repository.room.Article;
import cz.mtr.analyzaprodeju.repository.room.ItemFts;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class SearchViewModel extends AndroidViewModel {

    public enum SearchStatus {
        ANALYSIS, DATABASE
    }

    private static final String TAG = SearchViewModel.class.getSimpleName();


    private MutableLiveData<SearchStatus> mStatus = new MutableLiveData<>();
    private DataRepository mRepository;
    private SharedArticle mArticleDB;
    private SharedArticle mArticleAnalysis;

    private List<ItemFts> mList = new ArrayList<>();


    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }


    public List<ItemFts> searchByName(String name) {
        mList = mRepository.searchByName(name);

        return mList;
    }


    public void findArticle(String ean) {
        if (Model.getInstance().getAnalysis().containsKey(ean)) {
            mArticleAnalysis = Model.getInstance().getAnalysis().get(ean);
            mStatus.setValue(SearchStatus.ANALYSIS);
        } else if (Model.getInstance().getStoreItems().containsKey(ean)) {
            mArticleAnalysis = convertItemToArticle(Model.getInstance().getStoreItems().get(ean));
            mStatus.setValue(SearchStatus.ANALYSIS);
        } else {
            Article article = mRepository.getArticle(ean);
            if (article != null) {
                mArticleDB = convertArticleToSharedArticle(article);
                mStatus.setValue(SearchStatus.DATABASE);
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


    private SharedArticle convertItemToArticle(StoreItem item) {
        SharedArticle shared = new SharedArticle();
        shared.setEan(item.getEan());
        shared.setLocation(item.getLocation());
        shared.setPrice(item.getPrice());
        shared.setStored(item.getAmount());
        shared.setName(item.getName());
        shared.setSales1("0");
        shared.setSales2("0");
        shared.setRevenue("0");
        shared.setDaysOfSupplies("0");
        shared.setRanking("x");
        shared.setRankingEshop("x");
        shared.setCommision("neni");
        return shared;
    }
    public MutableLiveData<SearchStatus> getStatus() {
        return mStatus;
    }

    public SharedArticle getArticleDb() {
        return mArticleDB;
    }

    public SharedArticle getArticleAnalysis() {
        return mArticleAnalysis;
    }

}
