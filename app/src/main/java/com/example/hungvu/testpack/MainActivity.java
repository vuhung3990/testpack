package com.example.hungvu.testpack;

import android.gcm.utils.GCMRegister;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        // register eventbus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // don't allow background eventbus for save system resource
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // register
        GCMRegister register = new GCMRegister(this, new GCMRegister.GCMRegistrationCallback() {
            @Override
            public void onSuccess(String regid) {
                Log.d("aaa", regid);
            }

            @Override
            public void onFail(GCMRegister.GCMErrorType errorType) {
                if(errorType == GCMRegister.GCMErrorType.CANNOT_COMMUNICATE_WITH_SERVER){
                    Log.d("aaa", "cannot communicate with server");
                }else {
                    Log.d("aaa", "play service not avairable");
                }
            }
        });
        register.execute();
    }

    @Subscribe
    public void getMessageFromIntentService(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
}
