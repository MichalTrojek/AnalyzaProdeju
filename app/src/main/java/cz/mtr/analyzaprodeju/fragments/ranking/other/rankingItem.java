package cz.mtr.analyzaprodeju.fragments.ranking.other;

public class rankingItem {


    private String rank;
    private String name;
    private String amount;

    public rankingItem(String rank, String name) {
        this.rank = rank;
        this.name = name;
    }

    public rankingItem(String rank, String name, String amount) {
        this(rank, name);
        amount = amount;
    }


    public String getAmount(){
        return this.amount;
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
