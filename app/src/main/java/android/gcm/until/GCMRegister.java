package android.gcm.until;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by hungvu on 8/6/15.
 */
public class GCMRegister extends AsyncTask<Void, Void, String> {
    private final Context context;
    private final SharedPreferences preferences;
    private final GCMRegistrationCallback callback;
    private String regIdSaved;

    public GCMRegister(Context context, GCMRegistrationCallback callback) {
        this.context = context;
        this.callback = callback;
        preferences = context.getSharedPreferences(context.getClass().getSimpleName(), Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        // get current reg id saved in sharepreference
        regIdSaved = preferences.getString(GCMConfig.PREFERENCE_KEY_REG_ID, null);
    }

    @Override
    protected String doInBackground(Void... params) {
        if(regIdSaved == null && GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS){
            try {
                // register client
                GoogleCloudMessaging gcmObj = GoogleCloudMessaging.getInstance(context);
                return gcmObj.register(GCMConfig.GOOGLE_PROJECT_NUMBER);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String regid) {
        if(!TextUtils.isEmpty(regid)){
            // store in device
            preferences.edit().putString(GCMConfig.PREFERENCE_KEY_REG_ID, regid).commit();
            regIdSaved = regid;
            
            // callback success
            callback.onSuccess(regid);

            // TODO: store in server (option)
        }else {
            // callback fail
            callback.onFail(GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS ? GCMErrorType.CANNOT_COMMUNICATE_WITH_SERVER : GCMErrorType.GOOGLE_PLAY_SERVICE_NOT_AVAIRABLE);
        }
    }

    /**
     * Constant which error will be
     */
    public enum GCMErrorType {
        CANNOT_COMMUNICATE_WITH_SERVER,
        GOOGLE_PLAY_SERVICE_NOT_AVAIRABLE
    }

    /**
     * Gcm callback
     */
    public interface GCMRegistrationCallback {
        /**
         * when success
         * @param regid registration id
         */
        void onSuccess(String regid);

        /**
         * when cannot get reg id
         * @param gcmErrorType which error? {@link android.gcm.until.GCMRegister.GCMErrorType}
         */
        void onFail(GCMErrorType gcmErrorType);
    }
}
