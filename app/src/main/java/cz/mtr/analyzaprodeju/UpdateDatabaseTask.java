package cz.mtr.analyzaprodeju;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;

import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;
import cz.mtr.analyzaprodeju.fragments.ftp.FtpViewModel;
import cz.mtr.analyzaprodeju.repository.room.Article;

public class UpdateDatabaseTask extends AsyncTask<String, Integer, Void> {

    private static final String TAG = UpdateDatabaseTask.class.getSimpleName();
    String filename = "data.csv";
    String address = "test";
    String username = "test";
    String password = "test";
    ArrayList<Article> items = new ArrayList<>();
    FtpViewModel mViewModel;


    private Context context;

    private DialogLoadingFragment progressDialog;


    public UpdateDatabaseTask(Context c, FtpViewModel viewModel) {
        this.context = c;
        mViewModel = viewModel;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new DialogLoadingFragment();
        progressDialog.setCancelable(false);
        progressDialog.show(((MainActivity) context).getSupportFragmentManager(), "FragmentChangeDialog");
    }

    @Override
    protected Void doInBackground(String... voids) {
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.connect(address);
            Log.d(TAG, "Connected. Reply: " + ftp.getReplyString());

            boolean login = ftp.login(username, password);
            if (login) {
                Log.d(TAG, "Logged in");
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                Log.d(TAG, "Downloading");
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory("/vsechny");
                readFileStream(ftp.retrieveFileStream(filename));
                mViewModel.insertAll(items);
            } else {
                Toast.makeText(context, "Nepodarilo se přihlásit do ftp.", Toast.LENGTH_SHORT).show();
            }

            ftp.logout();
            ftp.disconnect();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage() + "");
            e.printStackTrace();
        }


        return null;
    }

    private void readFileStream(InputStream input) {
        try {
            CSVParser parser = CSVParser.parse(input, Charset.forName("Windows-1250"),
                    CSVFormat.RFC4180.withDelimiter(';').withQuote('"').withRecordSeparator("\r\n")
                            .withIgnoreEmptyLines(false).withIgnoreSurroundingSpaces().withFirstRecordAsHeader()
                            .withHeader("ean", "name", "price"));
            for (CSVRecord record : parser) {
                if (record.size() == 3) {
                    String ean = "";
                    String price = "";
                    String name = "";
                    ean = record.get("ean");
                    name = record.get("name");
                    price = record.get("price");
                    String normalizedName = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
                            .toLowerCase();
                    items.add(new Article(ean, name, normalizedName, price));
                } else {
                    // je to spatne naformatovane a neobsahujete zadna dulezita data
                }
            }

        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }


    }

//    @Override
//    protected Void doInBackground(String... voids) {
//        try {
//            if (downloadAndSaveFile(filename)) {
//                readFile();
//                Log.d(TAG, "FINISHED DOWNLOADING");
//                mViewModel.insertAll(items);
//            } else {
//                Log.d(TAG, "FAILED");
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "ERROR " + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }


    private Boolean downloadAndSaveFile(String filename)
            throws IOException {
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.connect(address);
            Log.d(TAG, "Connected. Reply: " + ftp.getReplyString());
            boolean success = false;
            boolean login = ftp.login(username, password);
            if (login) {
                Log.d(TAG, "Logged in");
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                Log.d(TAG, "Downloading");
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory("/vsechny");
                OutputStream outputStream = context.openFileOutput("data.csv", Context.MODE_PRIVATE);
                success = ftp.retrieveFile(filename, outputStream);
                outputStream.close();
            } else {
                Toast.makeText(context, "Nepodarilo se přihlásit do ftp.", Toast.LENGTH_SHORT).show();
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


    private void readFile() {
        String path = context.getFilesDir().getAbsolutePath() + "/data.csv";
        File input = new File(path);

        try {
            CSVParser parser = CSVParser.parse(input, Charset.forName("Windows-1250"),
                    CSVFormat.RFC4180.withDelimiter(';').withQuote('"').withRecordSeparator("\r\n")
                            .withIgnoreEmptyLines(false).withIgnoreSurroundingSpaces().withFirstRecordAsHeader()
                            .withHeader("ean", "name", "price"));


            for (CSVRecord record : parser) {
                if (record.size() == 3) {
                    String ean = "";
                    String price = "";
                    String name = "";
                    ean = record.get("ean");
                    name = record.get("name");
                    price = record.get("price");
                    String normalizedName = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
                            .toLowerCase();

                    items.add(new Article(ean, name, normalizedName, price));

                } else {
                    // je to spatne naformatovane a neobsahujete zadna dulezita data
                }

            }

        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }


    }


    @Override
    protected void onPostExecute(Void v) {
//        Model.getInstance().setAllDbItems(items);
        progressDialog.dismiss();
    }


}
