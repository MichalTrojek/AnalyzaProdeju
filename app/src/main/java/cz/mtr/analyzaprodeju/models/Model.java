package cz.mtr.analyzaprodeju.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.mtr.analyzaprodeju.fragments.scraper.stores.WebItem;
import cz.mtr.analyzaprodeju.fragments.scraper.suppliers.WebItemSuppliers;
import cz.mtr.analyzaprodeju.repository.preferences.AnalysisPreferences;
import cz.mtr.analyzaprodeju.repository.preferences.StoreItemsPreferences;
import cz.mtr.analyzaprodeju.repository.room.ArticlesDatabase.Article;
import cz.mtr.analyzaprodeju.models.datastructures.StoreItem;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class Model {

    public static final String TAG = Model.class.getSimpleName();

    private static volatile Model INSTANCE;
    private HashMap<String, SharedArticle> mAnalysis = new HashMap<>();
    private HashMap<String, StoreItem> mStoreItems;


    private ArrayList<ExportSharedArticle> orders = new ArrayList<>();
    private ArrayList<ExportSharedArticle> returns = new ArrayList<>();
    private String mImageLink = "";
    private String largeImageLink = "";
    private String ean;
    private List<WebItem> items = new ArrayList<>();
    private List<WebItemSuppliers> suppliersItems = new ArrayList<>();



    private String mTitleName = "";


    public Model() {

    }


    public static Model getInstance() {
        if (INSTANCE == null) {
            synchronized (Model.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Model();
                }
            }
        }
        return INSTANCE;
    }


    public void addReturns(ExportSharedArticle a) {
        if (!"".equals(a.getExportAmount())) {
            if (returns.contains(a)) {
                returns.remove(a);
                returns.add(a);
            } else {
                returns.add(a);
            }
        }
    }


    public List<WebItem> getItems() {
        return this.items;
    }

    public void setItems(List<WebItem> items) {
        this.items = items;
    }

    public List<WebItemSuppliers> getSuppliersItems() {
        return this.suppliersItems;
    }

    public void setSuppliersItems(List<WebItemSuppliers> items) {
        this.suppliersItems = items;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getEan() {
        return ean;
    }

    public void addOrders(ExportSharedArticle a) {
        if (!"".equals(a.getExportAmount())) {
            if (orders.contains(a)) {
                orders.remove(a);
                orders.add(a);
            } else {
                orders.add(a);
            }
        }
    }

    public ArrayList<Article> allDbItems = new ArrayList<>();

    public ArrayList<Article> getAllDbItems() {
        return allDbItems;
    }

    public void setAllDbItems(ArrayList<Article> items) {
        allDbItems = items;
    }

    public ArrayList<ExportSharedArticle> getOrders() {
        return orders;
    }

    public ArrayList<ExportSharedArticle> getReturns() {
        return returns;
    }


    public void setAnalysis(HashMap<String, SharedArticle> mAnalysis) {
        this.mAnalysis = mAnalysis;
        if (mAnalysis != null) {
            if (mAnalysis.size() > 0) {
                AnalysisPreferences.getInstance().setAnalysis(mAnalysis);
            }
        }
    }



    public HashMap<String, SharedArticle> getAnalysis() {
        return this.mAnalysis;
    }


    public HashMap<String, StoreItem> getStoreItems() {
        return mStoreItems;
    }

    public void setStoreItems(HashMap<String, StoreItem> storeItems) {
        mStoreItems = storeItems;
        StoreItemsPreferences.getInstance().saveStoreItems(storeItems);
    }
    public void saveOrdersAndReturns() {
        StoreItemsPreferences.getInstance().saveOrders(orders);
        StoreItemsPreferences.getInstance().saveReturns(returns);
    }

    public void loadOrdersAndReturns() {
        orders = StoreItemsPreferences.getInstance().loadOrders();
        returns = StoreItemsPreferences.getInstance().loadReturns();
    }

    public void setImageLink(String imageLink) {
        mImageLink = imageLink;
    }

    public void setLargeImageLink(String imageLink) {
        largeImageLink = imageLink;
    }

    public String getLargeImageLink() {
        return "https://knihydobrovsky.cz/" + largeImageLink;
    }


    public String getImageLink() {
        return "https://knihydobrovsky.cz" + mImageLink;
    }

    public String getTitleName() {
        return mTitleName;
    }

    public void setTitleName(String mTitleName) {
        this.mTitleName = mTitleName;
    }


    public void loadAnalysis() {
        mAnalysis = AnalysisPreferences.getInstance().getAnalysis();
    }

    public void loadStoreItems(){
        mStoreItems = StoreItemsPreferences.getInstance().loadStoreItems();
    }

    public void clearAnalysis() {
        if (mAnalysis != null) {
            mAnalysis.clear();
        }
    }



}
