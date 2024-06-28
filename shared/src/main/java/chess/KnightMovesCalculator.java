package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator
{
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        ArrayList<ChessMove> moveList = new ArrayList<>();
        ChessGame.TeamColor color = board.getPiece(myPosition).getTeamColor();

        ChessPosition temp;
        //MOVE UP LEFT: Row + 2, Col - 1
        temp = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() - 1);
        if (ChessPiece.isValidEndPosition(temp) && !ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));

        //MOVE UP RIGHT: Row + 2, Col + 1
        temp = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1);
        if (ChessPiece.isValidEndPosition(temp) && !ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));

        //MOVE DOWN LEFT: Row - 2, Col -1
        temp = new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() - 1);
        if (ChessPiece.isValidEndPosition(temp) && !ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));

        //MOVE DOWN RIGHT: Row - 2, Col + 1
        temp = new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() + 1);
        if (ChessPiece.isValidEndPosition(temp) && !ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));

        //MOVE LEFT UP: Row + 1, Col - 2
        temp = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 2);
        if (ChessPiece.isValidEndPosition(temp) && !ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));

        //MOVE LEFT DOWN: Row - 1, Col - 2
        temp = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 2);
        if (ChessPiece.isValidEndPosition(temp) && !ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));

        //MOVE RIGHT UP: Row + 1, Col + 2
        temp = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2);
        if (ChessPiece.isValidEndPosition(temp) && !ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));

        //MOVE RIGHT DOWN: Row - 1, Col + 2
        temp = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 2);
        if (ChessPiece.isValidEndPosition(temp) && !ChessPiece.isAllyPosition(board, temp, color)) moveList.add(new ChessMove(myPosition, temp));


        return moveList;
    }
}
