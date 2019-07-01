package cz.mtr.analyzaprodeju.fragments.printer.orders;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import cz.mtr.analyzaprodeju.fragments.printer.other.PrinterItem;
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

}
