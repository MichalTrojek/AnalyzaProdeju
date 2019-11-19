package cz.mtr.analyzaprodeju.services.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import cz.mtr.analyzaprodeju.auth.Authentication;
import cz.mtr.analyzaprodeju.fragments.ftp.analysis.FloresAnalysisReader;
import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;

public class UpdateAnalysisTask extends AsyncTask<String, Integer, Boolean> {

    private static final String TAG = " JobService";
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword;


    private String mPath = "";
    private boolean isEmpty = true, isLoggedIn = true;
    private FloresAnalysisReader mAnalysisReader;
    private long mTimestamp;

    public UpdateAnalysisTask(String name, String password) {
        if (password.equalsIgnoreCase("test123")) {
            mAddress = "214180.w80.wedos.net";
            mUsername = "w214180";
            mPassword = Authentication.getInstance().getDB();
            mPath = "/www/program/prodejny/" + name;
        } else {
            mPassword = password;
            mPath = "/prodejny/" + name;
        }
        Log.d("TestService", "Analysis file name " + name);
        mAnalysisReader = new FloresAnalysisReader();
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(String... voids) {
        boolean success = false;
        getOldRevenue();
        success = downloadAnalysisFromFtp();
        return success;
    }

    private boolean downloadAnalysisFromFtp() {
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
                    if (GeneralPreferences.getInstance().loadAnalysisUpdatedTime() != file.getTimestamp().getTimeInMillis()) {
                        isEmpty = false;
                        mAnalysisReader.readAnalysisFromFtp(
                                ftp.retrieveFileStream(file.getName()), file.getTimestamp().getTimeInMillis());
                        success = true;
                        mTimestamp = file.getTimestamp().getTimeInMillis();
                        Log.d("TestService", "Analysis Update");
                    } else {
                        Log.d("TestService", "Analysis no Update");

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

    private void getOldRevenue() {
        try {
            FTPClient ftp = new FTPClient();
            ftp.connect(mAddress);
            boolean login = ftp.login(mUsername, mPassword);
            if (login) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(mPath);

                if (ftp.listFiles().length > 2) {
                    FTPFile file = ftp.listFiles()[ftp.listFiles().length - 2];
                    if (GeneralPreferences.getInstance().loadAnalysisUpdatedTime() != file.getTimestamp().getTimeInMillis()) {
                        isEmpty = false;
                        mAnalysisReader.getTotalRevenueFromYesterday(
                                ftp.retrieveFileStream(file.getName()));

                        mTimestamp = file.getTimestamp().getTimeInMillis();
                        Log.d("TestService", "OldRevenue Update");
                    } else {
                        Log.d("TestService", "OldRevenue Update");
                    }
                }
            } else {
                isLoggedIn = false;
            }
            ftp.logout();
            ftp.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
