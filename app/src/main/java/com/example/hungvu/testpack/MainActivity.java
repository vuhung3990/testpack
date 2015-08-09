package com.example.hungvu.testpack;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        // register for better performance
        Log.d("onEvent", "Register");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save android resource
        Log.d("onEvent", "Unregister");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sample 1
        postMessage();

        // sample 2 async
        new AsyncTask<String, Integer, Void>(){

            @Override
            protected void onProgressUpdate(Integer... values) {
                EventBus.getDefault().post(values[0]);
            }

            @Override
            protected Void doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    byte data[] = new byte[1024];

                    long total = 0;

                    int count = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress((int) ((total * 100) / lenghtOfFile));
                    }

                    input.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                EventBus.getDefault().post("Image Completed.");

                // eventbus example 3
                startService(new Intent(MainActivity.this, MyService.class));
            }
        }.execute("http://xdind.com/wp-content/uploads/2013/06/trolling.jpg");
    }

    /**
     * send sample mesage every 10 seconds
     */
    private void postMessage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("onEvent", "send mesage");
                EventBus.getDefault().post("message " + System.currentTimeMillis());
                EventBus.getDefault().post((Long) System.currentTimeMillis());
                postMessage();
            }
        }, 10000);
    }

    /**
     * sample 1, Must have @Subscribe for get event
     * @param message
     */
    @Subscribe
    public void getMessage(String message){
        Log.d("onEvent", message);
    }

    /**
     * other sample 1
     * @param unix
     */
    @Subscribe
    public void getTime(Long unix){
        Log.d("onEvent", "unix: "+unix);
    }

    @Subscribe
    public void progressUpdate(Integer progress){
        Log.d("onEvent", "download: "+progress+"%");
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
