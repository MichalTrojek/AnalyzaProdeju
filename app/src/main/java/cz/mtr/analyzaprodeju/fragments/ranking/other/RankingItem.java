package cz.mtr.analyzaprodeju.fragments.ranking.other;

public class RankingItem {


    private String rank;
    private String name;
    private String ean;


    public RankingItem(String rank, String name, String ean) {
        this.rank = rank;
        this.name = name;
        this.ean = ean;
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


}
