package com.example.hungvu.testpack;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String IMAGEVIEW_TAG = "aaaaaa";
    private ImageView i3;
    private ImageView i1;
    private ImageView i2;
    private GridLayout drop_container;
    private RecyclerView recycleView;
    private List<GridObject> listData;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i1 = (ImageView) findViewById(R.id.imageView);
        i2 = (ImageView) findViewById(R.id.imageView2);

        /** ============================================== **/
        // setup recycle view
        recycleView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recycleView.setHasFixedSize(true);

        listData = new ArrayList<>();

        // set layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5); /*  [*]  */
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // don't put getWidth larger than spanCount [*]
                return listData.get(position).getWidth();
            }
        });
        recycleView.setLayoutManager(layoutManager);

        // adapter
        adapter = new GridAdapter(listData);
        recycleView.setAdapter(adapter);
        /** ============================================== **/

        // Sets the tag
        i1.setTag(IMAGEVIEW_TAG);
        i1.setOnTouchListener(this);
        i2.setTag(IMAGEVIEW_TAG);
        i2.setOnTouchListener(this);

        recycleView.setOnDragListener(new View.OnDragListener() {
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
                        GridObject object = (GridObject) event.getLocalState();

                        // add to grid
                        listData.add(object);
                        adapter.notifyDataSetChanged();

                        Log.d("life", "drop x=" + event.getX() + ", y=" + event.getY()+ ", width="+object.getWidth()+", height="+object.getHeight());
                        break;

                    // end
                    case DragEvent.ACTION_DRAG_ENDED:

                        // show for test
                        i1.setVisibility(View.VISIBLE);
                        i2.setVisibility(View.VISIBLE);
                        Log.d("life", "ended");
                        break;
                }
                return true;
            }
        });
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
                // view.getId() == R.id.imageView ? 1 : 2 | width = 1 if image else 2
                GridObject tagObject = new GridObject(view.getId() == R.id.imageView ? 1 : 2, 1, "text", getResources().getDrawable(R.mipmap.ic_launcher));

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
