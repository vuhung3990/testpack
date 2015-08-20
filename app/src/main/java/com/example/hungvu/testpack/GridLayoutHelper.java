package com.example.hungvu.testpack;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
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

    /**
     * total column of grid
     */
    private int totalColumnGrid;
    /**
     * total row of grid
     */
    private int totalRowGrid;

    /**
     * this variable to save gridlayout state ( true: position used, false: free space )
     */
    private boolean[][] state;

    public GridLayoutHelper(GridLayout gridLayout) {
        this.gridLayout = gridLayout;

        // get context from grid layout
        context = gridLayout.getContext();

        // init
        totalColumnGrid = gridLayout.getColumnCount();
        totalRowGrid = gridLayout.getRowCount();
        state = new boolean[totalRowGrid][totalColumnGrid];

        if (totalColumnGrid <= 0)
            try {
                throw new Exception("Please set GridLayout's columnCount in XML or programmatically");
            } catch (Exception e) {
                Log.e(GridLayoutHelper.class.getSimpleName(), "Please set GridLayout's columnCount in XML or programmatically.");
            }

        // TODO: calculate base unit
        this.baseUnit = context.getResources().getDisplayMetrics().widthPixels/totalColumnGrid;
    }

    /**
     * add new grid item into grid layout
     *
     * @param row        position row
     * @param rowSpan    row size
     * @param column     position column
     * @param columnSpan column size
     */
    public void addView(int row, int rowSpan, int column, int columnSpan) {
        // create view properties
        GridItemProperties properties = new GridItemProperties(row, rowSpan, column, columnSpan);

        // check view is existed in grid layout ?
        if (gridLayout.findViewWithTag(properties.getTagFromProperties()) == null) {
            // create new view
            Button button = new Button(context);
            // set view's content
            button.setText("1");

            // row spec (which position row?, rowSpan ?), column spec (which column?, column span ?)
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row, rowSpan), GridLayout.spec(column, columnSpan));
            // base unit * width (sample: width = base_unit=50 * columnSpan=2 || height = base_unit=50 * rowSpan=1)
            params.width = baseUnit * columnSpan;
            params.height = baseUnit * rowSpan;

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

            // set tag for escape duplicate add same view
            button.setTag(properties.getTagFromProperties());

            // add to grid layout
            gridLayout.addView(button);
            Log.d(this.getClass().getName(), "not existed -> add new view");
        } else {
            Log.d(this.getClass().getName(), "view existed -> skip");
        }
    }

    /**
     * fill all empty grid items
     */
    public void fillAllEmptyItem() {
        // fill all empty items
        for (int i = 0; i < totalRowGrid; i++) {
            for (int j = 0; j < totalColumnGrid; j++) {
                // check if state = false -> add empty grid item
                if (!state[i][j]) {
                    // create view properties
                    GridItemProperties properties = new GridItemProperties(i, 1, j, 1);

                    // check view is existed in grid layout ?
                    if (gridLayout.findViewWithTag(properties.getTagFromProperties()) == null) {
                        // create new view with bordered
                        View view = new View(context);
                        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_item));
                        GridLayout.LayoutParams paramsEmpty = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));

                        // empty width , height = base unit
                        paramsEmpty.width = baseUnit;
                        paramsEmpty.height = baseUnit;
                        view.setLayoutParams(paramsEmpty);

                        // set tag for escape duplicate add same view
                        view.setTag(properties.getTagFromProperties());

                        // add to grid layout
                        gridLayout.addView(view);
                        Log.d(this.getClass().getName(), "not existed -> add new empty view");
                    } else {
                        Log.d(this.getClass().getName(), "empty view existed -> skip");
                    }
                }
            }
        }
    }

    /**
     * @return totalColumnGrid
     */
    public int getTotalColumnGrid() {
        return totalColumnGrid;
    }

    /**
     * set totalColumnGrid
     *
     * @param totalColumnGrid
     */
    public void setTotalColumnGrid(int totalColumnGrid) {
        this.totalColumnGrid = totalColumnGrid;
    }

    /**
     * @return totalRowGrid
     */
    public int getTotalRowGrid() {
        return totalRowGrid;
    }

    /**
     * set totalRowGrid
     *
     * @param totalRowGrid
     */
    public void setTotalRowGrid(int totalRowGrid) {
        this.totalRowGrid = totalRowGrid;
    }

    /**
     * @return state
     */
    public boolean[][] getState() {
        return state;
    }

    /**
     * set state
     *
     * @param state
     */
    public void setState(boolean[][] state) {
        this.state = state;
    }

    /**
     * @return itemPadding
     */
    public int getItemPadding() {
        return itemPadding;
    }

    /**
     * set itemPadding
     *
     * @param itemPadding
     */
    public void setItemPadding(int itemPadding) {
        this.itemPadding = itemPadding;
    }

    /**
     * @return baseUnit
     */
    public int getBaseUnit() {
        return baseUnit;
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
}
