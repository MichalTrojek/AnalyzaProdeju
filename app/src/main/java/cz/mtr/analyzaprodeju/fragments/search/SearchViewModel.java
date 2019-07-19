package cz.mtr.analyzaprodeju.fragments.search;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

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
        Log.d(TAG, ean);
        if (Model.getInstance().getAnalysis().containsKey(ean)) {
            mArticleAnalysis = Model.getInstance().getAnalysis().get(ean);
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
