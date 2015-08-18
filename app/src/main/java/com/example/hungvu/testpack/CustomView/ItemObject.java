package com.example.hungvu.testpack.CustomView;

import android.graphics.Point;

/**
 * Created by Tuandv on 18-Aug-15.
 */
public class ItemObject {
    private int width;
    private int height;
    private Point position;

    public ItemObject(int width, int height, Point position) {
        this.width = width;
        this.height = height;
        this.position = position;
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

    /**
     * @return position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * set position
     *
     * @param position
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}
