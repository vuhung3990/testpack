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

    /**
     * retrieve object from tag
     * @see #getTagFromProperties()
     * @param tag String tag format
     * @return GridItemProperties or throw exception if not valid format
     */
    public static GridItemProperties retrieveObjectFromTag(String tag){
        String[] retrieve = tag.split("_");
        // 4 is parameters [ String.format("%d_%d_%d_%d", row, rowSpan, column, columnSpan) ]
        if (retrieve.length != 4) throw new GridLayoutHelper.MyCustomException("can't retrive object from tag" ,"tag format not valid");
        else
            return  new GridItemProperties(Integer.parseInt(retrieve[0]), Integer.parseInt(retrieve[1]), Integer.parseInt(retrieve[2]), Integer.parseInt(retrieve[3]));
    }
}
