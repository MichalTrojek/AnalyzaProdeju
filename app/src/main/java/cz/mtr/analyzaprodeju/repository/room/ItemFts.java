package cz.mtr.analyzaprodeju.repository.room;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;


@Fts4
@Entity(tableName = "fts_books_names")
public class ItemFts {

    @ColumnInfo(name = "ean")
    private String mEan;
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "normalizedName")
    private String mNormalizedName;

    public ItemFts(String ean, String name, String normalizedName) {
        this.mEan = ean;
        this.mName = name;
        this.mNormalizedName = normalizedName;
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


}
