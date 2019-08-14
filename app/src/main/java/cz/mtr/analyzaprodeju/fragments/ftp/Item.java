package cz.mtr.analyzaprodeju.fragments.ftp;


public class Item {

    private String ean;
    private String name;
    private String price;
    private String normalizedName;
    private String lowerCaseName;

    public Item(String ean, String name, String price, String normalizedName, String lowerCaseName) {
        this.setEan(ean);
        this.setName(name);
        this.setPrice(price);
        this.setNormalizedName(normalizedName);
        this.lowerCaseName = lowerCaseName;
    }

    public Item(String ean, String name, String price, String normalizedName) {
        this.setEan(ean);
        this.setName(name);
        this.setPrice(price);
        this.setNormalizedName(normalizedName);

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

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLowerCaseName() {
        return lowerCaseName;
    }

    public void setLowerCaseName(String lowerCaseName) {
        this.lowerCaseName = lowerCaseName;
    }

}
