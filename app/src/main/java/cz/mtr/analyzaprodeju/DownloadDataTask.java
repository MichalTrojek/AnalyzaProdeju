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
import java.util.ArrayList;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class DownloadDataTask extends AsyncTask<String, Integer, Void> {

    private static final String TAG = DownloadDataTask.class.getSimpleName();
    String address = "test";
    String username = "test";
    String password = "test";
    String filename = "anl2.csv";


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
            if (downloadAndSaveFile(filename)) {
                readAnalysisFile();
            }
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

                success = ftp.retrieveFile("/www/" + filename, outputStream);


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
        HashMap<String, SharedArticle> map = new HashMap<>();
        HashMap<String, ArrayList<String>> suppliers = new HashMap<>();
        String path = context.getFilesDir().getAbsolutePath() + "/analyza.csv";
        Log.d(TAG, path);
        try {
            InputStream is = new FileInputStream(new File(path));
            @SuppressWarnings("deprecation")
            CSVReader reader = new CSVReader(new InputStreamReader(is, "Windows-1250"), ';', '\"', 1);
            String[] record;
            int rankingCounter = 0;
            while ((record = reader.readNext()) != null) {

                if (record.length == 1) {

                } else {
                    rankingCounter++;
                    String ranking = rankingCounter + "";
                    Log.d(TAG, record.length + "");
                    String eshopCode = record[1];
                    String ean = record[2];

                    String name = record[3];

                    if (name.contains("Dárková knižní") || name.contains("Klubový slevový poukaz")
                            || name.contains("VOUCHER")) {
                        rankingCounter--;
                        continue;
                    }
                    String sales1 = record[4];
                    String sales2 = record[5];
                    String revenue = record[6];
                    String stored = record[7];
                    String daysOfSupplies = record[8];
                    String location = record[9];
                    String price = record[10];
                    String supplier = record[12];
                    String author = record[13];
                    String dateOfLastSale = record[14];
                    String dateOfLastDelivery = record[15];
                    String releaseDate = record[16];
                    String commision = record[17];
                    String rankingEshop = record[18];
                    String sales1DateSince = record[19];
                    String sales1DateTo = record[20];
                    String sales1Days = record[21];
                    String sales2DateSince = record[22];
                    String sales2DateTo = record[23];
                    String sales2Days = record[24];


                    if (!suppliers.containsKey(supplier)) {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(revenue);
                        suppliers.put(supplier, list);
                    } else {

                        suppliers.get(supplier).add(revenue);
                    }



                    SharedArticle shared = new SharedArticle();

                    shared.setEshopCode(eshopCode);
                    shared.setRanking(ranking);
                    shared.setEan(ean);
                    shared.setName(name);
                    shared.setSales1(sales1);
                    shared.setSales2(sales2);
                    shared.setRevenue(revenue);
                    shared.setStored(stored);
                    shared.setDaysOfSupplies(daysOfSupplies);
                    shared.setLocation(location);
                    shared.setPrice(price);
                    shared.setSupplier(supplier);
                    shared.setAuthor(author);
                    shared.setDateOfLastSale(dateOfLastSale);
                    shared.setDateOfLastDelivery(dateOfLastDelivery);
                    shared.setReleaseDate(releaseDate);
                    shared.setCommision(commision);
                    shared.setRankingEshop(rankingEshop);
                    shared.setSales1DateSince(sales1DateSince);
                    shared.setSales1DateTo(sales1DateTo);
                    shared.setSales1Days(sales1Days);
                    shared.setSales2DateSince(sales2DateSince);
                    shared.setSales2DateTo(sales2DateTo);
                    shared.setSales2Days(sales2Days);
                    map.put(ean, shared);
                }

            }
            Log.d(TAG, "" + map.size() + " polozek v analyze");

            if (map.size() != 0) {
                Model.getInstance().setAnalysis(map);
            }
            reader.close();


            for (String supplier : suppliers.keySet()) {
                double total = 0;

                for (String s : suppliers.get(supplier)) {
                    s = s.replace(",", ".");
                    double revenue = Double.parseDouble(s);
                    total += revenue;
                }


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
