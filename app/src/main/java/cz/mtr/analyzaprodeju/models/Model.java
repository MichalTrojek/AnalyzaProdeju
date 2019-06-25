package cz.mtr.analyzaprodeju.models;

import android.content.Context;

public class Model {

    private static volatile Model INSTANCE;

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


}
