package cz.mtr.analyzaprodeju.fragments.display.orders;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import cz.mtr.analyzaprodeju.fragments.display.other.PrinterItem;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;

public class OrdersViewModel extends ViewModel {
    public static final String TAG = OrdersViewModel.class.getSimpleName();


    public ArrayList<PrinterItem> getOrders() {
        ArrayList<PrinterItem> items = new ArrayList<>();
        for (ExportSharedArticle a : Model.getInstance().getOrders()) {
            items.add(new PrinterItem(a.getName(), a.getExportAmount()));
        }
        return items;
    }


    public void changeAmount(int position, String amount) {
        Model.getInstance().getOrders().get(position).setExportAmount(amount);
        Model.getInstance().saveOrdersAndReturns();
    }

    public void delete(int position) {
        Model.getInstance().getOrders().remove(position);
        Model.getInstance().saveOrdersAndReturns();
    }


}