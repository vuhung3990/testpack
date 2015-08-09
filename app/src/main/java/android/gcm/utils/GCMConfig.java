package android.gcm.utils;

import com.example.hungvu.testpack.MainActivity;

/**
 * Config gcm
 */
public class GCMConfig {
	
	//FIXME: used to share GCM regId with application server - using php app server (option)
	// static final String URL_SAVE_REGID_ON_SERVER = "http://test101.grasys.us/danceMovie/index.php/push_gcm/saveregid";
	// static final String URL_REMOVE_REGID_ON_SERVER = "http://test101.grasys.us/danceMovie/index.php/push_gcm/removeregid";

	//FIXME: Google Project Number
	static final String GOOGLE_PROJECT_NUMBER = "230990792283";
	
	//FIXME: mes array name in server code
	static final String MESSAGE_KEY = "text";
	
	static final String LOG_TAG = "gcm_log";

	// FIXME: edit intent class when click notify (if you use default notify)
	static final Class<?> target = MainActivity.class;

	// FIXME: preference key to save reg id
	static final String PREFERENCE_KEY_REG_ID = "pref_reg_id";
}
