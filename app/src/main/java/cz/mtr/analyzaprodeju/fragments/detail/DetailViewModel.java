package cz.mtr.analyzaprodeju.fragments.detail;

import android.app.Application;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.models.datastructures.DisplayableArticle;
import cz.mtr.analyzaprodeju.repository.room.linkDatabase.LinkRepository;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class DetailViewModel extends AndroidViewModel {

    public static final String TAG = DetailViewModel.class.getSimpleName();


    private MutableLiveData<DisplayableArticle> mArticleFromAnalysis = new MutableLiveData<>();
    private LinkRepository mRepository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new LinkRepository(application);
    }

    public void loadImage(String ean, ImageView imageView, ProgressBar progressBar) {
        String imageLink = mRepository.getLink(ean);
        if (imageLink != null) {
            Model.getInstance().setImageLink(imageLink);
            Model.getInstance().setLargeImageLink(imageLink);
            ImageDisplay display = new ImageDisplay(Model.getInstance().getImageLink(), imageView, progressBar);
            display.show();
        } else {
            ImageScrapTask task = new ImageScrapTask(imageView, progressBar, mRepository);
            task.execute(ean);
        }

    }

    public MutableLiveData<DisplayableArticle> getArticle(SharedArticle a) {
        DisplayableArticle article = new DisplayableArticle(a.getRanking(), a.getEan(), a.getName(), a.getSales1(), a.getSales2()
                , a.getRevenue(), a.getStored(), a.getDaysOfSupplies(), a.getLocation(), a.getPrice(), a.getSupplier(),
                a.getAuthor(), a.getDateOfLastSale(), a.getDateOfLastDelivery(), a.getReleaseDate(), a.getCommision(), a.getRankingEshop(),
                a.getSales1DateSince(), a.getSales1DateTo(), a.getSales1Days(), a.getSales2DateSince(),
                a.getSales2DateTo(), a.getSales2Days(), a.getEshopCode(), change(a.getDontOrder()));
        mArticleFromAnalysis.setValue(article);
        return mArticleFromAnalysis;
    }

    private String change(String input) {
        if ("n".equals(input.toLowerCase()) || input.isEmpty()) {
            return "";
        } else {
            return " (Doprodej)";
        }
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


}
