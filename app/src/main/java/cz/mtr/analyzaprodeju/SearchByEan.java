package cz.mtr.analyzaprodeju;

import android.app.Application;

import cz.mtr.analyzaprodeju.fragments.barcode.BarcodeViewModel;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.DataRepository;
import cz.mtr.analyzaprodeju.repository.room.Article;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class SearchByEan {

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


}
