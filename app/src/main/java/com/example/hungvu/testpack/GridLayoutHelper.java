package com.example.hungvu.testpack;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

/**
 * quick setup my grid layout helper
 */
public class GridLayoutHelper {
    /**
     * screen width / totalColumnGrid
     */
    private final int baseUnit;

    /**
     * save context of grid layout
     */
    private final Context context;

    /**
     * default padding of item
     */
    private int itemPadding = (int) convertDpToPixel(0.5f);

    private GridLayout gridLayout;

    private int totalColumnGrid;
    private int totalRowGrid;

    /**
     * this variable to save gridlayout state ( true: position used, false: free space )
     */
    boolean [][] state;

    public GridLayoutHelper(GridLayout gridLayout, int baseUnit) {
        this.gridLayout = gridLayout;

        // init
        totalColumnGrid = gridLayout.getColumnCount();
        totalRowGrid = gridLayout.getRowCount();
        state = new boolean[totalRowGrid][totalColumnGrid];

        // TODO: calculate base unit
        this.baseUnit = baseUnit;

        // get context from grid layout
        context = gridLayout.getContext();
    }

    public void addView(int row, int rowSpan, int column, int columnSpan){
        // create new view
        Button button = new Button(context);
        // set view's content
        button.setText("1");

        // row spec (which position row?, rowSpan ?), column spec (which column?, column span ?)
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row, rowSpan), GridLayout.spec(column, columnSpan));
        // base unit * width (sample: width = base_unit=50 * columnSpan=2 || height = base_unit=50 * rowSpan=1)
        params.width = (int) convertDpToPixel(baseUnit * columnSpan);
        params.height = (int) convertDpToPixel(baseUnit * rowSpan);

        // calculate row spec state ( row + rowSpan is last position of row)
        for (int i = row; i < row + rowSpan; i++) {
            // calculate column spec state
            for (int j = column; j < column + columnSpan; j++) {
                // set state is true( means it's used )
                state[i][j] = true;
            }
        }

        // set layout param
        button.setLayoutParams(params);

        // set default padding
        button.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);

        // set my border (option)
        button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_item));

        // add to grid layout
        gridLayout.addView(button);
    }

    public void fillAllEmptyItem(){
        // fill all empty items
        for (int i = 0; i < totalRowGrid; i++) {
            for (int j = 0; j < totalColumnGrid; j++) {
                // check if state = false -> add empty grid item
                if( !state[i][j] ){

                    // create new view with bordered
                    View view = new View(context);
                    view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_item));
                    GridLayout.LayoutParams paramsEmpty = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));

                    // empty width , height = base unit
                    paramsEmpty.width = (int) convertDpToPixel(baseUnit);
                    paramsEmpty.height = (int) convertDpToPixel(baseUnit);
                    view.setLayoutParams(paramsEmpty);

                    // add to grid layout
                    gridLayout.addView(view);
                }
            }
        }
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
}
