package com.example.hungvu.testpack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * <pre>
 * require:
 *  - android.permission.ACCESS_NETWORK_STATE
 *  - android.permission.ACCESS_WIFI_STATE
 * </pre>
 */
public class WifiChangeReceiver extends BroadcastReceiver {
    private static final long COUNT_DOWN_TIME = 5000; // 5seconds
    private onWifiChangeListener listener;
    /**
     * don't receive on fist time (no need)
     */
    private boolean fistTime;

    /**
     * last state of wifi connection
     */
    private LastWifiState lastState;
    /**
     * save current wifi info
     */
    private WifiInfo currentWifiInfo = null;

    /**
     * constant last wifi connection state
     */
    public enum LastWifiState {
        CONNECT_FAIL, CHANGED
    }

    /**
     * please call this before register receiver
     *
     * @param listener callback
     */
    public void setListener(onWifiChangeListener listener) {
        fistTime = true;
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMan.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting() && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                // skip first time because it is current connection
                if (!fistTime) {
                    WifiManager mainWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo currentWifi = mainWifi.getConnectionInfo();

                    // save current state
                    lastState = LastWifiState.CHANGED;
                    currentWifiInfo = currentWifi;
                }
            } else {
                // save current state
                lastState = LastWifiState.CONNECT_FAIL;
                currentWifiInfo = null;
            }

            // allow receive on wifi change
            fistTime = false;
        }
    }

    /**
     * call onActivityResult when back from wifi setting
     *
     * @param context for unregister this broadcast
     */
    public void waitForUnregister(final Context context) {
        // (COUNT_DOWN_TIME - 50) is step of timer, don't care it
        // after COUNT_DOWN_TIME (milliseconds) will call onFinish
        new CountDownTimer(COUNT_DOWN_TIME, COUNT_DOWN_TIME - 50) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                // unregister receiver
                try {
                    context.unregisterReceiver(WifiChangeReceiver.this);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (listener != null) {
                    listener.OnFinish(lastState, currentWifiInfo);
                }
            }
        }.start();
    }

    public interface onWifiChangeListener {
        /**
         * after unregister {@link WifiChangeReceiver}
         *
         * @param lastState       {@link WifiChangeReceiver.LastWifiState }
         * @param currentWifiInfo return current wifi info, null if <b>lastState</b> = CONNECT_FAIL
         */
        void OnFinish(LastWifiState lastState, WifiInfo currentWifiInfo);
    }
}
