package cz.mtr.analyzaprodeju.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.mtr.analyzaprodeju.fragments.ranking.item.RankingItem;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class RankingRepository {

    private static final String TAG = RankingRepository.class.getSimpleName();

    private String info = "";
    private String dailyRevenue = "";
    private MutableLiveData<List<RankingItem>> allRankingItem = new MutableLiveData<>();

    public RankingRepository() {

    }


    public LiveData<List<RankingItem>> getAllRankingItems() {
        allRankingItem.setValue(getRankingItems());
        return allRankingItem;
    }

    private List<RankingItem> getRankingItems() {
        List<RankingItem> rankingItems = new ArrayList<>();
        List<SharedArticle> articlesFromAnalysis = new ArrayList<>(Model.getInstance().getAnalysis().values());
        articlesFromAnalysis = sortByStoreRankings(articlesFromAnalysis);
        for (SharedArticle a : articlesFromAnalysis) {
            rankingItems.add(new RankingItem(a.getRanking(), a.getName(), a.getEan(), a.getStored(), a.getSales1(), a.getSales2(), a.getSales1Days(), a.getSales2Days()));
        }
        return rankingItems;
    }

    private List<SharedArticle> sortByStoreRankings(List<SharedArticle> sharedArticles) {
        List<SharedArticle> articles = new ArrayList<>(sharedArticles);
        Collections.sort(articles, new Comparator<SharedArticle>() {
            @Override
            public int compare(SharedArticle article, SharedArticle article2) {
                int one = Integer.parseInt(article.getRanking());
                int two = Integer.parseInt(article2.getRanking());
                if (one > two) {
                    return -1;
                } else if (one < two) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        Collections.reverse(articles);
        return articles;
    }

    public String getInfo() {
        ArrayList<SharedArticle> list = new ArrayList<>(Model.getInstance().getAnalysis().values());
        SharedArticle article = (SharedArticle) list.get(0);
        info = String.format("Od: %s  Do: %s\nCelkem %s Kč\bVčera: %s Kč", article.getSales1DateSince(), article.getSales1DateTo(), getTotalRevenue(), dailyRevenue);
        return info;
    }

    private String getTotalRevenue() {
        HashMap<String, SharedArticle> analysis = Model.getInstance().getAnalysis();
        int total = 0;
        for (SharedArticle a : analysis.values()) {
            String revenue = a.getRevenue();
            revenue = revenue.replace(",", ".");
            Double asDouble = Double.parseDouble(revenue);
            int asInt = asDouble.intValue();
            if(asInt > 0) {
                total += asInt;
            }
        }
        calculateDailyRevenue(total);
        return createFormattedString(total);
    }

    private void calculateDailyRevenue(int total){
        dailyRevenue = createFormattedString(total - GeneralPreferences.getInstance().getTotalRevenueFromYesterday());
    }

    private String createFormattedString(int total) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setDecimalSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(total);
    }


}
