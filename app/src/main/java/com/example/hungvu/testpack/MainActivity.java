package com.example.hungvu.testpack;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import static com.example.hungvu.testpack.GridLayoutHelper.*;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String IMAGEVIEW_TAG = "aaaaaa";
    private TextView i3;
    private TextView i1;
    private TextView i2;
    private GridLayout drop_container;
    private LayoutInflater inflater;
    private GridLayoutHelper gridLayoutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        i1 = (TextView) findViewById(R.id.imageView1);
        i2 = (TextView) findViewById(R.id.imageView2);
        i3 = (TextView) findViewById(R.id.imageView3);

        // set the tag
        i1.setTag(IMAGEVIEW_TAG);
        i1.setOnTouchListener(this);

        i2.setTag(IMAGEVIEW_TAG);
        i2.setOnTouchListener(this);

        i3.setTag(IMAGEVIEW_TAG);
        i3.setOnTouchListener(this);


        drop_container = (GridLayout) findViewById(R.id.drop_container);

        // event
        View.OnDragListener dragListener = new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                // retrieve drop position
                GridItemProperties properties = GridItemProperties.retrieveObjectFromTag((String) v.getTag());

                // retrieve size of drag object
                GridLayoutHelper.DragObject object = (GridLayoutHelper.DragObject) event.getLocalState();
                Log.d("life", object.getWidth() + ":" + object.getHeight());

                switch (event.getAction()) {
                    // start
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("life", "started");
                        break;

                    // start sending location and when user hover
                    case DragEvent.ACTION_DRAG_ENTERED:
                        gridLayoutHelper.setColorHint(properties.getRow(), object.getHeight(), properties.getColumn(), object.getWidth(), true);
                        Log.d("life", "entered");
                        break;

                    // get x, y here
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d("life", "X=" + event.getX() + ", Y=" + event.getY());

                        break;

                    // stop sending location if leave view
                    case DragEvent.ACTION_DRAG_EXITED:
                        gridLayoutHelper.setColorHint(properties.getRow(), object.getHeight(), properties.getColumn(), object.getWidth(), false);
                        Log.d("life", "exited");
                        break;

                    // x, y last change here
                    case DragEvent.ACTION_DROP:
                        // if re-order item, i will remove it before add new item
                        if(object.getAction() == DragObject.DragUserAction.RE_ORDER){
                            GridItemProperties itemProperties = new GridItemProperties(object.getRow(), object.getHeight(), object.getColumn(), object.getWidth());
                            gridLayoutHelper.removeViewInGrid(itemProperties);
                        }
                        gridLayoutHelper.addView(properties.getRow(), object.getHeight(), properties.getColumn(), object.getWidth());

//                        Log.d("aaa", view.getId()+"..."+drop_container.getId());
//
//                        ImageView imageView = new ImageView(drop_container.getContext());
//                        imageView.setImageDrawable(i1.getDrawable());
//                        drop_container.addView(imageView);
                        Log.d("life", "drop x=" + event.getX() + ", y=" + event.getY() + ", width=" + object.getWidth() + ", height=" + object.getHeight());
                        break;

                    // end
                    case DragEvent.ACTION_DRAG_ENDED:

                        // show for test
                        i1.setVisibility(View.VISIBLE);
                        i2.setVisibility(View.VISIBLE);
                        i3.setVisibility(View.VISIBLE);
                        Log.d("life", "ended");
                        break;
                }
                return true;
            }
        };

        gridLayoutHelper = new GridLayoutHelper(drop_container, dragListener);
        gridLayoutHelper.setOnAddItemListener(new addViewEvent() {
            @Override
            public void onSuccess(GridItemProperties properties) {
                Log.e(getLocalClassName(), "Success: " + properties.getTagFromProperties());
            }

            @Override
            public void onDuplicate(GridItemProperties properties) {
                Log.e(getLocalClassName(), "Duplicate: " + properties.getTagFromProperties());
            }

            @Override
            public void onBadParameters(GridItemProperties properties) {
                Log.e(getLocalClassName(), "Bad params: " + properties.getTagFromProperties());
            }

            @Override
            public void onNotEnoughSpace(GridItemProperties properties) {
                Log.e(getLocalClassName(), "Not enough space: " + properties.getTagFromProperties());
            }
        });

//        gridLayoutHelper.addView(2, 3, 3, 1);
//        gridLayoutHelper.addView(0, 3, 0, 2);
//        // install invalid param ( Bad params: 3_3_0_1 )
//        gridLayoutHelper.addView(3, 3, 0, 1);
//        // install out of bound position ( Not enough space: 1_2_1_2 )
//        gridLayoutHelper.addView(1, 2, 1, 2);


//        drop_container.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View view, DragEvent event) {
//                switch (event.getAction()) {
//                    // start
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        Log.d("life", "started");
//                        break;
//
//                    // start sending location and when user hover
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        Log.d("life", "entered");
//                        break;
//
//                    // get x, y here
//                    case DragEvent.ACTION_DRAG_LOCATION:
//                        Log.d("life", "X=" + event.getX() + ", Y=" + event.getY());
//                        break;
//
//                    // stop sending location if leave view
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        Log.d("life", "exited");
//                        break;
//
//                    // x, y last change here
//                    case DragEvent.ACTION_DROP:
//                        // retrieve object state here
//                        DragObject object = (DragObject) event.getLocalState();
//
//
//                        Log.d("aaa", view.getId()+"..."+drop_container.getId());
//
//                        ImageView imageView = new ImageView(drop_container.getContext());
//                        imageView.setImageDrawable(i1.getDrawable());
//                        drop_container.addView(imageView);
//                        Log.d("life", "drop x=" + event.getX() + ", y=" + event.getY() + ", width=" + object.getWidth() + ", height=" + object.getHeight());
//                        break;
//
//                    // end
//                    case DragEvent.ACTION_DRAG_ENDED:
//
//                        // show for test
//                        i1.setVisibility(View.VISIBLE);
//                        Log.d("life", "ended");
//                        break;
//                }
//                return true;
//            }
//        });
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
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
                GridLayoutHelper.DragObject dragObject = null;
                if (view.getId() == i1.getId()) {
                    dragObject = new GridLayoutHelper.DragObject(1, 1);
                } else if (view.getId() == i2.getId()) {
                    dragObject = new GridLayoutHelper.DragObject(2, 2);
                } else if (view.getId() == i3.getId()) {
                    dragObject = new GridLayoutHelper.DragObject(2, 1);
                }

                view.startDrag(data, //data to be dragged
                        shadowBuilder, //drag shadow
                        dragObject, //local data about the drag and drop operation
                        0   //no needed flags
                );

                // hide but keep view placeholder
                view.setVisibility(View.INVISIBLE);
                return true;
        }
        return false;
    }
}
