package cz.mtr.analyzaprodeju.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import cz.mtr.analyzaprodeju.models.Model;

public class UpdateDataJobService extends JobService {

    private static final String TAG = UpdateDatabaseTask.class.getSimpleName();
    private boolean jobCancelled = false;
    private UpdateDataServiceTask mTask;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "UpdateDataJob started");
        doBackgroundWork(jobParameters);
        return true; // true = task bude trvat delsi dobu
    }


    private void doBackgroundWork(final JobParameters jobParameters) {
        String storeName = Model.getInstance().getPrefs().getSelectedStore();
        String password = Model.getInstance().getPrefs().getPassword();

        if (!storeName.isEmpty() && !password.isEmpty()) {
            Log.d(TAG, "Task called");
            mTask = new UpdateDataServiceTask(storeName, password) {
                @Override
                protected void onPostExecute(Boolean success) {
                    jobFinished(jobParameters, !success);
                }
            };
            mTask.execute();
        }
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job cancelled before completion");
        if (mTask != null) {
            mTask.cancel(true);
        }
        jobCancelled = true; // pokud pouzivam async task tak tady zavolam cancel.
        return true; // true =  prelozit termin
    }
}
