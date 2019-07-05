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

    public ItemFts(String ean, String name) {
        this.mEan = ean;
        this.mName = name;
    }

    public String getEan() {
        return this.mEan;
    }

    public String getName() {
        return this.mName;
    }


}
