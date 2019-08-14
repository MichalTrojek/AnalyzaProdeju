package cz.mtr.analyzaprodeju;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import cz.mtr.analyzaprodeju.fragments.ftp.UpdateDatabaseTask;

public class UpdateDataJobService extends JobService {

    private static final String TAG = UpdateDatabaseTask.class.getSimpleName();
    private boolean jobCancelled = false;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "UpdateDataJob started");
        doBackgroundWork(jobParameters);


        return true;
    }


    private void doBackgroundWork(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "Run: " + i);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "JOB finished");
                jobFinished(jobParameters, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true; // pokud pouzivam async task tak tady zavolam cancel.
        return true; // true =  prelozit termin
    }
}
