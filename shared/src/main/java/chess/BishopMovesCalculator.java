package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator
{
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        ArrayList<ChessMove> moveList = new ArrayList<>();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();

        //MOVE UP RIGHT
        ChessPosition temp = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
        while (ChessPiece.isValidEndPosition(temp))
        {
            //IF NULL
            if (board.getPiece(temp) == null) moveList.add(new ChessMove(myPosition, temp));

                //IF ALLY
            else if (ChessPiece.isAllyPosition(board, temp, color)) break;

                //IF OPPONENT
            else
            {
                moveList.add(new ChessMove(myPosition, temp));
                break;
            }

            temp = new ChessPosition(temp.getRow() + 1, temp.getColumn() + 1);
        }

        //MOVE DOWN RIGHT
        temp = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
        while (ChessPiece.isValidEndPosition(temp))
        {
            //IF NULL
            if (board.getPiece(temp) == null) moveList.add(new ChessMove(myPosition, temp));

                //IF ALLY
            else if (ChessPiece.isAllyPosition(board, temp, color)) break;

                //IF OPPONENT
            else
            {
                moveList.add(new ChessMove(myPosition, temp));
                break;
            }

            temp = new ChessPosition(temp.getRow() - 1, temp.getColumn() + 1);
        }

        //MOVE UP LEFT
        temp = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
        while (ChessPiece.isValidEndPosition(temp))
        {
            //IF NULL
            if (board.getPiece(temp) == null) moveList.add(new ChessMove(myPosition, temp));

                //IF ALLY
            else if (ChessPiece.isAllyPosition(board, temp, color)) break;

                //IF OPPONENT
            else
            {
                moveList.add(new ChessMove(myPosition, temp));
                break;
            }

            temp = new ChessPosition(temp.getRow() + 1, temp.getColumn() - 1);
        }

        //MOVE DOWN LEFT
        temp = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
        while (ChessPiece.isValidEndPosition(temp))
        {
            //IF NULL
            if (board.getPiece(temp) == null) moveList.add(new ChessMove(myPosition, temp));

                //IF ALLY
            else if (ChessPiece.isAllyPosition(board, temp, color)) break;

                //IF OPPONENT
            else
            {
                moveList.add(new ChessMove(myPosition, temp));
                break;
            }

            temp = new ChessPosition(temp.getRow() - 1, temp.getColumn() - 1);
        }

        return moveList;
    }
}
