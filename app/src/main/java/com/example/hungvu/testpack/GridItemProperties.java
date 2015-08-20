package com.example.hungvu.testpack;

/**
 * Created by Tuandv on 19-Aug-15.
 */
public class GridItemProperties {
    private int column;
    private int row;
    private int columnSpan;
    private int rowSpan;

    public GridItemProperties(int row, int rowSpan, int column, int columnSpan) {
        this.column = column;
        this.row = row;
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
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

    /**
     * @return columnSpan
     */
    public int getColumnSpan() {
        return columnSpan;
    }

    /**
     * set columnSpan
     *
     * @param columnSpan
     */
    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    /**
     * @return rowSpan
     */
    public int getRowSpan() {
        return rowSpan;
    }

    /**
     * set rowSpan
     *
     * @param rowSpan
     */
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    /**
     * @return unique tag from properties
     */
    public String getTagFromProperties(){
        return String.format("%d_%d_%d_%d", row, rowSpan, column, columnSpan);
    }
}
