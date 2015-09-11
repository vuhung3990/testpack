package com.example.hungvu.testpack;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements WifiChangeReceiver.onWifiChangeListener {

    private static final int REQUEST_CODE = 1001;
    private WifiChangeReceiver receiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new WifiChangeReceiver();
        receiver.setListener(this);

        // -------------------------------------- //;
        // register receiver
        registerReceiver(receiver, filter);
        // start setting activity
        startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            Log.d("aaa", "timer");
            receiver.waitForUnregister(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnFinish(WifiChangeReceiver.LastWifiState lastState, WifiInfo currentWifiInfo) {
        Log.d("aaa", "|====| lastState="+lastState+", currentWifiInfo="+currentWifiInfo);
    }
}
