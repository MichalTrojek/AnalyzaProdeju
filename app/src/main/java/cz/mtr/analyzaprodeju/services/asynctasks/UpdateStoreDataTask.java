package cz.mtr.analyzaprodeju.services.asynctasks;

import android.os.AsyncTask;

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
        mFilename = name;
        mPath = "/stavy/";
        mPassword = password;
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
                    if (f.getName().toLowerCase().equals(GeneralPreferences.getInstance().loadLastStoreNameFile())) {

                    }
                    if (f.getName().toLowerCase().equals(mFilename.toLowerCase())) {
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
