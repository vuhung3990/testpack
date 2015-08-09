package com.example.hungvu.testpack;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import de.greenrobot.event.EventBus;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post("Service example.");
            }
        }, 3000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
