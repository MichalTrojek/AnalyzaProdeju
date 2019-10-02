package cz.mtr.analyzaprodeju.services.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

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
        Log.d(TAG, "Constructor");
        mFilename = name;
        mPath = "/stavy/";
        mPassword = password;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "On Preexcecute ");
    }

    @Override
    protected Boolean doInBackground(String... voids) {
        boolean success = false;
        Log.d(TAG, "Downloading started");
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
//                    if(f.getName().equalsIgnoreCase(GeneralPreferences.getInstance().loadLastStoreNameFile()) && f.getTimestamp() != GeneralPreferences.getInstance().loadLastTimestanp()){
//
//                    }
                    Log.d(TAG, f.getTimestamp() + "");
                    if (f.getName().toLowerCase().equals(mFilename.toLowerCase())) {
                        Log.d(TAG, "Inside second if");
                        GeneralPreferences.getInstance().saveLastStoreNameFile(f.getName().toLowerCase());
                        Model.getInstance().setStoreItems(new StoreDataReader().getStoreStatus(ftp.retrieveFileStream(mFilename)));
                        break;
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
        Log.d(TAG, "On post execute");
        if (!isLoggedIn) {
            Log.d(TAG, "failed to login");
            success = false;
        }

        Log.d(TAG, "succes");
        super.onPostExecute(success);
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
