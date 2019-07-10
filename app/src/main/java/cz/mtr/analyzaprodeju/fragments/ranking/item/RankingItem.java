package cz.mtr.analyzaprodeju.fragments.ranking.item;

public class RankingItem {


    private String rank;
    private String name;
    private String ean;
    private String amount;
    private String firstSales;
    private String secondSales;
    private String firstDays;
    private String secondDays;


    public RankingItem(String rank, String name, String ean, String amount, String firstSales, String secondSales, String firstDays, String secondDays) {
        this.rank = rank;
        this.name = name;
        this.ean = ean;
        this.amount = amount;
        this.firstSales = firstSales;
        this.secondSales = secondSales;
        this.firstDays = firstDays;
        this.secondDays = secondDays;
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

    public String getAmount() {
        return amount;
    }

    public String getFirstSales() {
        return firstSales;
    }

    public String getSecondSales() {
        return secondSales;
    }

    public String getFirstDays() {
        return firstDays;
    }

    public String getSecondDays() {
        return secondDays;
    }


}
