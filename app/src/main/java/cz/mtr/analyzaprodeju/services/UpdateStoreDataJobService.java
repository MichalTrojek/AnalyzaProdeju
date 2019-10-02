package cz.mtr.analyzaprodeju.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;
import cz.mtr.analyzaprodeju.services.asynctasks.UpdateStoreDataTask;

public class UpdateStoreDataJobService extends JobService {

    private static final String TAG = UpdateStoreDataJobService.class.getSimpleName();
    private boolean jobCancelled = false;
    private UpdateStoreDataTask mTask;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "UpdateStoreDataService has started");
        GeneralPreferences.init(getApplicationContext());
        doBackgroundWork(jobParameters);
        return true; // true = task bude trvat delsi dobu
    }


    private void doBackgroundWork(final JobParameters jobParameters) {
        String storeName = GeneralPreferences.getInstance().loadFilename();
        String password = GeneralPreferences.getInstance().loadPassword();
        if (!storeName.isEmpty() && !password.isEmpty()) {
            Log.d(TAG, "UpdateStoreDataService Task called");
            mTask = new UpdateStoreDataTask(storeName, password) {
                @Override
                protected void onPostExecute(Boolean success) {
                    jobFinished(jobParameters, !success);
                    Log.d(TAG, "UpdateStoreDataService JOB FINISHED");
                }
            };
            mTask.execute();
        }
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "UpdateStoreDataService Job cancelled before completion");
        if (mTask != null) {
            mTask.cancel(true);
        }
        jobCancelled = true; // pokud pouzivam async task tak tady zavolam cancel.
        return true; // true =  prelozit termin
    }
}
