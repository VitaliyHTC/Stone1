package com.vitaliyhtc.stone1.activity;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.vitaliyhtc.stone1.Config;
import com.vitaliyhtc.stone1.TimerDialogFragment;
import com.vitaliyhtc.stone1.TimerService;

public class CoreActivity extends AppCompatActivity {

    private BroadcastReceiver mBroadcastReceiver;



    @Override
    protected void onStart(){
        super.onStart();
        performBroadcastReceiverRegistration();

        startService(new Intent(this, TimerService.class));
    }

    @Override
    protected void onStop(){
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    private void performBroadcastReceiverRegistration(){
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String timeString = intent.getExtras().getString(Config.TAG_TIMER_SERVICE_TIME);
                String imageUrl = intent.getExtras().getString(Config.TAG_TIMER_SERVICE_IMAGE);

                FragmentManager manager = getFragmentManager();
                TimerDialogFragment timerDialogFragment = new TimerDialogFragment();
                timerDialogFragment.setInitialParameters(getApplicationContext(), timeString, imageUrl);
                timerDialogFragment.show(manager, "timer_dialog");
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(Config.TAG_BROADCAST_RECEIVER));
    }

}
