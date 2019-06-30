package cz.mtr.analyzaprodeju.models.datastructures;

import java.io.Serializable;

public class DisplayableArticle implements Serializable {

    private static final long serialVersionUID = -2160864851277312099L;

    private String ranking = "";
    private String ean = "";
    private String name = "";
    private String sales1 = "";
    private String sales2 = "";
    private String revenue = "";
    private String stored = "";
    private String daysOfSupplies = "";
    private String location = "";
    private String price = "";
    private String supplier = "";
    private String author = "";
    private String dateOfLastSale = "";
    private String dateOfLastDelivery = "";
    private String releaseDate = "";
    private String commision = "";
    private String rankingEshop = "";
    private String sales1DateSince = "";
    private String sales1DateTo = "";
    private String sales1Days = "";
    private String sales2DateSince = "";
    private String sales2DateTo = "";
    private String sales2Days = "";

    public DisplayableArticle(String ranking, String ean, String name, String sales1, String sales2, String revenue, String stored,
                              String daysOfSupplies, String location, String price, String supplier, String author, String dateOfLastSale,
                              String dateOfLastDelivery, String releaseDate, String commision, String rankingEshop,
                              String sales1DateSince, String sales1DateTo, String sales1Days, String sales2DateSince, String sales2DateTo,
                              String sales2Days) {
        super();
        this.ranking = ranking;
        this.ean = ean;
        this.name = name;
        this.sales1 = sales1;
        this.sales2 = sales2;
        this.revenue = revenue;
        this.stored = stored;
        this.daysOfSupplies = daysOfSupplies;
        this.location = location;
        this.price = price;
        this.supplier = supplier;
        this.author = author;
        this.dateOfLastSale = dateOfLastSale;
        this.dateOfLastDelivery = dateOfLastDelivery;
        this.releaseDate = releaseDate;
        this.commision = commision;
        this.rankingEshop = rankingEshop;
        this.sales1DateSince = sales1DateSince;
        this.sales1DateTo = sales1DateTo;
        this.sales1Days = sales1Days;
        this.sales2DateSince = sales2DateSince;
        this.sales2DateTo = sales2DateTo;
        this.sales2Days = sales2Days;
    }

    public DisplayableArticle(String ean, String name, String price) {
        this.ean = ean;
        this.name = name;
        this.price = price;
    }


    public DisplayableArticle() {

    }

    public String getRanking() {
        return ranking;
    }

    public String getEan() {
        return ean;
    }

    public String getName() {
        return name;
    }

    public String getSales1() {
        return sales1;
    }

    public String getSales2() {
        return sales2;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getStored() {
        return stored;
    }

    public String getDaysOfSupplies() {
        return daysOfSupplies;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getAuthor() {
        return author;
    }

    public String getDateOfLastSale() {
        return dateOfLastSale;
    }

    public String getDateOfLastDelivery() {
        return dateOfLastDelivery;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getCommision() {
        return commision;
    }

    public String getRankingEshop() {
        return rankingEshop;
    }

    public String getSales1DateSince() {
        return sales1DateSince;
    }

    public String getSales1DateTo() {
        return sales1DateTo;
    }

    public String getSales1Days() {
        return sales1Days;
    }

    public String getSales2DateSince() {
        return sales2DateSince;
    }

    public String getSales2DateTo() {
        return sales2DateTo;
    }

    public String getSales2Days() {
        return sales2Days;
    }

}