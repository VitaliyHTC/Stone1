package com.vitaliyhtc.stone1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerService extends Service {

    SimpleDateFormat simpleDateFormat;

    private int counter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        counter++;
        if(counter == 1){
            makeTimer();
        }

        return START_NOT_STICKY;
    }

    private void makeTimer(){
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(1);

        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Intent intent = new Intent(Config.TAG_BROADCAST_RECEIVER);

                Calendar calendar = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                String time = simpleDateFormat.format(calendar.getTime());

                intent.putExtra(Config.TAG_TIMER_SERVICE_TIME, time);
                intent.putExtra(Config.TAG_TIMER_SERVICE_IMAGE, "http://lorempixel.com/400/400/");

                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
        }, 0, 2, TimeUnit.MINUTES);
    }
}
