package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition
{
    int row;
    int column;
    public ChessPosition(int row, int col)
    {
        //TESTS ARE IN 1 BASE INDEX
        setRow(row);
        setColumn(column);
    }

    public void setRow(int rowIn) { row = rowIn; }
    public void setColumn(int columnIn) { column = columnIn; }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return column;
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition position = (ChessPosition) o;
        return row == position.row && column == position.column;
    }

    public int hashCode() { return Objects.hash(row, column); }
}
