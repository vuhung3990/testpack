package com.example.hungvu.testpack;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.hungvu.testpack.CustomView.CustomItem;
import com.example.hungvu.testpack.CustomView.ItemObject;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String IMAGEVIEW_TAG = "aaaaaa";
    private ImageView i3;
    private ImageView i1;
    private ImageView i2;
    private GridLayout drop_container;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        i1 = (ImageView) findViewById(R.id.imageView);

        // Sets the tag
        i1.setTag(IMAGEVIEW_TAG);
        i1.setOnTouchListener(this);

        // sample item 1 (2x1)
        CustomItem item1 = new CustomItem(this, new ItemObject(2, 1, new Point(0, 0)));
        // sample 2 (2x2)
        CustomItem item2 = new CustomItem(this, new ItemObject(2, 2, new Point(0, 2)));
        // sample 3 (1x3)
        CustomItem item3 = new CustomItem(this, new ItemObject(1, 3, new Point(3, 1)));

        drop_container = (GridLayout) findViewById(R.id.drop_container);

        drop_container.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                switch (event.getAction()) {
                    // start
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("life", "started");
                        break;

                    // start sending location and when user hover
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("life", "entered");
                        break;

                    // get x, y here
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d("life", "X=" + event.getX() + ", Y=" + event.getY());
                        break;

                    // stop sending location if leave view
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("life", "exited");
                        break;

                    // x, y last change here
                    case DragEvent.ACTION_DROP:
                        // retrieve object state here
                        TagObject object = (TagObject) event.getLocalState();

                        ImageView imageView = new ImageView(drop_container.getContext());
                        imageView.setImageDrawable(i1.getDrawable());
                        drop_container.addView(imageView);
                        Log.d("life", "drop x=" + event.getX() + ", y=" + event.getY()+ ", width="+object.getWidth()+", height="+object.getHeight());
                        break;

                    // end
                    case DragEvent.ACTION_DRAG_ENDED:

                        // show for test
                        i1.setVisibility(View.VISIBLE);
                        Log.d("life", "ended");
                        break;
                }
                return true;
            }
        });
    }

    class TagObject{
        private int width;
        private int height;

        public TagObject(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * @return width
         */
        public int getWidth() {
            return width;
        }

        /**
         * set width
         *
         * @param width
         */
        public void setWidth(int width) {
            this.width = width;
        }

        /**
         * @return height
         */
        public int getHeight() {
            return height;
        }

        /**
         * set height
         *
         * @param height
         */
        public void setHeight(int height) {
            this.height = height;
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
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // create it from the object's tag
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                // create local state object then retrieve later in ACTION_DROP
                TagObject tagObject = new TagObject(2,1);

                view.startDrag(data, //data to be dragged
                        shadowBuilder, //drag shadow
                        tagObject, //local data about the drag and drop operation
                        0   //no needed flags
                );

                // hide but keep view placeholder
                view.setVisibility(View.INVISIBLE);
                return true;
        }
        return false;
    }
}
