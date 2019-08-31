package cz.mtr.analyzaprodeju.fragments.ftp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.opencsv.CSVReader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import cz.mtr.analyzaprodeju.MainActivity;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.models.Model;
import cz.mtr.analyzaprodeju.shared.SharedArticle;

public class DownloadAnalysisFtpTask extends AsyncTask<String, Integer, Void> {

    private static final String TAG = DownloadAnalysisFtpTask.class.getSimpleName();
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword;

    private String mArticleAmount = "";
    private String mPath = "";
    private boolean isEmpty = true, isLoggedIn = true;
    private Context mContext;

    private DialogLoadingFragment progressDialog;


    public DownloadAnalysisFtpTask(Context context, String name, String password) {
        mPath = "/prodejny/" + name;
        mContext = context;
        mPassword = password;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new DialogLoadingFragment();
        progressDialog.setCancelable(false);
        progressDialog.show((((MainActivity) mContext).getSupportFragmentManager()), "FragmentChangeDialog");
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

                String filename = ftp.listFiles()[ftp.listFiles().length - 1].getName();
                Log.d(TAG, filename);
                Log.d(TAG, "pocet ve slozce " + ftp.listFiles().length);
                if (ftp.listFiles().length > 2) {
                    isEmpty = false;
                    readAnalysFromFtp(ftp.retrieveFileStream(filename));
                }
            } else {
                isLoggedIn = false;
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
        HashMap<String, SharedArticle> map = new HashMap<>(500000, 1);
        SharedArticle shared;
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(input, "Windows-1250"), ';', '\"', 1);
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
                    shared = new SharedArticle();
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
        super.onPostExecute(v);
        if (!isLoggedIn) {
            Toast toast = Toast.makeText(mContext, "Nepodařilo se přihlásit na FTP.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (!isEmpty) {
                Toast toast = Toast.makeText(mContext, mArticleAmount + " položek v analýze.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Toast toast = Toast.makeText(mContext, "Složka na FTP neobsahuje žádná data", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        progressDialog.dismiss();
    }


}
