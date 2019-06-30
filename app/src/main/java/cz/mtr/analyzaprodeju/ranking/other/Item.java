package cz.mtr.analyzaprodeju.ranking.other;

public class Item {


    private String rank;
    private String name;

    public Item(String rank, String name) {
        this.rank = rank;
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
