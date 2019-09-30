package cz.mtr.analyzaprodeju.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import cz.mtr.analyzaprodeju.fragments.ftp.FloresAnalysisReader;
import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;

public class UpdateAnalysisTask extends AsyncTask<String, Integer, Boolean> {

    private static final String TAG = UpdateAnalysisTask.class.getSimpleName();
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword;


    private String mPath = "";
    private boolean isEmpty = true, isLoggedIn = true;
    private FloresAnalysisReader mAnalysisReader;


    public UpdateAnalysisTask(String name, String password) {
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

        try {
            FTPClient ftp = new FTPClient();
            ftp.connect(mAddress);
            boolean login = ftp.login(mUsername, mPassword);
            if (login) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(mPath);

                if (ftp.listFiles().length > 2) {
                    FTPFile file = ftp.listFiles()[ftp.listFiles().length - 1];
                    if (GeneralPreferences.getInstance().loadUpdatedTime()!= file.getTimestamp().getTimeInMillis()) {
                        GeneralPreferences.getInstance().saveUpdatedTime(file.getTimestamp().getTimeInMillis());
                        isEmpty = false;
                        mAnalysisReader.readAnalysisFromFtp(
                                ftp.retrieveFileStream(file.getName()));
                        success = true;
                        Log.d(TAG, "Update");
                    } else {
                        Log.d(TAG, "neni update");

                    }
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
        return success;
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
