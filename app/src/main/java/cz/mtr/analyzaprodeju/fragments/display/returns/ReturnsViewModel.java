package cz.mtr.analyzaprodeju.fragments.display.returns;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import cz.mtr.analyzaprodeju.fragments.display.other.DisplayItem;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;

public class ReturnsViewModel extends ViewModel {
    public static final String TAG = ReturnsViewModel.class.getSimpleName();


    public ArrayList<DisplayItem> getReturns() {
        ArrayList<DisplayItem> items = new ArrayList<>();
        for (ExportSharedArticle a : Model.getInstance().getReturns()) {
            items.add(new DisplayItem(a.getName(), a.getExportAmount()));
        }
        return items;
    }


    public void changeAmount(int position, String amount) {
        Model.getInstance().getReturns().get(position).setExportAmount(amount);
        Model.getInstance().saveOrdersAndReturns();
    }

    public void delete(int position) {
        Model.getInstance().getReturns().remove(position);
        Model.getInstance().saveOrdersAndReturns();
    }
}
