package chess;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard
{
    ArrayList<ArrayList<ChessPiece>> chessArray;
    public ChessBoard()
    {
        chessArray = new ArrayList<>(8);
        for (int i = 0; i < 8; i++)
        {
            ArrayList<ChessPiece> row = new ArrayList<>(8);
            for (int j = 0; j < 8; j++)
            {
                row.add(null);
            }
            chessArray.add(row);
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece)
    {
        chessArray.get(position.getRow()-1).set(position.getColumn()-1, piece);
    }

    public void removePiece(ChessPosition position)
    {
        chessArray.get(position.getRow()-1).set(position.getColumn()-1, null);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position)
    {
        ChessPiece piece = (ChessPiece) chessArray.get(position.getRow()-1).get(position.getColumn()-1);
        return piece;
    }

    public ArrayList<ArrayList<ChessPiece>> getChessArray() { return chessArray; }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard()
    {
        chessArray = new ArrayList<>(8);
        for (int i = 0; i < 8; i++)
        {
            ArrayList<ChessPiece> row = new ArrayList<>(8);
            for (int j = 0; j < 8; j++)
            {
                row.add(null);
            }
            chessArray.add(row);
        }

        //insert blacks
        insertRoyals(ChessGame.TeamColor.BLACK);
        insertPawns(ChessGame.TeamColor.BLACK);

        //insert whites
        insertRoyals(ChessGame.TeamColor.WHITE);
        insertPawns(ChessGame.TeamColor.WHITE);
    }

    public void insertRoyals(ChessGame.TeamColor color)
    {
        int row = 0;
        if(color == ChessGame.TeamColor.BLACK) row = 7;
        chessArray.get(row).set(0, new ChessPiece(color, ChessPiece.PieceType.ROOK));
        chessArray.get(row).set(1, new ChessPiece(color, ChessPiece.PieceType.KNIGHT));
        chessArray.get(row).set(2, new ChessPiece(color, ChessPiece.PieceType.BISHOP));
        chessArray.get(row).set(3, new ChessPiece(color, ChessPiece.PieceType.QUEEN));
        chessArray.get(row).set(4, new ChessPiece(color, ChessPiece.PieceType.KING));
        chessArray.get(row).set(5, new ChessPiece(color, ChessPiece.PieceType.BISHOP));
        chessArray.get(row).set(6, new ChessPiece(color, ChessPiece.PieceType.KNIGHT));
        chessArray.get(row).set(7, new ChessPiece(color, ChessPiece.PieceType.ROOK));
    }

    public void insertPawns(ChessGame.TeamColor color)
    {
        int row = 1;
        if (color == ChessGame.TeamColor.BLACK) row = 6;
        for (int i = 0; i < 8; i++)
        {
            chessArray.get(row).set(i, new ChessPiece(color, ChessPiece.PieceType.PAWN));
        }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 7; i >= 0; i--)
        {
            builder.append("|");
            for (int j = 0; j < 8; j++)
            {
                if (chessArray.get(i).get(j) == null)
                {
                    builder.append(" ");
                }
                else
                {
                    switch (chessArray.get(i).get(j).getTeamColor())
                    {
                        case WHITE:
                            switch(chessArray.get(i).get(j).getPieceType())
                            {
                                case PAWN:
                                    builder.append("P");
                                    break;
                                case ROOK:
                                    builder.append("R");
                                    break;
                                case KNIGHT:
                                    builder.append("N");
                                    break;
                                case BISHOP:
                                    builder.append("B");
                                    break;
                                case QUEEN:
                                    builder.append("Q");
                                    break;
                                case KING:
                                    builder.append("K");
                                    break;
                            }
                            break;
                        case BLACK:
                            switch(chessArray.get(i).get(j).getPieceType())
                            {
                                case PAWN:
                                    builder.append("p");
                                    break;
                                case ROOK:
                                    builder.append("r");
                                    break;
                                case KNIGHT:
                                    builder.append("n");
                                    break;
                                case BISHOP:
                                    builder.append("b");
                                    break;
                                case QUEEN:
                                    builder.append("q");
                                    break;
                                case KING:
                                    builder.append("k");
                                    break;
                            }
                            break;
                    }
                }
                builder.append("|");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.equals(chessArray, that.chessArray);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(chessArray);
    }
}
