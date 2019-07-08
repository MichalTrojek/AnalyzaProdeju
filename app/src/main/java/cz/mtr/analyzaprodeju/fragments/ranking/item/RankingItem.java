package cz.mtr.analyzaprodeju.fragments.ranking.item;

public class RankingItem {


    private String rank;
    private String name;
    private String ean;
    private String revenue;


    public RankingItem(String rank, String name, String ean, String revenue) {
        this.rank = rank;
        this.name = name;
        this.ean = ean;
        this.revenue = revenue;
    }


    public String getEan() {
        return this.ean;
    }

    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getRevenue() {
        return revenue;
    }


}
