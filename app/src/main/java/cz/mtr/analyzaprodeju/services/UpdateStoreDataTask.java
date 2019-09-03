package cz.mtr.analyzaprodeju.services;

import android.os.AsyncTask;
import android.util.Log;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

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
                        Model.getInstance().setStoreItems(readStoreStatus(ftp.retrieveFileStream(mFilename)));
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
        Log.d(TAG, "readStoreStatus started");
        try {

            CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(input, "Windows-1250")).withSkipLines(1).withCSVParser(parser).build();

            String[] record;
            Log.d(TAG, mFilename);
            while ((record = reader.readNext()) != null) {

                try {
                    items.put(record[0], new StoreItem(record[0], record[3], record[8], record[2], record[4]));
                } catch (Exception e) {
                    Log.d(TAG, mFilename);
                    Log.d(TAG, record[0]);
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
            if (items.size() != 0) {
                Log.d(TAG, "Store Items " + items.size() + " Velikost");

            }
            reader.close();

        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
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
