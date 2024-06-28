package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator
{
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        ArrayList<ChessMove> moveList = new ArrayList<>();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();
        ChessPosition temp;

        //MOVE UP
        temp = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
        if (ChessPiece.isValidEndPosition(temp))
        {
            if(!ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));
        }

        //MOVE DOWN
        temp = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
        if (ChessPiece.isValidEndPosition(temp))
        {
            if(!ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));
        }

        //MOVE LEFT
        temp = new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1);
        if (ChessPiece.isValidEndPosition(temp))
        {
            if(!ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));
        }

        //MOVE RIGHT
        temp = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1);
        if (ChessPiece.isValidEndPosition(temp))
        {
            if(!ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));
        }

        //MOVE UP LEFT
        temp = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
        if (ChessPiece.isValidEndPosition(temp))
        {
            if(!ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));
        }

        //MOVE UP RIGHT
        temp = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
        if (ChessPiece.isValidEndPosition(temp))
        {
            if(!ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));
        }

        //MOVE DOWN LEFT
        temp = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
        if (ChessPiece.isValidEndPosition(temp))
        {
            if(!ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));
        }

        //MOVE DOWN RIGHT
        temp = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
        if (ChessPiece.isValidEndPosition(temp))
        {
            if(!ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));
        }

        return moveList;
    }
}
