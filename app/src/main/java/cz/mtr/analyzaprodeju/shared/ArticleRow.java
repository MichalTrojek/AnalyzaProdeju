package cz.mtr.analyzaprodeju.shared;

import java.io.Serializable;

public class ArticleRow implements Serializable {

    private static final long serialVersionUID = -2160864851277312099L;

    private String rank = "", firstCode = "", ean = "", name = "", sales = "", sales2 = "", revenue = "",
            storedAmount = "", supply = "", locations = "", price = "", dph = "", supplier = "", author = "",
            dateOflastSale = "", dateOfLastDelivery = "", realeaseDate = "", deliveredAs = "", eshopRank = "", analysisDate = "";

    public ArticleRow() {

    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sell1) {
        this.sales = sell1;
    }

    public String getSales2() {
        return sales2;
    }

    public void setSales2(String sell2) {
        this.sales2 = sell2;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getStoredAmount() {
        return storedAmount;
    }

    public void setStoredAmount(String storedAmount) {
        this.storedAmount = storedAmount;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDph() {
        return dph;
    }

    public void setDph(String dph) {
        this.dph = dph;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateOfLastSale() {
        return dateOflastSale;
    }

    public void setDateOfLastSale(String dateOflastSale) {
        this.dateOflastSale = dateOflastSale;
    }

    public String getDateOfLastDelivery() {
        return dateOfLastDelivery;
    }

    public void setDateOfLastDelivery(String dateOfLastDelivery) {
        this.dateOfLastDelivery = dateOfLastDelivery;
    }

    public String getRealeaseDate() {
        return realeaseDate;
    }

    public void setRealeaseDate(String realeaseDate) {
        this.realeaseDate = realeaseDate;
    }

    public String getDeliveredAs() {
        return deliveredAs;
    }

    public void setDeliveredAs(String deliveredAs) {
        this.deliveredAs = deliveredAs;
    }

    public void setEshopRank(String eshopRank) {
        this.eshopRank = eshopRank;

    }

    public String getAnalysisDate() {
        return this.analysisDate;
    }

    public void setAnalysisDate(String date) {
        this.analysisDate = date;
    }


    public String getEshopRank() {
        return eshopRank;
    }

}
