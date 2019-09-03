package cz.mtr.analyzaprodeju.repository.preferences;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class AnalysisPreferences {

    private final String PREFERENCES_NAME = "cz.mtr.analyzaprodeju.analysispreferences";
    private final String ANALYSIS = "analysis";
    private static volatile AnalysisPreferences INSTANCE;
    private android.content.SharedPreferences mPrefs;

    private AnalysisPreferences(Context context) {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() to get single instance of this class.");
        } else {
            mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void init(Context context) {
        if (INSTANCE == null) {
            synchronized (AnalysisPreferences.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AnalysisPreferences(context);

                }
            }
        }
    }

    public static synchronized AnalysisPreferences getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException(AnalysisPreferences.class.getSimpleName() + "is not initilized, call init() method first");
        }
        return INSTANCE;
    }


    public void setAnalysis(HashMap<String, SharedArticle> analysis) {
        Gson gson = new Gson();
        String json = gson.toJson(analysis);
        mPrefs.edit().putString(ANALYSIS, json).commit();
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


}
