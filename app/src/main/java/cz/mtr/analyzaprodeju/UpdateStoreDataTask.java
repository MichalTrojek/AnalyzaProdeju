package cz.mtr.analyzaprodeju;

import android.os.AsyncTask;
import android.util.Log;

import com.opencsv.CSVReader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.mtr.analyzaprodeju.fragments.ftp.FloresAnalysisReader;
import cz.mtr.analyzaprodeju.models.Model;

public class UpdateStoreDataTask extends AsyncTask<String, Integer, Boolean> {

    private static final String TAG = UpdateStoreDataTask.class.getSimpleName();
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword;


    private String mPath = "";
    private boolean isEmpty = true, isLoggedIn = true;
    private FloresAnalysisReader mAnalysisReader;
    private String mFilename;

    public UpdateStoreDataTask(String name, String password) {
        Log.d(TAG, "UPDATE NAME " + name);
        mFilename = name;
        mPath = "/stavy/";
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

                for (FTPFile f : ftp.listFiles()) {
                    if (f.getName().toLowerCase().equals(mFilename.toLowerCase())) {
                        Log.d(TAG, f.getName().toLowerCase().equals(mFilename.toLowerCase()) + " " + f.getName().toLowerCase() + " name " + mFilename.toLowerCase());
                        if (Model.getInstance().getPrefs().getUpdatedTime() != f.getTimestamp().getTimeInMillis()) {
                            Model.getInstance().getPrefs().setUpdatedTime(f.getTimestamp().getTimeInMillis());
                            Model.getInstance().getPrefs().setStoreItems(readAnalysFromFtp(ftp.retrieveFileStream(mFilename)));
                        }
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

    private ArrayList<StoreItem> readAnalysFromFtp(InputStream input) {
        ArrayList<StoreItem> items = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(input, "Windows-1250"), ';', '\"', 1);
            String[] record;

            Log.d(TAG, "Started");
            while ((record = reader.readNext()) != null) {
                try {
                    items.add(new StoreItem(record[0], record[3], record[8], record[2], record[4]));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }


            if (items.size() != 0) {
                Log.d(TAG, items.size() + " Velikost");
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
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
