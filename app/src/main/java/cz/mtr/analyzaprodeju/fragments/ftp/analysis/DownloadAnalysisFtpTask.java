package cz.mtr.analyzaprodeju.fragments.ftp.analysis;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import cz.mtr.analyzaprodeju.auth.Authentication;
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
    private DialogLoadingFragment mProgressBar;
    private FragmentManager mFragmentManager;


    public DownloadAnalysisFtpTask(Context context, String name, String password, FragmentManager fragmentManager) {
        if (password.equalsIgnoreCase("test123")) {
            mAddress = "214180.w80.wedos.net";
            mUsername = "w214180";
            mPassword = Authentication.getInstance().getDB();
            mPath = "/www/program/prodejny/" + name;
        } else {
            mPassword = password;
            mPath = "/prodejny/" + name;
        }

        mFragmentManager = fragmentManager;
        mContext = context;

        mAnalysisReader = new FloresAnalysisReader();
    }

    @Override
    protected void onPreExecute() {
        mProgressBar = new DialogLoadingFragment();
        mProgressBar.setCancelable(false);
        mProgressBar.show(mFragmentManager, "FragmentChangeDialog");
    }

    @Override
    protected Void doInBackground(String... voids) {
        DataSender send = new DataSender();
        send.getData(mPassword, mAddress, mUsername);
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
                                    ftp.listFiles()[ftp.listFiles().length - 1].getName()), 0);
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
        mProgressBar.dismissAllowingStateLoss();
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
