package cz.mtr.analyzaprodeju.models;

import android.content.Context;

import java.util.HashMap;

import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class Model {

    private static volatile Model INSTANCE;
    private HashMap<String, SharedArticle> analysis = new HashMap<>();
    private SharedPreferences mPrefs;
    private Context mContext;


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
