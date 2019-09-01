package cz.mtr.analyzaprodeju.services;

import android.os.AsyncTask;
import android.util.Log;

import com.opencsv.CSVReader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

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
                        if (Model.getInstance().getPrefs().getUpdatedTime() != f.getTimestamp().getTimeInMillis()) {
                            Model.getInstance().getPrefs().setUpdatedTime(f.getTimestamp().getTimeInMillis());
                            Model.getInstance().getPrefs().setStoreItems(readStoreStatus(ftp.retrieveFileStream(mFilename)));
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

    private HashMap<String, StoreItem> readStoreStatus(InputStream input) {
        HashMap<String, StoreItem> items = new HashMap<>();

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(input, "Windows-1250"), ';', '\"', 1);
            String[] record;
            while ((record = reader.readNext()) != null) {
                try {
                    items.put(record[0], new StoreItem(record[0], record[3], record[8], record[2], record[4]));
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
