package com.example.android.jobschedulerexample.util;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.android.jobschedulerexample.TestJobService;

/**
 * This class is a helper class to schedule Jobs. We can create different methods to schedule different jobs as we please. Creating a separate class for this decouples our code, making it easier to edit or remove Jobs, and provides easier access to Job creation from different classes within our project
 */
@SuppressLint("JobSchedulerService") //<-- ignore this line, not needed
public class JobUtil {
    @RequiresApi(api = Build.VERSION_CODES.M) // <-- getSystemService method is only implemented starting from API M
    public static void scheduleJob(Context context) {
        /*
          The ComponentName object is the component we are using. Components available in Android include BroadcastReceiver, ContentProvider, Activity, and Service. In our case we provide TestJobService as the second parameter to the ComponentName constructor because our TestJobService extends a JobService
        */
        ComponentName serviceComponent = new ComponentName(context, TestJobService.class);


        /*The JobInfo object allows you to define the criteria for the JobService. Specifying when the Job should be performed, what broadcasts to listen for to start the Job (i.e. device charging, connected to network, etc..) and other settings.*/
        JobInfo.Builder jobInfo = new JobInfo.Builder(0, serviceComponent);

        //tell the Job to wait at least one second before executing
        jobInfo.setMinimumLatency(1000);

        //tell the job to wait a maximum of 3 seconds
        jobInfo.setOverrideDeadline(3000);

        //device must be charging for job to execute
        jobInfo.setRequiresCharging(false);

        //these and other settings can be specified

        //get the JobScheduler service that will be scheduling our Jobs
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);

        //build the job and schedule it if the jobScheduler is not null
        if (jobScheduler != null) {
            jobScheduler.schedule(jobInfo.build());
        }

    }
}
