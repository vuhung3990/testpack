package com.example.hungvu.testpack;

import android.gcm.until.GCMRegister;
import android.gcm.until.SingleTon;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        SingleTon.bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SingleTon.bus.unregister(this);
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
