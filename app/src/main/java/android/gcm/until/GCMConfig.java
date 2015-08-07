package android.gcm.until;

import com.example.hungvu.testpack.MainActivity;

public interface GCMConfig {
	
	//FIXME: used to share GCM regId with application server - using php app server
	static final String APP_SAVE_REGID = "http://test101.grasys.us/danceMovie/index.php/push_gcm/saveregid";
	static final String APP_REMOVE_REGID = "http://test101.grasys.us/danceMovie/index.php/push_gcm/removeregid";

	//FIXME: Google Project Number
	static final String GOOGLE_PROJECT_NUMBER = "230990792283";
	
	//FIXME: mes array name in server code
	static final String MESSAGE_KEY = "text";
	
	static final String LOG_TAG = "gcm_log";

	// FIXME: edit intent class when click notify
	static final Class<?> target = MainActivity.class;

	// FIXME: preference key to save reg id
	static final String PREFERENCE_KEY_REG_ID = "pref_reg_id";
}
