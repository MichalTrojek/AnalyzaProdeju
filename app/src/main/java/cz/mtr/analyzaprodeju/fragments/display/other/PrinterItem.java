package cz.mtr.analyzaprodeju.fragments.display.other;

public class PrinterItem {

    private String name, amount;

    public PrinterItem(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }


    public String getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
}
