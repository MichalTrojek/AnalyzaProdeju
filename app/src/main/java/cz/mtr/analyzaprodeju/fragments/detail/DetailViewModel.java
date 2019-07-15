package cz.mtr.analyzaprodeju.fragments.detail;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.fragments.scraper.ScrapInfoAsyncTask;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.models.datastructures.DisplayableArticle;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;
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
                a.getSales2DateTo(), a.getSales2Days(), a.getEshopCode());
        mArticleFromAnalysis.setValue(article);
        return mArticleFromAnalysis;
    }

    public void saveArticleAndAmountOrders(DisplayableArticle article, String amount) {
        Model.getInstance().addOrders(getExportSharedArticle(article, amount));
    }

    public void saveArticleAndAmountReturns(DisplayableArticle article, String amount) {
        Model.getInstance().addReturns(getExportSharedArticle(article, amount));
    }

    private ExportSharedArticle getExportSharedArticle(DisplayableArticle article, String amount) {
        ExportSharedArticle exportArticle = new ExportSharedArticle();
        exportArticle.setRanking(article.getRanking());
        exportArticle.setEan(article.getEan());
        exportArticle.setName(article.getName());
        exportArticle.setSales1(article.getSales1());
        exportArticle.setSales1(article.getSales2());
        exportArticle.setRevenue(article.getRevenue());
        exportArticle.setStored(article.getStored());
        exportArticle.setDaysOfSupplies(article.getDaysOfSupplies());
        exportArticle.setLocation(article.getLocation());
        exportArticle.setPrice(article.getPrice());
        exportArticle.setSupplier(article.getSupplier());
        exportArticle.setAuthor(article.getAuthor());
        exportArticle.setDateOfLastSale(article.getDateOfLastSale());
        exportArticle.setDateOfLastDelivery(article.getDateOfLastDelivery());
        exportArticle.setReleaseDate(article.getReleaseDate());
        exportArticle.setCommision(article.getCommision());
        exportArticle.setRankingEshop(article.getRankingEshop());
        exportArticle.setSales1DateSince(article.getSales1DateSince());
        exportArticle.setSales1DateTo(article.getSales1DateTo());
        exportArticle.setSales1Days(article.getSales1Days());
        exportArticle.setSales2DateSince(article.getSales2DateSince());
        exportArticle.setSales2DateTo(article.getSales2DateTo());
        exportArticle.setSales2Days(article.getSales2Days());
        exportArticle.setExportAmount(amount);
        return exportArticle;
    }

    public void navigateToScraper(View view, TextView eanTextView) {
        if (Model.getInstance().getPrefs().getLogin().isEmpty() || Model.getInstance().getPrefs().getPassword().isEmpty()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.knihydobrovsky.cz/vyhledavani?search=" + eanTextView.getText().toString()));
            getApplication().startActivity(browserIntent);
        } else {
            Model.getInstance().setEan(eanTextView.getText().toString());
            ScrapInfoAsyncTask task = new ScrapInfoAsyncTask(view, getApplication().getApplicationContext());
            task.execute(Model.getInstance().getEan());
        }
    }


}
