package cz.mtr.analyzaprodeju.services.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import cz.mtr.analyzaprodeju.auth.Authentication;
import cz.mtr.analyzaprodeju.fragments.ftp.storedata.StoreDataReader;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;

public class UpdateStoreDataTask extends AsyncTask<String, Integer, Boolean> {

    private static final String TAG = UpdateStoreDataTask.class.getSimpleName();
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword;


    private String mPath = "";
    private boolean isLoggedIn = true;
    private String mFilename;

    public UpdateStoreDataTask(String name, String password) {
        Log.d("TestService", "StoreDaa file name " +  name);
        if (password.equalsIgnoreCase("test123")) {
            mAddress = "214180.w80.wedos.net";
            mUsername = "w214180";
            mPassword = Authentication.getInstance().getDB();
            mPath = "/www/program/stavy/";
        } else {
            mPassword = password;
            mPath = "/stavy/";
        }


        mFilename = name;

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(String... voids) {
        boolean success = false;

        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.connect(mAddress);
            boolean login = ftp.login(mUsername, mPassword);
            if (login) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(mPath);
                for (FTPFile f : ftp.listFiles()) {

                    if (f.getName().toLowerCase().equals(mFilename.toLowerCase()) && GeneralPreferences.getInstance().loadStoreDataUpdateTime() != f.getTimestamp().getTimeInMillis()) {
                        Log.d("TestService", "Downloading store data");
                        Model.getInstance().setStoreItems(new StoreDataReader().getStoreStatus(ftp.retrieveFileStream(mFilename), f.getTimestamp().getTimeInMillis()));
                        break;
                    } else {
                        Log.d("TestService", "No Store Update1");
                    }
                }
                success = true;
            } else {
                isLoggedIn = false;
            }
            ftp.logout();
            ftp.disconnect();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (!isLoggedIn) {
            Log.d("TestService", "failed to log in");
            success = false;
        }
        Log.d("TestService", "store data success " + success);
        super.onPostExecute(success);
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
