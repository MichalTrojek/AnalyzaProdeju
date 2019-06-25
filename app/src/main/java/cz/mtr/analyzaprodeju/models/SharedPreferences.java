package cz.mtr.analyzaprodeju.models;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.shared.ArticleRow;
import cz.mtr.analyzaprodeju.shared.ExportArticle;

public class SharedPreferences {

    private final String PREFERENCES_NAME = "MainPreferences";
    private final String IP_ADDRESS = "ip";
    private final String DATABASE_VERSION = "currentDatabaseVersion";
    private final String ANALYSIS = "analysis";
    private final String ORDERS = "orders";
    private final String RETURNS = "returns";


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

    public int getCurrentDatabaseVersion() {
        return mPrefs.getInt(DATABASE_VERSION, -99);
    }

    public void setCurrentDatabaseVersion(int currentDatabaseVersion) {
        mEditor = mPrefs.edit();
        mEditor.putInt(DATABASE_VERSION, currentDatabaseVersion);
        mEditor.apply();
    }


    public void setAnalysis(HashMap<String, ArticleRow> analysis) {
        mEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(analysis);
        mEditor.putString(ANALYSIS, json);
        mEditor.apply();
    }


    public HashMap<String, ArticleRow> getAnalysis() {
        Gson gson = new Gson();
        String json = mPrefs.getString(ANALYSIS, null);
        Type type = new TypeToken<HashMap<String, ArticleRow>>() {
        }.getType();
        HashMap<String, ArticleRow> analysis;
        analysis = gson.fromJson(json, type);
        if (analysis == null) {
            analysis = new HashMap<>();
        }
        return analysis;
    }

    public void setOrders(ArrayList<ExportArticle> orders) {
        mEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        mEditor.putString(ORDERS, json);
        mEditor.apply();
    }

    public void setReturns(ArrayList<ExportArticle> returns) {
        mEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(returns);
        mEditor.putString(RETURNS, json);
        mEditor.apply();
    }

    public ArrayList<ExportArticle> getOrders() {
        Gson gson = new Gson();
        String json = mPrefs.getString(ORDERS, null);
        Type type = new TypeToken<ArrayList<ExportArticle>>() {
        }.getType();
        ArrayList<ExportArticle> orders;
        orders = gson.fromJson(json, type);
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }

    public ArrayList<ExportArticle> getReturns() {
        Gson gson = new Gson();
        String json = mPrefs.getString(RETURNS, null);
        Type type = new TypeToken<ArrayList<ExportArticle>>() {
        }.getType();
        ArrayList<ExportArticle> returns;
        returns = gson.fromJson(json, type);
        if (returns == null) {
            returns = new ArrayList<>();
        }
        return returns;
    }


}