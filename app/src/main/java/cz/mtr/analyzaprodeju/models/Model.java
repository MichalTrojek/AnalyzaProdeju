package cz.mtr.analyzaprodeju.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

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
        if (returns.contains(a)) {
            returns.remove(a);
            returns.add(a);
        } else {
            returns.add(a);
        }
    }


    public void addOrders(ExportSharedArticle a) {
        if (orders.contains(a)) {
            orders.remove(a);
            orders.add(a);
        } else {
            orders.add(a);
        }

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
        if (analysis.size() > 0) {
            saveAnalysis();
        }
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
        analysis.clear();
    }


}
