package cz.mtr.analyzaprodeju.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.mtr.analyzaprodeju.fragments.scraper.stores.WebItem;
import cz.mtr.analyzaprodeju.fragments.scraper.suppliers.WebItemSuppliers;
import cz.mtr.analyzaprodeju.repository.room.Article;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class Model {

    public static final String TAG = Model.class.getSimpleName();

    private static volatile Model INSTANCE;
    private HashMap<String, SharedArticle> analysis = new HashMap<>();
    private SharedPreferences mPrefs;
    private Context mContext;
    private ArrayList<ExportSharedArticle> orders = new ArrayList<>();
    private ArrayList<ExportSharedArticle> returns = new ArrayList<>();
    private String mImageLink = "";
    private String largeImageLink = "";


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

    public void createPrefs(Context context) {
        this.mContext = context;
        mPrefs = new SharedPreferences(mContext);

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

    String ean;
    List<WebItem> items = new ArrayList<>();
    List<WebItemSuppliers> suppliersItems = new ArrayList<>();

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


    public SharedPreferences getPrefs() {
        return mPrefs;
    }

    public void setAnalysis(HashMap<String, SharedArticle> analysis) {
        this.analysis = analysis;
        if (analysis != null) {
            if (analysis.size() > 0) {
                saveAnalysis();
            }
        }
    }

    public void saveOrdersAndReturns() {
        mPrefs.setOrders(orders);
        mPrefs.setReturns(returns);
    }

    public void loadOrdersAndReturns() {
        orders = mPrefs.getOrders();
        returns = mPrefs.getReturns();
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
        return "https://knihydobrovsky.cz/" + mImageLink;
    }

    public String getTitleName() {
        return mTitleName;
    }

    public void setTitleName(String mTitleName) {
        this.mTitleName = mTitleName;
    }


    public HashMap<String, SharedArticle> getAnalysis() {
        return this.analysis;
    }

    public void saveAnalysis() {
        mPrefs.setAnalysis(analysis);
    }

    public void loadAnalysis() {
        analysis = mPrefs.getAnalysis();
    }

    public void clearAnalysis() {
        if (analysis != null) {
            analysis.clear();
        }
    }


}
