package cz.mtr.analyzaprodeju.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import cz.mtr.analyzaprodeju.asynctasks.UpdateAnalysisTask;
import cz.mtr.analyzaprodeju.repository.preferences.GeneralPreferences;

public class UpdateAnalysisJobService extends JobService {

    private static final String TAG = UpdateAnalysisJobService.class.getSimpleName();
    private boolean jobCancelled = false;
    private UpdateAnalysisTask mTask;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "UpdateAnalysisJobService started");
        doBackgroundWork(jobParameters);
        return true; // true = task bude trvat delsi dobu
    }


    private void doBackgroundWork(final JobParameters jobParameters) {
        String storeName = GeneralPreferences.getInstance().loadSelectedStore();
        String password = GeneralPreferences.getInstance().loadPassword();
        if (!storeName.isEmpty() && !password.isEmpty()) {
            Log.d(TAG, "Task called");
            mTask = new UpdateAnalysisTask(storeName, password) {
                @Override
                protected void onPostExecute(Boolean success) {
                    jobFinished(jobParameters, !success);
                    Log.d(TAG, "JOB FINISHED");
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
