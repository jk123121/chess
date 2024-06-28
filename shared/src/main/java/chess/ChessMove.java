package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove
{
    ChessPosition startPosition;
    ChessPosition endPosition;
    ChessPiece.PieceType promotionPiece = null;
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece)
    {
        setStartPosition(startPosition);
        setEndPosition(endPosition);
        setPromotionPiece(promotionPiece);
    }

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition)
    {
        setStartPosition(startPosition);
        setEndPosition(endPosition);
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    public void setStartPosition(ChessPosition startPosition) { this.startPosition = startPosition; }

    public void setEndPosition(ChessPosition endPosition) { this.endPosition = endPosition; }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece()
    {
        //System.out.println("Which piece do you want?");
        //Scanner scanner = new Scanner(System.in);
        //ChessPiece.PieceType piece = ChessPiece.PieceType.valueOf(scanner.nextLine());
        //if (piece == ChessPiece.PieceType.PAWN) piece = null;
        return promotionPiece;
    }

    public void setPromotionPiece(ChessPiece.PieceType promo) { promotionPiece = promo; }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMove move = (ChessMove) o;
        return Objects.equals(startPosition, move.startPosition) && Objects.equals(endPosition, move.endPosition);
    }

    public int hashCode() { return Objects.hash(startPosition, endPosition); }

    public String toString()
    {
        StringBuilder build = new StringBuilder();
        build.append("Start Position: " + startPosition.getRow() + ", " + startPosition.getColumn() + "\n");
        build.append("End Position: " + endPosition.getRow() + ", " + endPosition.getColumn() + "\n");

        return build.toString();
    }
}
