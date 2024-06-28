package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator
{
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        ArrayList<ChessMove> moveList = new ArrayList<>();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();

        //WHITE PIECES
        if (color == ChessGame.TeamColor.WHITE)
        {
            //MOVE DIAGONAL: row + 1, col +/- 1
            //No need to check at the end because pawn will promote

            ////MOVE DIAGONAL RIGHT
            ChessPosition rightDiagonalPosition = new ChessPosition(myPosition.getRow()+1,
                    myPosition.getColumn()+1);
            ChessPiece rightDiagonalPiece = null;
            if (ChessPiece.isValidEndPosition(rightDiagonalPosition))
            {
                rightDiagonalPiece = board.getPiece(rightDiagonalPosition);
            }
            if (myPosition.getColumn() < 7 && rightDiagonalPiece != null && rightDiagonalPiece.getTeamColor() == ChessGame.TeamColor.BLACK)
            {
                moveList.add(new ChessMove(myPosition, rightDiagonalPosition));
            }

            ////MOVE DIAGONAL LEFT
            ChessPosition leftDiagonalPosition = new ChessPosition(myPosition.getRow()+1,
                    myPosition.getColumn()-1);
            ChessPiece leftDiagonalPiece = null;
            if (ChessPiece.isValidEndPosition(leftDiagonalPosition))
            {
                leftDiagonalPiece = board.getPiece(leftDiagonalPosition);
            }
            if (myPosition.getColumn() > 0 && leftDiagonalPiece != null && leftDiagonalPiece.getTeamColor() == ChessGame.TeamColor.BLACK)
            {
                moveList.add(new ChessMove(myPosition, leftDiagonalPosition));
            }

            //MOVE FORWARD
            ////START POSITION
            ChessPosition upPosition = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());

            ////MOVE 1 UP
            if(board.getPiece(upPosition) == null) moveList.add(new ChessMove(myPosition, upPosition));

            ////MOVE 2 IF POSSIBLE UP
            if (myPosition.getRow() == 2)
            {
                ChessPosition up2Position = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
                if (board.getPiece(up2Position) == null && board.getPiece(upPosition) == null) moveList.add(new ChessMove(myPosition, up2Position));
            }
        }
        //BLACK PIECES
        else
        {
            //MOVE DIAGONAL: row - 1, col +/- 1
            //No need to check at the end because pawn will promote

            ////MOVE DIAGONAL RIGHT
            ChessPosition rightDiagonalPosition = new ChessPosition(myPosition.getRow()-1,
                    myPosition.getColumn()+1);
            ChessPiece rightDiagonalPiece = null;
            if (ChessPiece.isValidEndPosition(rightDiagonalPosition))
            {
                rightDiagonalPiece = board.getPiece(rightDiagonalPosition);
            }
            if (myPosition.getColumn() < 7 && rightDiagonalPiece != null && rightDiagonalPiece.getTeamColor() == ChessGame.TeamColor.WHITE)
            {
                moveList.add(new ChessMove(myPosition, rightDiagonalPosition));
            }

            ////MOVE DIAGONAL LEFT
            ChessPosition leftDiagonalPosition = new ChessPosition(myPosition.getRow()-1,
                    myPosition.getColumn()-1);
            ChessPiece leftDiagonalPiece = null;
            if (ChessPiece.isValidEndPosition(leftDiagonalPosition))
            {
                leftDiagonalPiece = board.getPiece(leftDiagonalPosition);
            }
            if (myPosition.getColumn() > 0 && leftDiagonalPiece != null && leftDiagonalPiece.getTeamColor() == ChessGame.TeamColor.WHITE)
            {
                moveList.add(new ChessMove(myPosition, leftDiagonalPosition));
            }

            //MOVE FORWARD
            ChessPosition downPosition = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            //START POSITION
            //move 1 DOWN
            if(board.getPiece(downPosition) == null) moveList.add(new ChessMove(myPosition, downPosition));

            //move 2 if possible DOWN
            if (myPosition.getRow() == 7)
            {
                ChessPosition down2Position = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                if (board.getPiece(down2Position) == null && board.getPiece(downPosition) == null) moveList.add(new ChessMove(myPosition, down2Position));
            }
        }
        return moveList;
    }
}
