package com.example.android.jobschedulerexample;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/* Register the receiver at runtime. The intent filter is the action our broadcast receiver in our app is listening for. When a headset is plugged in, our StartServiceReceiver will execute its onReceive method which calls JobUtil.scheduleJob() which then schedules the Job we asked to be scheduled */
        registerReceiver(new StartServiceReceiver(), new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

}
