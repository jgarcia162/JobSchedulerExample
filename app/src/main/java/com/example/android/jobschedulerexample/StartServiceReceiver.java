package com.example.android.jobschedulerexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.android.jobschedulerexample.util.JobUtil;

import java.util.Objects;

/**
 * When a broadcast is launched by our app, another app, or the device (think charger plugged in, battery low, device restarted, network connected), our app will recieve this broadcast and the code within onReceive will be executed. Here we have our onReceive scheduling a Job.
 */

public class StartServiceReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        /* schedule a job but make sure the broadcasted intent is the intent we are expecting. This protects our app from receiving phony broadcasts made third party sources (think Malware or malicious third party apps) */
        if (Objects.equals(intent.getAction(), Intent.ACTION_HEADSET_PLUG)) {
            JobUtil.scheduleJob(context);
        }
    }
}
