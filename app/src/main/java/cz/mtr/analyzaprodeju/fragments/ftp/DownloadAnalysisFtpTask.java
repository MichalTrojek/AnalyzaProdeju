package cz.mtr.analyzaprodeju.fragments.ftp;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import cz.mtr.analyzaprodeju.FloresAnalysisReader;
import cz.mtr.analyzaprodeju.MainActivity;
import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;

public class DownloadAnalysisFtpTask extends AsyncTask<String, Integer, Void> {

    private static final String TAG = DownloadAnalysisFtpTask.class.getSimpleName();
    private String mAddress = "81.95.110.138";
    private String mUsername = "knihydobro9";
    private String mPassword;


    private String mPath = "";
    private boolean isEmpty = true, isLoggedIn = true;
    private Context mContext;
    private FloresAnalysisReader mAnalysisReader;
    private DialogLoadingFragment progressDialog;


    public DownloadAnalysisFtpTask(Context context, String name, String password) {
        mPath = "/prodejny/" + name;
        mContext = context;
        mPassword = password;
        mAnalysisReader = new FloresAnalysisReader();
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
            boolean login = ftp.login(mUsername, mPassword);
            if (login) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(mPath);
                if (ftp.listFiles().length > 2) {
                    isEmpty = false;
                    mAnalysisReader.readAnalysisFromFtp(
                            ftp.retrieveFileStream(
                                    ftp.listFiles()[ftp.listFiles().length - 1].getName()));
                }
            } else {
                isLoggedIn = false;
            }
            ftp.logout();
            ftp.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        if (!isLoggedIn) {
            showToast("Nepodařilo se přihlásit na FTP.", true);
        } else {
            if (!isEmpty) {
                showToast(mAnalysisReader.getArticleAmount() + " položek v analýze.", false);
            } else {
                showToast("Složka na FTP neobsahuje žádná data", true);
            }
        }
        mContext = null;
        progressDialog.dismiss();
    }

    private void showToast(String text, boolean isShort) {
        Toast toast = Toast.makeText(mContext, text, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onCancelled() {
        mContext = null;
        super.onCancelled();
    }
}
