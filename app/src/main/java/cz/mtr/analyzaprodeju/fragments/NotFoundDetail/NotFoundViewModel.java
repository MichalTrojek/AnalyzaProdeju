package cz.mtr.analyzaprodeju.fragments.NotFoundDetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class NotFoundViewModel extends ViewModel {

    public static final String TAG = NotFoundViewModel.class.getSimpleName();
    private MutableLiveData<SharedArticle> mArticleDb = new MutableLiveData<>();

    public MutableLiveData<SharedArticle> getArticleDb(SharedArticle article) {
        mArticleDb.setValue(article);
        return mArticleDb;
    }

    public void saveArticleAndAmountOrders(SharedArticle article, String amount) {
        ExportSharedArticle exportArticle = new ExportSharedArticle();
        exportArticle.setEan(article.getEan());
        exportArticle.setName(article.getName());
        exportArticle.setSupplier(article.getSupplier());
        exportArticle.setExportAmount(amount);
        Model.getInstance().addOrders(exportArticle);
    }

    public void saveArticleAndAmountReturns(SharedArticle article, String amount) {
        ExportSharedArticle exportArticle = new ExportSharedArticle();
        exportArticle.setEan(article.getEan());
        exportArticle.setName(article.getName());
        exportArticle.setSupplier(article.getSupplier());
        exportArticle.setExportAmount(amount);
        Model.getInstance().addReturns(exportArticle);
    }

}
