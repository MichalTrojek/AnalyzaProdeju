package cz.mtr.analyzaprodeju.repository.preferences;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.models.datastructures.StoreItem;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;

public class StoreItemsPreferences {

    private final String PREFERENCES_NAME = "cz.mtr.analyzaprodeju.storeitempreferences";
    private static volatile StoreItemsPreferences INSTANCE;
    private android.content.SharedPreferences mPrefs;


    private static final String ORDERS = "orders";
    private static final String RETURNS = "returns";
    private static final String STORE_ITEMS = "storeitems";

    private StoreItemsPreferences(Context context) {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() to get single instance of this class.");
        } else {
            mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void init(Context context) {
        if (INSTANCE == null) {
            synchronized (StoreItemsPreferences.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StoreItemsPreferences(context);
                }
            }
        }
    }

    public static synchronized StoreItemsPreferences getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException(StoreItemsPreferences.class.getSimpleName() + "is not initilized, call init() method first");
        }
        return INSTANCE;
    }


    public void saveOrders(ArrayList<ExportSharedArticle> orders) {
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        mPrefs.edit().putString(ORDERS, json).commit();
    }

    public ArrayList<ExportSharedArticle> loadOrders() {
        Gson gson = new Gson();
        String json = mPrefs.getString(ORDERS, null);
        Type type = new TypeToken<ArrayList<ExportSharedArticle>>() {
        }.getType();
        ArrayList<ExportSharedArticle> orders;
        orders = gson.fromJson(json, type);
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }


    public void saveReturns(ArrayList<ExportSharedArticle> returns) {
        Gson gson = new Gson();
        String json = gson.toJson(returns);
        mPrefs.edit().putString(RETURNS, json).commit();
    }

    public ArrayList<ExportSharedArticle> loadReturns() {
        Gson gson = new Gson();
        String json = mPrefs.getString(RETURNS, null);
        Type type = new TypeToken<ArrayList<ExportSharedArticle>>() {
        }.getType();
        ArrayList<ExportSharedArticle> returns;
        returns = gson.fromJson(json, type);
        if (returns == null) {
            returns = new ArrayList<>();
        }
        return returns;
    }

    public void saveStoreItems(HashMap<String, StoreItem> items) {
        Gson gson = new Gson();
        String json = gson.toJson(items);
        mPrefs.edit().putString(STORE_ITEMS, json).commit();
    }

    public HashMap<String, StoreItem> loadStoreItems() {
        Gson gson = new Gson();
        String json = mPrefs.getString(STORE_ITEMS, null);
        Type type = new TypeToken<HashMap<String, StoreItem>>() {
        }.getType();
        HashMap<String, StoreItem> storeItems = gson.fromJson(json, type);
        if (storeItems == null) {
            storeItems = new HashMap<>();
        }
        return storeItems;
    }


}
