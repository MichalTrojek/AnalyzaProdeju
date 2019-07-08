package cz.mtr.analyzaprodeju.fragments.display.other;

public class DisplayItem {

    private String mName, mAmount, mRevenue;

    public DisplayItem(String name, String amount) {
        this.mName = name;
        this.mAmount = amount;
    }


    public String getAmount() {
        return mAmount;
    }

    public String getName() {
        return mName;
    }


}
