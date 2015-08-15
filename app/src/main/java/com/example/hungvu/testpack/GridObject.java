package com.example.hungvu.testpack;

import android.graphics.drawable.Drawable;

/**
 * Created by Tuandv on 15-Aug-15.
 */
public class GridObject {
    private Drawable drawable;
    private int width;
    private int height;
    private int column;
    private int row;
    private String content;
    /**
     * Grid object
     * @param width grid item width
     * @param height grid item width
     */
    public GridObject(int width, int height, String content, Drawable drawable) {
        this.width = width;
        this.height = height;
        this.content = content;
        this.drawable = drawable;
    }

    /**
     * @return drawable
     */
    public Drawable getDrawable() {
        return drawable;
    }

    /**
     * set drawable
     *
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * set content
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
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
     * @return column
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
     * @return row
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
}
