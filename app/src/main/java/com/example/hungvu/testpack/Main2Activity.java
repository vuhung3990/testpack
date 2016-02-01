package com.example.hungvu.testpack;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Subscriber;
import rx.Subscription;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "rxJava";
    private Subscription sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sub = RxBus.getInstance().subscribe(Integer.class, new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "onNext: " + o);
            }
        });

        new AsyncTask<Void, Integer, Void>() {

            @Override
            protected void onProgressUpdate(Integer... values) {
                RxBus.getInstance().send(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(500);
                        publishProgress(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        RxBus.getInstance().unSubscribe(sub);
        super.onDestroy();
    }
}
