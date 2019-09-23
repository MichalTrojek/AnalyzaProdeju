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


    public void saveFilename(String name) {
        mPrefs.edit().putString(SELECTED_FILENAME, name).commit();
    }

    public String loadFilename() {
        return mPrefs.getString(SELECTED_FILENAME, "");
    }


    public void saveSelectedStore(String name) {
        mPrefs.edit().putString(SELECTED_STORE_NAME, name).commit();
    }

    public String loadSelectedStore() {
        return mPrefs.getString(SELECTED_STORE_NAME, "");
    }


    public void saveLastStoreNameFile(String name) {
        mPrefs.edit().putString(LAST_STORE_FILE_NAME, name).commit();
    }


    public String loadLastStoreNameFile() {
        return mPrefs.getString(LAST_STORE_FILE_NAME, "nenalezlo");
    }


    public long loadUpdatedTime() {
        return mPrefs.getLong(TIME, -99);
    }

    public void saveUpdatedTime(long time) {
        mPrefs.edit().putLong(TIME, time).commit();
    }


}
