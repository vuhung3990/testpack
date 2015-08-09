package android.gcm.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import de.greenrobot.event.EventBus;

/**
 * when received data from gcm server
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Create a new component identifier.identifier
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.e(GCMConfig.LOG_TAG, "Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.e(GCMConfig.LOG_TAG, "Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                /**
                 * FIXME: get message from server
                 * {
                 *      "data" : {
                 *          "text" : "message",
                 *          "detail" : "some detail here",
                 *          "score" : "10"
                 *      }
                 * }
                 *
                 * ======= sample ==========
                 * + extras.get("text");
                 * + extras.get("detail");
                 * + extras.get("score");
                 */
                String mesage = (String) extras.get(GCMConfig.MESSAGE_KEY);

                // TODO: if you want get data for main thread you can use EventBus 3.0 (option)
                // gradle : compile 'de.greenrobot:eventbus:3.0.0-beta1'
                 EventBus.getDefault().post(mesage);

                // wakeup device and create new notify
                ComponentName comp = new ComponentName(context.getPackageName(), GCMNotificationIntentService.class.getName());
                startWakefulService(context, (intent.setComponent(comp)));
            }
        }

        setResultCode(Activity.RESULT_OK);
    }
}
