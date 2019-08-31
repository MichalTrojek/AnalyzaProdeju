package cz.mtr.analyzaprodeju.models;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.StoreItem;
import cz.mtr.analyzaprodeju.shared.ExportSharedArticle;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class SharedPreferences {

    private final String PREFERENCES_NAME = "MainPreferences";
    private final String IP_ADDRESS = "ip";
    private final String DATABASE_VERSION = "currentDatabaseVersion";
    private final String ANALYSIS = "analysis";
    private final String ORDERS = "orders";
    private final String RETURNS = "returns";
    private final String PASSWORD = "password";
    private final String LOGIN = "login";

    private android.content.SharedPreferences mPrefs;
    private android.content.SharedPreferences.Editor mEditor;


    public SharedPreferences(Context context) {
        mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public String getIp() {
        return mPrefs.getString(IP_ADDRESS, "0.0.0.0");
    }

    public void setIp(String ip) {
        mEditor = mPrefs.edit();
        mEditor.putString(IP_ADDRESS, ip);
        mEditor.apply();
    }

    public String getPassword() {
        return mPrefs.getString(PASSWORD, "");
    }


    public void setPassword(String password) {
        mEditor = mPrefs.edit();
        mEditor.putString(PASSWORD, password);
        mEditor.apply();
    }


    public String getLogin() {
        return mPrefs.getString(LOGIN, "");
    }

    public void setLogin(String login) {
        mEditor = mPrefs.edit();
        mEditor.putString(LOGIN, login);
        mEditor.apply();
    }

    public int getCurrentDatabaseVersion() {
        return mPrefs.getInt(DATABASE_VERSION, -99);
    }


    public void setCurrentDatabaseVersion(int currentDatabaseVersion) {
        mEditor = mPrefs.edit();
        mEditor.putInt(DATABASE_VERSION, currentDatabaseVersion);
        mEditor.apply();
    }


    public void setAnalysis(HashMap<String, SharedArticle> analysis) {
        mEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(analysis);
        mEditor.putString(ANALYSIS, json);
        mEditor.apply();
    }


    public HashMap<String, SharedArticle> getAnalysis() {
        Gson gson = new Gson();
        String json = mPrefs.getString(ANALYSIS, null);
        Type type = new TypeToken<HashMap<String, SharedArticle>>() {
        }.getType();
        HashMap<String, SharedArticle> analysis;
        analysis = gson.fromJson(json, type);
        if (analysis == null) {
            analysis = new HashMap<>();
        }
        return analysis;
    }

    public void setOrders(ArrayList<ExportSharedArticle> orders) {
        mEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        mEditor.putString(ORDERS, json);
        mEditor.apply();
    }

    public void setReturns(ArrayList<ExportSharedArticle> returns) {
        mEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(returns);
        mEditor.putString(RETURNS, json);
        mEditor.apply();
    }

    public ArrayList<ExportSharedArticle> getOrders() {
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

    public ArrayList<ExportSharedArticle> getReturns() {
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

    public void setStoreItems(ArrayList<StoreItem> items) {
        mEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        mEditor.putString("storeItems", json);
        mEditor.apply();
    }

    public ArrayList<StoreItem> getStoreItems() {
        Gson gson = new Gson();
        String json = mPrefs.getString("storeItems", null);
        Type type = new TypeToken<ArrayList<StoreItem>>() {
        }.getType();
        ArrayList<StoreItem> storeItems = gson.fromJson(json, type);
        if (storeItems == null) {
            storeItems = new ArrayList<>();
        }
        return storeItems;
    }

    public void setLastSelectedItem(int index) {
        mEditor = mPrefs.edit();
        mEditor.putInt("index", index);
        mEditor.apply();
    }

    public int getLastSelectedItem() {
        return mPrefs.getInt("index", 0);
    }

    public String getSelectedStore() {
        return mPrefs.getString("selectedStore", "");
    }

    public void setSelectedStore(String name) {
        mEditor = mPrefs.edit();
        mEditor.putString("selectedStore", name);
        mEditor.apply();
    }

    public long getUpdatedTime() {
        return mPrefs.getInt("time", -99);
    }

    public void setUpdatedTime(long currentDbVersion) {
        mEditor = mPrefs.edit();
        mEditor.clear();
        mEditor.putLong("time", currentDbVersion);
        mEditor.apply();
    }


}
