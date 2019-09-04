package cz.mtr.analyzaprodeju.repository.room;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseCopier {

    private static final String TAG = DatabaseCopier.class.getSimpleName();
    private static final String DATABASE_NAME = "BooksDatabase.db";
    private Context mContext;
    private ContextWrapper cw;


    public DatabaseCopier(Context context) {
        this.mContext = context;
    }


    public void copy() {
        final File databasePath = mContext.getDatabasePath(DATABASE_NAME);
        if (databasePath.exists()) {
            return; // if database already exists, do nothing.
        }
        databasePath.getParentFile().mkdirs(); // make sure it has parent directory, in this case /databases/ or else it throws error


        try {
            InputStream input = mContext.getAssets().open(DATABASE_NAME);
            OutputStream output = new FileOutputStream(databasePath);

            byte[] buffer = new byte[8192];
            int length;


            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.close();
            output.flush();
            input.close();
            Log.i(TAG, "Database has been copied.");
            Log.d("DEBUG","DONE");
        } catch (IOException e) {
            Log.i(TAG, "Database has not been copied.");
            e.printStackTrace();
        }


    }


}
