package cz.mtr.analyzaprodeju.fragments.printer.returns;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import cz.mtr.analyzaprodeju.fragments.printer.other.PrinterItem;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;

public class ReturnsViewModel extends ViewModel {
    public static final String TAG = ReturnsViewModel.class.getSimpleName();


    public ArrayList<PrinterItem> getReturns() {
        ArrayList<PrinterItem> items = new ArrayList<>();
        for (ExportSharedArticle a : Model.getInstance().getReturns()) {
            items.add(new PrinterItem(a.getName(), a.getExportAmount()));
        }
        return items;
    }

}
