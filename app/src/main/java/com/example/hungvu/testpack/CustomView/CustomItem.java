package com.example.hungvu.testpack.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.example.hungvu.testpack.R;

/**
 * Created by Tuandv on 18-Aug-15.
 */
public class CustomItem extends FrameLayout {
    private ItemObject itemObject = null;
    private int base_unit;

    public CustomItem(Context context) {
        super(context);
        init(null);
    }

    public CustomItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(null);
    }

    public CustomItem(Context context, ItemObject itemObject){
        super(context);
        init(itemObject);
    }

    private void init(ItemObject itemObject) {
        View view = inflate(getContext(), R.layout.item, this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setGravity(Gravity.FILL);

        // calculate base nit
        base_unit = 50;

        if(itemObject != null){
            // save item object
            setItemObject(itemObject);

            params.columnSpec = GridLayout.spec(itemObject.getPosition().x, itemObject.getWidth()); // start = position.x, size = width
            params.rowSpec = GridLayout.spec(itemObject.getPosition().y, itemObject.getHeight());
        }

        // set layout params
        view.setLayoutParams(params);
    }

    /**
     * @return itemObject
     */
    public ItemObject getItemObject() {
        return itemObject;
    }

    /**
     * set itemObject
     *
     * @param itemObject
     */
    public void setItemObject(ItemObject itemObject) {
        this.itemObject = itemObject;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ItemObject object = getItemObject();
        setMeasuredDimension(base_unit * getItemObject().getWidth(), base_unit * getItemObject().getHeight());
    }
}
