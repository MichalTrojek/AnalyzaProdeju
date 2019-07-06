package cz.mtr.analyzaprodeju.shared;

import java.io.Serializable;

public class SharedArticle implements Serializable {

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


    public SharedArticle() {

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

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSales1(String sales1) {
        this.sales1 = sales1;
    }

    public void setSales2(String sales2) {
        this.sales2 = sales2;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public void setStored(String stored) {
        this.stored = stored;
    }

    public void setDaysOfSupplies(String daysOfSupplies) {
        this.daysOfSupplies = daysOfSupplies;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDateOfLastSale(String dateOfLastSale) {
        this.dateOfLastSale = dateOfLastSale;
    }

    public void setDateOfLastDelivery(String dateOfLastDelivery) {
        this.dateOfLastDelivery = dateOfLastDelivery;
    }
}