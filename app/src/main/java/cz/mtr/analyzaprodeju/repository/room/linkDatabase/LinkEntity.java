package cz.mtr.analyzaprodeju.repository.room.linkDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class LinkEntity {


    @PrimaryKey
    @ColumnInfo(name = "ean")
    @NotNull
    public String ean;

    @ColumnInfo(name = "link")
    public String link;


    public LinkEntity(@NotNull String ean, String link) {
        this.ean = ean;
        this.link = link;
    }



}
