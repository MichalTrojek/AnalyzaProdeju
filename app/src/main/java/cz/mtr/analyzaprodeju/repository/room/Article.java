package cz.mtr.analyzaprodeju.repository.room;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articles")
public class Article {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ean")
    private String mEan;
    @NonNull
    @ColumnInfo(name = "name")
    private String mName;
    @NonNull
    @ColumnInfo(name = "normalizedName")
    private String mNormalizedName;
    @NonNull
    @ColumnInfo(name = "price")
    private String mPrice;


    public Article(String ean, String name, String normalizedName, String price) {
        this.mEan = ean;
        this.mName = name;
        this.mNormalizedName = normalizedName;
        this.mPrice = price;
    }

    public String getEan() {
        return this.mEan;
    }

    public String getName() {
        return this.mName;
    }

    public String getNormalizedName() {
        return this.mNormalizedName;
    }

    public String getPrice() {
        return this.mPrice;
    }


}
