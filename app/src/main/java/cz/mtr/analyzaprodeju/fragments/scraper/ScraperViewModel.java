package cz.mtr.analyzaprodeju.fragments.scraper;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cz.mtr.analyzaprodeju.fragments.scraper.stores.WebItem;
import cz.mtr.analyzaprodeju.fragments.scraper.suppliers.WebItemSuppliers;
import cz.mtr.analyzaprodeju.models.Model;


public class ScraperViewModel extends ViewModel {

    List<WebItem> items = new ArrayList<>();


    public ScraperViewModel() {

    }


    public List<WebItem> getItems() {
        return Model.getInstance().getItems();
    }

    public List<WebItemSuppliers> getSuppliersItems() {
        return Model.getInstance().getSuppliersItems();
    }


}
