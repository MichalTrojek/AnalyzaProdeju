package cz.mtr.analyzaprodeju.fragments.ftp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.MainActivity;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class DownloadAnalysisFtpTask extends AsyncTask<String, Integer, Void> {

    private static final String TAG = DownloadAnalysisFtpTask.class.getSimpleName();
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword = "";
    private String mArticleAmount = "";
    private String mPath = "";


    private Context mContext;

    private DialogLoadingFragment progressDialog;


    public DownloadAnalysisFtpTask(Context c, String name) {
        mPath = "/prodejny/" + name;
        this.mContext = c;

    }

    @Override
    protected void onPreExecute() {
        progressDialog = new DialogLoadingFragment();
        progressDialog.setCancelable(false);
        progressDialog.show(((MainActivity) mContext).getSupportFragmentManager(), "FragmentChangeDialog");
    }

    @Override
    protected Void doInBackground(String... voids) {
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.connect(mAddress);
            Log.d(TAG, "Connected. Reply: " + ftp.getReplyString());

            boolean login = ftp.login(mUsername, mPassword);
            if (login) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(mPath);

                String filename = ftp.listFiles()[2].getName();
                readAnalysFromFtp(ftp.retrieveFileStream(filename));
            } else {
                Toast.makeText(mContext, "Nepodarilo se přihlásit do ftp.", Toast.LENGTH_SHORT).show();
            }
            ftp.logout();
            ftp.disconnect();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage() + "");
            e.printStackTrace();
        }
        return null;
    }


    private HashMap<String, SharedArticle> readAnalysFromFtp(InputStream input) {
        HashMap<String, SharedArticle> map = new HashMap<>();
        HashMap<String, ArrayList<String>> suppliers = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(input, "Windows-1250"), ';', '\"', 1);
            String[] record;
            int rankingCounter = 0;
            while ((record = reader.readNext()) != null) {
                try {
                    rankingCounter++;
                    String ranking = rankingCounter + "";
//                    Log.d(TAG, record.length + "");
                    String eshopCode = record[1];
                    String ean = record[2];
//                    Log.d(TAG, ean);
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
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

            mArticleAmount = map.size() + "";
            if (map.size() != 0) {
                Model.getInstance().setAnalysis(map);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    @Override
    protected void onPostExecute(Void v) {
        Toast.makeText(mContext, mArticleAmount + " položek v analýze.", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }


}
