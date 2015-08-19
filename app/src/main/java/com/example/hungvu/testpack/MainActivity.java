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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

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


        /***********************************************************************************************************/
        int itemPadding = (int) convertDpToPixel(1);
        int TOTAL_COLUMN_GRID = 4;  // grid get column count
        int TOTAL_ROW_GRID = 5;     // grid get row count
        boolean [][] state = new boolean[TOTAL_ROW_GRID][TOTAL_COLUMN_GRID];

        // sample item 1 (2x1)
        Button button1 = new Button(this);
        button1.setText("1");

        // row spec (which position row?, rowSpan ?), column spec (which column?, column span ?)
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(GridLayout.spec(0, 1), GridLayout.spec(0, 2));
        // save row spec state ( 0+1 is last position of row)
        // save column spec state
        for (int i = 0; i < 0+1; i++) {
            for (int j = 0; j < 0+2; j++) {
                state[i][j] = true;
            }
        }

        // base unit * width
        params1.width = (int) convertDpToPixel(100);
        params1.height = (int) convertDpToPixel(50);
        button1.setLayoutParams(params1);
        button1.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
        button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item));

        // sample 2 (2x2)
        Button button2 = new Button(this);
        button2.setText("2");

        GridLayout.LayoutParams params2 = new GridLayout.LayoutParams(GridLayout.spec(2, 2), GridLayout.spec(0, 2));
        // save row spec state
        // save column spec state
        for (int i = 2; i < 2+2; i++) {
            for (int j = 0; j < 0+2; j++) {
                state[i][j] = true;
            }
        }

        params2.width = (int) convertDpToPixel(100);
        params2.height = (int) convertDpToPixel(100);
        button2.setLayoutParams(params2);
        button2.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
        button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item));

        // sample 3 (1x3)
        Button button3 = new Button(this);
        button3.setText("3");

        GridLayout.LayoutParams params3 = new GridLayout.LayoutParams(GridLayout.spec(1, 3), GridLayout.spec(3, 1));
        // save row spec state
        // save column spec state
        for (int i = 1; i < 1+3; i++) {
            for (int j = 3; j < 3+1; j++) {
                state[i][j] = true;
            }
        }

        params3.width = (int) convertDpToPixel(50);
        params3.height = (int) convertDpToPixel(150);
        button3.setLayoutParams(params3);
        button3.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
        button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item));

        drop_container = (GridLayout) findViewById(R.id.drop_container);
        drop_container.addView(button1);
        drop_container.addView(button2);
        drop_container.addView(button3);

        // fill empty items
        for (int i = 0; i < TOTAL_ROW_GRID; i++) {
            for (int j = 0; j < TOTAL_COLUMN_GRID; j++) {
                // check if state = false -> add empty grid item
                if( !state[i][j] ){

                    // create new view with bordered
                    View view = new View(this);
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_item));
                    GridLayout.LayoutParams paramsEmpty = new GridLayout.LayoutParams(GridLayout.spec(i, 1), GridLayout.spec(j, 1));
                    paramsEmpty.width = (int) convertDpToPixel(50);
                    paramsEmpty.height = (int) convertDpToPixel(50);
                    view.setLayoutParams(paramsEmpty);

                    drop_container.addView(view);
                }
            }
        }

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

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
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
