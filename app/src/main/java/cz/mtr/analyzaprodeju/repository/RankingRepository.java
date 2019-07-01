package cz.mtr.analyzaprodeju.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.fragments.ranking.other.rankingItem;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class RankingRepository {

    private String info = "";

    public RankingRepository() {

    }


    public List<rankingItem> getStoreListOfItems() {
        List<rankingItem> rankingItems = new ArrayList<>();
        List<SharedArticle> articles = new ArrayList<>(Model.getInstance().getAnalysis().values());
        articles = sortByStoreRankings(articles);
        for (SharedArticle a : articles) {
            rankingItems.add(new rankingItem(a.getRanking(), a.getName()));
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
        info = String.format("Od: %s\nDo: %s\nCelkem %s dní", article.getSales1DateSince(), article.getSales1DateTo(), article.getSales1Days());
        return info;
    }


}
