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


        /*The JobInfo object allows you to define the criteria for the JobService. Specifying when the Job should be performed, what broadcasts to listen for to start the Job (i.e. device charging, connected to network, etc..) and other settings. the Job ID is used by the OS to ensure multiple instances of the same Jobs are not scheduled, this number is arbitrary and we can specify our own*/
        JobInfo.Builder jobInfo = new JobInfo.Builder(0, serviceComponent);

        /*Tell Android to wait a maximum of 3 seconds. Why? The entire point of the JobScheduler is to group together similar tasks then execute them simultaneously in order to save battery. Android will wait a set amount of time for other similar Jobs to be scheduled before beginning execution of those jobs. Example, multiple apps need to download some data, one app schedules a networking job, android will hold on to that job for a set amount of time to see if any other apps also request to use the network connection. We can tell Android to not wait more than x amount of time with the setOverrideDeadline. As the name implies, we are overriding the OS' default deadline */
        jobInfo.setOverrideDeadline(3000);

        //device does not need to be charging for job to execute
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
