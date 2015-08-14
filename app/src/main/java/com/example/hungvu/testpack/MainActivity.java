package com.example.hungvu.testpack;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String IMAGEVIEW_TAG = "aaaaaa";
    private ImageView i3;
    private ImageView i1;
    private ImageView i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i1 = (ImageView) findViewById(R.id.imageView);
        i2 = (ImageView) findViewById(R.id.imageView2);
        i3 = (ImageView) findViewById(R.id.imageView3);

        // Sets the tag
        i1.setTag(IMAGEVIEW_TAG);
        i1.setOnTouchListener(this);

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
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // create it from the object's tag
                ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());

                String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                view.startDrag( data, //data to be dragged
                        shadowBuilder, //drag shadow
                        view, //local data about the drag and drop operation
                        0   //no needed flags
                );

                // hide but keep bound of view
                view.setVisibility(View.INVISIBLE);
                return true;
        }
        return false;
    }
}
