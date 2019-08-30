package cz.mtr.analyzaprodeju;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.opencsv.CSVReader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class DownloadDataTask extends AsyncTask<String, Integer, Void> {

    private static final String TAG = DownloadDataTask.class.getSimpleName();
    String address = "81.95.110.138";
    String username = "knihydobro9";
    String password = "";
    String filename = "2019-08-25.csv";




    private Context context;

    private DialogLoadingFragment progressDialog;


    public DownloadDataTask(Context c) {
        this.context = c;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new DialogLoadingFragment();
        progressDialog.setCancelable(false);
        progressDialog.show(((MainActivity) context).getSupportFragmentManager(), "FragmentChangeDialog");

    }

    @Override
    protected Void doInBackground(String... voids) {
        try {
//            if (downloadAndSaveFile(filename)) {
                readAnalysisFile();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private Boolean downloadAndSaveFile(String filename)
            throws IOException {
        FTPClient ftp = null;
        //StandardCharsets.UTF_8
        try {
            ftp = new FTPClient();
            ftp.connect(address);
            Log.d(TAG, "Connected. Reply: " + ftp.getReplyString());

            ftp.login(username, password);
            Log.d(TAG, "Logged in");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            Log.d(TAG, "Downloading");
            ftp.enterLocalPassiveMode();

            OutputStream outputStream = null;
            boolean success = false;
            try {
                    outputStream = context.openFileOutput("analyza.csv", Context.MODE_PRIVATE);

                success = ftp.retrieveFile("/prodejny/VAN/" + filename, outputStream);


                Log.d(TAG, success + "");
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            Log.d(TAG, "Succes " + success);
            return success;
        } finally {
            if (ftp != null) {
                ftp.logout();
                ftp.disconnect();
            }
        }
    }


    private HashMap<String, SharedArticle> readAnalysisFile() {
        HashMap<String, SharedArticle> map = new HashMap<>(500000, 1);
        String path = context.getFilesDir().getAbsolutePath() + "/analyza.csv";
        Log.d(TAG, path);
        try {
            InputStream is = new FileInputStream(new File(path));
            @SuppressWarnings("deprecation")
            CSVReader reader = new CSVReader(new InputStreamReader(is, "Windows-1250"), ';', '\"', 1);
            String[] record;
            int rankingCounter = 0;
            while ((record = reader.readNext()) != null) {
                try {
                    rankingCounter++;
                    if (record[3].contains("Dárková knižní") || record[3].contains("Klubový slevový poukaz")
                            || record[3].contains("VOUCHER")) {
                        rankingCounter--;
                        continue;
                    }
                    SharedArticle shared = new SharedArticle();
                    shared.setEshopCode(record[1]);
                    shared.setRanking(rankingCounter + "");
                    shared.setEan(record[2]);
                    shared.setName(record[3]);
                    shared.setSales1(record[4]);
                    shared.setSales2(record[5]);
                    shared.setRevenue(record[6]);
                    shared.setStored(record[7]);
                    shared.setDaysOfSupplies(record[8]);
                    shared.setLocation(record[9]);
                    shared.setPrice(record[10]);
                    shared.setSupplier(record[12]);
                    shared.setAuthor(record[13]);
                    shared.setDontOrder(record[14]);
                    shared.setDateOfLastSale(record[15]);
                    shared.setDateOfLastDelivery(record[16]);
                    shared.setReleaseDate(record[17]);
                    shared.setCommision(record[18]);
                    shared.setRankingEshop(record[19]);
                    shared.setSales1DateSince(record[20]);
                    shared.setSales1DateTo(record[21]);
                    shared.setSales1Days(record[22]);
                    shared.setSales2DateSince(record[23]);
                    shared.setSales2DateTo(record[24]);
                    shared.setSales2Days(record[25]);

                    map.put(record[2], shared);
                    shared = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }


            }
            Log.d(TAG, "" + map.size() + " polozek v analyze");

            if (map.size() != 0) {
//                Model.getInstance().setAnalysis(map);
            }
            reader.close();

            for (SharedArticle s : map.values()) {
                Log.d(TAG, s.getEan());
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }

        return map;
    }


    private void deleteDir(File file) {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                deleteEmptyDir(file);
            } else {
                File files[] = file.listFiles();
                for (File fileDelete : files) {
                    deleteDir(fileDelete);
                }

                if (file.list().length == 0) {
                    deleteEmptyDir(file);
                }
            }
        } else {
            file.delete();
        }
    }

    private void deleteEmptyDir(File file) {
        file.delete();
    }


    @Override
    protected void onPostExecute(Void v) {

        progressDialog.dismiss();
    }


}
