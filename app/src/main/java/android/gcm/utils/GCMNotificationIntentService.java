package android.gcm.utils;

import android.app.IntentService;
import android.content.Intent;

/**
 * for wakeup device
 */
public class GCMNotificationIntentService extends IntentService {

	/**
	 * must have blank constructor
	 */
	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// FIXME: android studio support create new notify or you can create your own custom notify
		// FIXME: pass intent extra from receive
		GCMNotification.notify(this, "you have a new message", "Sample tite", "message here", 10);
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
}