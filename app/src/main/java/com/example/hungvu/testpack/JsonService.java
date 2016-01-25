package com.example.hungvu.testpack;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by hung on 1/22/16.
 */
public class JsonService extends IntentService {
    public JsonService() {
        super("json service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        RxBus.getInstance().send("start sleep 5s");
        try {
            Thread.sleep(5000);
            RxBus.getInstance().send("end sleep 5s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
