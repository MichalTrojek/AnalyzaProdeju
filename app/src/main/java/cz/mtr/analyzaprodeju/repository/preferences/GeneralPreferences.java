package cz.mtr.analyzaprodeju.repository.preferences;

import android.content.Context;

public class GeneralPreferences {

    private static volatile GeneralPreferences INSTANCE;

    private static final String PREFERENCES_NAME = "cz.mtr.analyzaprodeju.generalpreferences";

    private static final String IP_ADDRESS = "ip";
    private static final String PASSWORD = "password";
    private static final String INDEX_OF_LAST_SELECTED_ITEM = "lastselecteditem";
    private static final String SELECTED_FILENAME = "filename";
    private static final String SELECTED_STORE_NAME = "selectedstorename";
    private static final String LAST_STORE_FILE_NAME = "laststorefilename";
    private static final String TIME = "time";
    private static final String STORE_DATA_TIME = "storedatatime";


    private android.content.SharedPreferences mPrefs;

    private GeneralPreferences(Context context) {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() to get single instance of this class.");
        } else {
            mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void init(Context context) {
        if (INSTANCE == null) {
            synchronized (GeneralPreferences.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GeneralPreferences(context);
                }
            }
        }
    }

    public static synchronized GeneralPreferences getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException(GeneralPreferences.class.getSimpleName() + "is not initilized, call init() method first");
        }
        return INSTANCE;
    }


    public String loadPassword() {
        return mPrefs.getString(PASSWORD, "");
    }


    public void savePassword(String password) {
        mPrefs.edit().putString(PASSWORD, password).commit();
    }

    public String loadIp() {
        return mPrefs.getString(IP_ADDRESS, "0.0.0.0");
    }

    public void saveIp(String ip) {
        mPrefs.edit().putString(IP_ADDRESS, ip).commit();
    }

    public void saveLastSelectedItem(int index) {
        mPrefs.edit().putInt(INDEX_OF_LAST_SELECTED_ITEM, index).commit();
    }

    public int loadIndexOfLastSelectedItem() {
        return mPrefs.getInt(INDEX_OF_LAST_SELECTED_ITEM, 0);
    }



    // stavy prodejen
    public void saveFilename(String name) {
        mPrefs.edit().putString(SELECTED_FILENAME, name).commit();
    }


    // stavy prodejen
    public String loadFilename() {
        return mPrefs.getString(SELECTED_FILENAME, "");
    }


    public void saveSelectedStore(String name) {
        mPrefs.edit().putString(SELECTED_STORE_NAME, name).commit();
    }

    public String loadSelectedStore() {
        return mPrefs.getString(SELECTED_STORE_NAME, "");
    }



    public long loadAnalysisUpdatedTime() {
        return mPrefs.getLong(TIME, -99);
    }

    public void saveAnalysisUpdatedTime(long time) {
        mPrefs.edit().putLong(TIME, time).commit();
    }


    public long loadStoreDataUpdateTime(){
        return mPrefs.getLong(STORE_DATA_TIME, -99);
    }

    public void saveStoreDataTime(long time){
        mPrefs.edit().putLong(STORE_DATA_TIME, time).commit();
    }


}
