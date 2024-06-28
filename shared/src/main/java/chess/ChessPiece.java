package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece
{
    PieceType type;
    ChessGame.TeamColor color;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type)
    {
        this.type = type;
        this.color = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType
    {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    public void setPieceColor(ChessGame.TeamColor color) { this.color = color; }
    public void setTeamType(PieceType type) { this.type = type; }

    public static boolean isValidEndPosition(ChessPosition positionIn)
    {
        if (positionIn.getRow() >= 1 && positionIn.getRow() < 9 &&
                positionIn.getColumn() >= 1 && positionIn.getColumn() < 9) return true;
        return false;
    }

    public static boolean isAllyPosition(ChessBoard board, ChessPosition posistionIn, ChessGame.TeamColor color)
    {
        if (isValidEndPosition(posistionIn) && board.getPiece(posistionIn) != null)
        {
            if (board.getPiece(posistionIn).getTeamColor() == color) return true;
        }
        return false;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        if (type == PieceType.PAWN) { return PawnMovesCalculator.pieceMoves(board, myPosition); }
        if (type == PieceType.ROOK) { return RookMovesCalculator.pieceMoves(board, myPosition); }
        if (type == PieceType.KNIGHT) { return KnightMovesCalculator.pieceMoves(board, myPosition); }
        if (type == PieceType.BISHOP) { return BishopMovesCalculator.pieceMoves(board, myPosition); }
        return null;
    }
}
