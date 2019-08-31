package cz.mtr.analyzaprodeju;

public class StoreItem {
    private String ean = "", location = "", amount = "", name = "", price = "";

    public StoreItem() {
    }

    public StoreItem(String ean, String location, String amount, String name, String price) {
        this.ean = ean;
        this.location = location;
        this.amount = amount;
        this.name = name;
        this.price = price;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEan() {
        return ean;
    }

    public String getLocation() {
        return location;
    }

    public String getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

}
