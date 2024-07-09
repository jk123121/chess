package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame
{
    TeamColor turn;
    ChessBoard board;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() { return turn; }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) { turn = team; }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor
    {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition)
    {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) return null;

        ArrayList<ChessMove> pieceMoves = (ArrayList<ChessMove>) piece.pieceMoves(board, startPosition);
        pieceMoves.removeIf((move) -> isInCheckPeek(move));

        return pieceMoves;
    }

    private boolean isInCheckPeek(ChessMove move)
    {
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();

        ChessPiece startPiece = board.getChessArray().get(startPos.getRow() - 1).get(startPos.getColumn() - 1);
        ChessPiece endPiece = board.getChessArray().get(endPos.getRow() - 1).get(endPos.getColumn() - 1);

        //temp add piece at end position
        board.getChessArray().get(endPos.getRow() - 1).set(endPos.getColumn() - 1,
                new ChessPiece(startPiece.getTeamColor(), startPiece.getPieceType()));

        //Temp remove piece at start position
        board.getChessArray().get(startPos.getRow() - 1).set(startPos.getColumn() - 1, null);

        boolean result = isInCheck(startPiece.getTeamColor());

        //Revert changes to piece positions
        board.getChessArray().get(startPos.getRow() - 1).set(startPos.getColumn() - 1, startPiece);
        board.getChessArray().get(endPos.getRow() - 1).set(endPos.getColumn() - 1, endPiece);

        return result;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException
    {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        ArrayList<ChessMove> moveList = (ArrayList<ChessMove>) validMoves(move.getStartPosition());
        if (moveList.isEmpty())
        {
            throw new InvalidMoveException();
        }
        boolean moveCheck = moveList.contains(move);
        if (getTeamTurn() != piece.getTeamColor() || !moveCheck) throw new InvalidMoveException();
        movePiece(move.getStartPosition(), move.getEndPosition());

        //if pawn reaches end getPromotionPiece();
        if (((piece.getTeamColor() == TeamColor.WHITE) &&
                (piece.getPieceType() == ChessPiece.PieceType.PAWN) &&
                (move.getEndPosition().getRow() == 8))
                ||
                ((piece.getTeamColor() == TeamColor.BLACK) &&
                        (piece.getPieceType() == ChessPiece.PieceType.PAWN) &&
                        (move.getEndPosition().getRow() == 1)))
        {
            board.removePiece(move.getEndPosition());
            board.addPiece(move.getEndPosition(), createPiece(move.getPromotionPiece(), piece.getTeamColor()));
        }

        TeamColor enemyColor = getTeamTurn() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
        setTeamTurn(enemyColor);
    }

    private ChessPiece createPiece(ChessPiece.PieceType type, TeamColor color)
    {
        return new ChessPiece(color, type);
    }

    public void movePiece(ChessPosition startPosition, ChessPosition endPosition)
    {
        ChessPiece startPiece = (ChessPiece) board.getChessArray().get(startPosition.getRow()-1).get(startPosition.getColumn()-1);
        ChessPiece endPiece = (ChessPiece) board.getChessArray().get(endPosition.getRow()-1).get(endPosition.getColumn()-1);
        ChessPiece.PieceType type = startPiece.getPieceType();

        if (endPiece == null)
        {
            relocate(type, endPosition, startPiece);
            board.removePiece(startPosition);
        }
        else if (endPiece.getTeamColor() != startPiece.getTeamColor())
        {
            board.removePiece(endPosition);
            relocate(type, endPosition, startPiece);
            board.removePiece(startPosition);
        }
        else System.out.println("Cannot move to that location");
    }

    public void relocate(ChessPiece.PieceType type, ChessPosition endPosition, ChessPiece startPiece)
    {
        board.addPiece(endPosition, new ChessPiece(startPiece.getTeamColor(), startPiece.getPieceType()));
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor)
    {
        ArrayList<ArrayList<ChessPiece>> chessArray = board.getChessArray();
        ChessPosition kingPosition = null;

        //FIND TEAM'S KING
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                ChessPiece piece = (ChessPiece) chessArray.get(i).get(j);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor)
                {
                    kingPosition = new ChessPosition(i + 1, j + 1);
                    break;
                }
            }
        }

        if (kingPosition == null) { return false; }

        //FIND ENEMY'S MOVES
        TeamColor enemyColor = teamColor == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
        ArrayList<ChessMove> enemyMoves = getTeamMoves(enemyColor);
        for (ChessMove move : enemyMoves)
        {
            if ((kingPosition.getRow() == move.getEndPosition().getRow()) && (kingPosition.getColumn() == move.getEndPosition().getColumn()))
            {
                return true;
            }
        }
        return false;
    }

    private ArrayList<ChessMove> getTeamMoves(TeamColor teamColor)
    {
        ArrayList<ChessMove> teamMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                ChessPiece piece = board.getChessArray().get(i).get(j);
                if (piece != null && piece.getTeamColor() == teamColor)
                {
                    teamMoves.addAll(piece.pieceMoves(board, new ChessPosition(i + 1, j + 1)));
                }
            }
        }
        return teamMoves;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor)
    {
        //Is not in check to start
        if (!isInCheck(teamColor))
        {
            //Iterate through all teamcolor's pieces &get piece's moves and iterate moving to each spot
            ArrayList<ChessMove> teamMoves = getTeamMoves(teamColor);

            ////check if any moves in list is in check
            for (ChessMove move : teamMoves)
            {
                if (!isInCheckPeek(move)) { return false; }
            }
            return true;
        }
        //Iterate through all teamcolor's pieces &get piece's moves and iterate moving to each spot
        ArrayList<ChessMove> teamMoves = getTeamMoves(teamColor);

        ////check if any moves in list is in check
        for (ChessMove move : teamMoves)
        {
            if (!isInCheckPeek(move)) { return false; }
        }

        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor)
    {
        ArrayList<ChessMove> allMoves = new ArrayList<>();
        ChessPosition position = null;

        //FIND TEAM'S POSITIONS
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.getChessArray().get(i).get(j) != null && board.getChessArray().get(i).get(j).getTeamColor() == teamColor)
                {
                    allMoves.addAll(validMoves(new ChessPosition(i + 1, j + 1)));
                }
            }
        }
        if (allMoves.isEmpty()) { return true; }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) { this.board = board; }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() { return board; }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return turn == chessGame.turn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() { return Objects.hash(turn, board); }
}