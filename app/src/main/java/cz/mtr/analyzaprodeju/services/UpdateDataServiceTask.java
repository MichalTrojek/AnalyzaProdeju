package cz.mtr.analyzaprodeju.services;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import cz.mtr.analyzaprodeju.fragments.ftp.FloresAnalysisReader;

public class UpdateDataServiceTask extends AsyncTask<String, Integer, Boolean> {

    private static final String TAG = UpdateDataServiceTask.class.getSimpleName();
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword;


    private String mPath = "";
    private boolean isEmpty = true, isLoggedIn = true;
    private FloresAnalysisReader mAnalysisReader;


    public UpdateDataServiceTask(String name, String password) {
        Log.d(TAG, "UPDATE NAME " + name);
        mPath = "/prodejny/" + name;
        mPassword = password;
        mAnalysisReader = new FloresAnalysisReader();
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
                if (ftp.listFiles().length > 2) {
                    isEmpty = false;
                    mAnalysisReader.readAnalysisFromFtp(
                            ftp.retrieveFileStream(
                                    ftp.listFiles()[ftp.listFiles().length - 1].getName()));
                    success = true;
                }
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
        } else {
            if (isEmpty) {
                success = false;//Složka na FTP neobsahuje žádná data
            }
        }
        super.onPostExecute(success);
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
