package com.example.hungvu.testpack;

import android.content.ContentUris;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get data
//        Cursor cursor = getContentResolver().query(ContentProviderSample.CONTENT_URI, null, null, null, null);
//        // best performance
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                Log.d("aaaaaaaa", "id=" + cursor.getInt(0) + ", name=" + cursor.getString(cursor.getColumnIndex("name")) + ", age=" + cursor.getInt(cursor.getColumnIndex("age")));
//            }
//            while (cursor.moveToNext());
//            cursor.close();
//        }

        // update data
//        ContentValues cv = new ContentValues();
//        cv.put("name", "vuhung");
//        cv.put("age", 27);
//        getContentResolver().update(ContentUris.withAppendedId(ContentProviderSample.CONTENT_URI, 7), cv, null, null);

        // delete
//        getContentResolver().delete(ContentUris.withAppendedId(ContentProviderSample.CONTENT_URI, 6), null, null);
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
