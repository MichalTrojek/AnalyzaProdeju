package cz.mtr.analyzaprodeju.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import cz.mtr.analyzaprodeju.fragments.dialogs.DialogLoadingFragment;

public class UnziperTask extends AsyncTask<String, Integer, Boolean> {

    private static final String TAG = UnziperTask.class.getSimpleName();

    private final String DATABASE_NAME = "BooksDatabase.db";
    private final String ZIP_FILE = "BooksDatabase.zip";
    private Context mContext;

    private DialogLoadingFragment mProgressBar;
    private FragmentManager mFragmentManager;


    public UnziperTask(Context context, FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
        mContext = context;


    }

    @Override
    protected void onPreExecute() {
        mProgressBar = new DialogLoadingFragment();
        mProgressBar.setCancelable(false);
        mProgressBar.show(mFragmentManager, "FragmentChangeDialog");
    }

    @Override
    protected Boolean doInBackground(String... voids) {
        InputStream is;
        ZipInputStream zis;
        final File databasePath = mContext.getDatabasePath(DATABASE_NAME);

        if (databasePath.exists()) {
            return false;
        }
        try {
            is = mContext.getAssets().open(ZIP_FILE);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    databasePath.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(databasePath);
                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }
                fout.close();
                zis.closeEntry();
            }
            zis.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }


    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (success) {
            Toast.makeText(mContext, "Databaze nahrána", Toast.LENGTH_SHORT).show();
        }
        mProgressBar.dismiss();
    }


    @Override
    protected void onCancelled() {
        mContext = null;
        mProgressBar.dismiss();
        super.onCancelled();
    }


}
