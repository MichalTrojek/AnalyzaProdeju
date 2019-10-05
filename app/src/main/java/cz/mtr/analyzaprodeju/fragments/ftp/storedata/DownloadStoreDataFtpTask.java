package cz.mtr.analyzaprodeju.fragments.ftp.storedata;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import cz.mtr.analyzaprodeju.auth.Authentication;
import cz.mtr.analyzaprodeju.models.Model;

public class DownloadStoreDataFtpTask extends AsyncTask<String, Integer, Boolean> {

    private static final String TAG = DownloadStoreDataFtpTask.class.getSimpleName();
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword;


    private String mPath = "";
    private boolean isLoggedIn = true;
    private String mFilename;

    public DownloadStoreDataFtpTask(String name, String password) {
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
                    Log.d(TAG, mFilename);
                    if (f.getName().toLowerCase().equals(mFilename.toLowerCase())) {
                        Model.getInstance().setStoreItems(new StoreDataReader().getStoreStatus(ftp.retrieveFileStream(mFilename)));
                        Log.d(TAG, "Velikost storeitem " + Model.getInstance().getStoreItems().size());
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
        return null;
    }
 


    @Override
    protected void onPostExecute(Boolean success) {
        if (!isLoggedIn) {
            success = false;
        }
        super.onPostExecute(success);
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
