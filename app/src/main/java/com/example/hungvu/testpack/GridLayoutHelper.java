package com.example.hungvu.testpack;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

/**
 * quick setup my grid layout helper
 */
public class GridLayoutHelper {
    /**
     * Drag listener on each item cell
     */
    private final View.OnDragListener onDragListener;
    /**
     * screen width / totalColumnGrid
     */
    private int baseUnit = 0;

    /**
     * save context of grid layout
     */
    private final Context context;

    /**
     * default padding of item ( not empty item ) for show border
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

    /**
     * for register add event
     */
    private addViewEvent event;

    public GridLayoutHelper(GridLayout gridLayout, View.OnDragListener dragListener) {
        this.gridLayout = gridLayout;
        this.onDragListener = dragListener;

        // get context from grid layout
        context = gridLayout.getContext();

        // init
        totalColumnGrid = gridLayout.getColumnCount();
        totalRowGrid = gridLayout.getRowCount();
        state = new boolean[totalRowGrid][totalColumnGrid];

        // you have to set column and row of grid
        if (totalRowGrid <= 0 || totalColumnGrid <= 0) {
            // ============== ERROR ===============
            // Please set GridLayout's columnCount and rowCount in XML or programmatically.
            // ====================================
            throw new MyCustomException("Please set GridLayout's columnCount and rowCount in XML or programmatically.");
        } else {
            // TODO: calculate base unit
            this.baseUnit = context.getResources().getDisplayMetrics().widthPixels / totalColumnGrid;
            notifyGrid();
        }
    }

    /**
     * don't care about this class, custom throwable message
     * usage : throw new MyCustomException("Please set GridLayout's columnCount and rowCount in XML or programmatically.");
     */
    public static class MyCustomException extends RuntimeException {
        private final String message;
        private String title = "([ ERROR ])";

        /**
         * Custom throwable with message with default title
         *
         * @param message your message
         */
        public MyCustomException(String message) {
            this.message = message;
        }

        /**
         * Custom throwable with message and title
         *
         * @param message your message
         * @param title   title of message
         */
        public MyCustomException(String message, String title) {
            this.message = message;
            this.title = title;
        }

        @Override
        public String getMessage() {
            String msg = message;
            String borderString = new String(new char[msg.length() / 2]).replace("\0", "=");
            msg = "\n" + borderString + title + borderString + "\n" + msg + "\n" + borderString + new String(new char[title.length()]).replace("\0", "=") + borderString + "\n";
            return msg;
        }
    }

    /**
     * add new grid item into grid layout
     *
     * @param row        position row
     * @param rowSpan    row size
     * @param column     position column
     * @param columnSpan column size
     */
    public void addView(final int row, final int rowSpan, final int column, final int columnSpan) {
        // create view properties
        GridItemProperties properties = new GridItemProperties(row, rowSpan, column, columnSpan);

        // check valid column and row
        if ((row + rowSpan) <= totalRowGrid && (column + columnSpan) <= totalColumnGrid) {
            //  check available space to add
            boolean availableSpace = true;
            for (int i = row; i < row + rowSpan; i++) {
                for (int j = column; j < column + columnSpan; j++) {
                    // if have any invalid space => break now
                    if (state[i][j]) {
                        availableSpace = false;
                        break;
                    }
                }
            }

            if (availableSpace) {
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

                    // set drag listener
                    button.setOnDragListener(onDragListener);

                    button.setOnTouchListener(new View.OnTouchListener() {
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
                                    GridLayoutHelper.DragObject dragObject = new DragObject(columnSpan, rowSpan);
                                    // set info for re-order item
                                    dragObject.setAction(DragObject.DragUserAction.RE_ORDER, row, column);

                                    // remove view state
                                    for (int i = row; i < row + rowSpan; i++) {
                                        for (int j = column; j < column + columnSpan; j++) {
                                            state[i][j] = false;
                                        }
                                    }
                                    // update grid items
                                    notifyGrid();
                                    view.setVisibility(View.INVISIBLE);

                                    // start drag view
                                    view.startDrag(data, //data to be dragged
                                            shadowBuilder, //drag shadow
                                            dragObject, //local data about the drag and drop operation
                                            0   //no needed flags
                                    );
                                    return true;
                            }
                            return false;
                        }
                    });

                    // add to grid layout
                    gridLayout.addView(button);
                    if (event != null) event.onSuccess(properties);
                    // refresh grid layout
                    notifyGrid();
                    Log.e(this.getClass().getName(), "valid -> add new view");
                } else {
                    if (event != null) event.onDuplicate(properties);
                    Log.e(this.getClass().getName(), "view existed -> skip");
                }
            } else {
                if (event != null) event.onNotEnoughSpace(properties);
                Log.e(this.getClass().getName(), "column = " + column + " and row = " + row + " not enought space -> skip");
            }
        } else {
            if (event != null) event.onBadParameters(properties);
            Log.e(this.getClass().getName(), "column = " + column + " and row = " + row + " is not valid -> skip");
        }
    }

    /**
     * remove view item in grid layout
     *
     * @param properties for find view item
     */
    public void removeViewInGrid(GridItemProperties properties) {
        if (gridLayout != null) {
            // find view item with tag
            View view = gridLayout.findViewWithTag(properties.getTagFromProperties());
            if (view != null) {
                gridLayout.removeView(view);
            } else {
                Log.e(getClass().getSimpleName(), "", new MyCustomException("can't find view with tag: " + properties.getTagFromProperties()));
            }
        } else
            throw new MyCustomException("grid layout is null, please init before use this.");
    }

    /**
     * register add item listener
     *
     * @param event add view events
     */
    public void setOnAddItemListener(addViewEvent event) {
        this.event = event;
    }

    /**
     * add event case
     */
    public interface addViewEvent {
        /**
         * add view success
         *
         * @param properties item's properties
         */
        void onSuccess(GridItemProperties properties);

        /**
         * item is existed
         *
         * @param properties item's properties
         */
        void onDuplicate(GridItemProperties properties);

        /**
         * parameter not valid with grid layout (example: column > columnCount of grid layout...)
         *
         * @param properties item's properties
         */
        void onBadParameters(GridItemProperties properties);

        /**
         * not enough space to add view
         *
         * @param properties item's properties
         */
        void onNotEnoughSpace(GridItemProperties properties);
    }

    /**
     * fill all empty grid items
     */
    public void notifyGrid() {
        // fill all empty items
        for (int i = 0; i < totalRowGrid; i++) {
            for (int j = 0; j < totalColumnGrid; j++) {
                // create view properties
                GridItemProperties properties = new GridItemProperties(i, -1, j, -1);

                // check if state = false -> add empty grid item
                if (!state[i][j]) {
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
                        view.setTag(String.format("%d_%d_%d_%d", properties.getRow(), -1, properties.getColumn(), -1));

                        // setDragListener
                        view.setOnDragListener(onDragListener);

                        // add to grid layout
                        gridLayout.addView(view);
                        Log.e(this.getClass().getName(), "not existed -> add new empty view");
                    } else {
                        Log.e(this.getClass().getName(), "empty view existed -> skip");
                    }
                } else {
                    // remove empty if it's used
                    View view = gridLayout.findViewWithTag(properties.getTagFromProperties());
                    if (view != null)
                        gridLayout.removeView(view);
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
     * @param totalColumnGrid number of total grid layout column
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
     * @param totalRowGrid number of total grid layout row
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
     * @param state 2D array state of grid
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
     * @param itemPadding padding of insert row
     */
    public void setItemPadding(int itemPadding) {
        this.itemPadding = itemPadding;
    }

    /**
     * @return baseUnit (px)
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

    /**
     * set color hint on DragEvent.ACTION_DRAG_ENTERED and DragEvent.ACTION_DRAG_EXITED
     *
     * @param row        position row
     * @param rowSpan    row size
     * @param column     position column
     * @param columnSpan column size
     * @param isEnter    set true if call in DragEvent.ACTION_DRAG_ENTERED, false in other
     */
    public void setColorHint(int row, int rowSpan, int column, int columnSpan, boolean isEnter) {
        // check valid column and row
        if ((row + rowSpan) <= totalRowGrid && (column + columnSpan) <= totalColumnGrid) {
            //  check available space to add
            boolean availableSpace = true;
            for (int i = row; i < row + rowSpan; i++) {
                for (int j = column; j < column + columnSpan; j++) {
                    // if have any invalid space => break now
                    if (state[i][j]) {
                        availableSpace = false;
                        break;
                    }
                }
            }

            // set background
            if (availableSpace) {
                for (int i = row; i < row + rowSpan; i++) {
                    for (int j = column; j < column + columnSpan; j++) {
                        // retrieve info
                        GridItemProperties properties = new GridItemProperties(i, -1, j, -1);
                        View view = gridLayout.findViewWithTag(properties.getTagFromProperties());
                        // check view not null ( maybe unnecessary but safer )
                        if (view != null) {
                            // on DragEvent.ACTION_DRAG_ENTERED set color
                            if (isEnter) view.setBackgroundColor(Color.BLUE);
                                // on DragEvent.ACTION_DRAG_EXITED restore background
                            else
                                view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_item));
                        }
                    }
                }
            }
        }
    }

    /**
     * for send drag info
     */
    public static class DragObject {
        /**
         * action from user
         *
         * @see com.example.hungvu.testpack.GridLayoutHelper.DragObject.DragUserAction
         */
        private DragUserAction action;
        /**
         * width of drag view
         */
        private int width;
        /**
         * height of drag view
         */
        private int height;
        /**
         * column of current item grid (for re-order)
         */
        private int column;

        /**
         * column of current item grid (for re-order)
         */
        private int row;

        public enum DragUserAction {
            ADD_NEW, RE_ORDER;
        }

        /**
         * Constructor
         *
         * @param width  width of drag view
         * @param height height of drag view
         */
        public DragObject(int width, int height) {
            this.width = width;
            this.height = height;

            // default
            action = DragUserAction.ADD_NEW;
            column = -1;
            row = -1;
        }

        /**
         * @return current item row
         */
        public int getRow() {
            return row;
        }

        /**
         * set row
         *
         * @param row
         */
        public void setRow(int row) {
            this.row = row;
        }

        /**
         * @return current item column
         */
        public int getColumn() {
            return column;
        }

        /**
         * set column
         *
         * @param column
         */
        public void setColumn(int column) {
            this.column = column;
        }

        /**
         * set user action
         *
         * @param action
         */
        public void setAction(DragUserAction action, int row, int column) {
            this.action = action;
            this.row = row;
            this.column = column;
        }

        /**
         * @return user action
         */
        public DragUserAction getAction() {
            return action;
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
         * @param width item's width
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
         * @param height item's height
         */
        public void setHeight(int height) {
            this.height = height;
        }
    }
}
