package learn.chess;

public class Knight {

    int row;
    int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean move(int row, int column) {
        if (row < 0 || row > 7 ||
                column < 0 || column > 7 ||
                (row == this.row && column == this.column)
        ) { return false; }

        int differenceInRows = Math.abs(row - this.row);
        int differenceInColumns = Math.abs(column - this.column);

        if ((differenceInRows == 2 && differenceInColumns == 3)
                || (differenceInRows == 3 && differenceInColumns == 2)) {
            this.row = row;
            this.column = column;
            return true;
        }

        return false;
    }
}
