package cz.mtr.analyzaprodeju.fragments.search;

import android.app.Application;

import cz.mtr.analyzaprodeju.fragments.barcode.BarcodeViewModel;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.room.ArticlesDatabase.DataRepository;
import cz.mtr.analyzaprodeju.repository.room.ArticlesDatabase.Article;
import cz.mtr.analyzaprodeju.models.datastructures.StoreItem;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class SearchByEan {

    private static final String TAG = SearchByEan.class.getSimpleName();

    private DataRepository mRepository;
    private SharedArticle mArticleDB;
    private SharedArticle mArticleAnalysis;


    public SearchByEan(Application application) {
        mRepository = new DataRepository(application);
    }

    public SharedArticle getArticleDb() {
        return mArticleDB;
    }

    public SharedArticle getArticleAnalysis() {
        return mArticleAnalysis;
    }


    public BarcodeViewModel.Status findArticle(String ean) {
        if (Model.getInstance().getAnalysis().containsKey(ean)) {
            mArticleAnalysis = Model.getInstance().getAnalysis().get(ean);
            return BarcodeViewModel.Status.ANALYSIS;
        } else if (Model.getInstance().getStoreItems().containsKey(ean)) {
            mArticleAnalysis = convertItemToArticle(Model.getInstance().getStoreItems().get(ean));
            return BarcodeViewModel.Status.ANALYSIS;
        } else {
            Article article = mRepository.getArticle(ean);
            if (article != null) {
                mArticleDB = convertArticleToSharedArticle(article);
                return BarcodeViewModel.Status.DATABASE;
            } else {
                return BarcodeViewModel.Status.NOT_FOUND;
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


}
