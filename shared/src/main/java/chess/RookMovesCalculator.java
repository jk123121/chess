package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator
{
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        ArrayList<ChessMove> moveList = new ArrayList<>();

        //MOVE VERTICALLY
        int currRow = myPosition.getRow();

        ////UP
        for (int i = currRow + 1; i < 9; i++)
        {
            ChessPosition temp = new ChessPosition(i, myPosition.getColumn());

            //NO PIECE
            if (board.getPiece(temp) == null) moveList.add(new ChessMove(myPosition, temp));

            //INTERFERENCE
            if (board.getPiece(temp) != null)
            {
                //RUN INTO DIFF COLOR
                if (board.getPiece(temp).getTeamColor() != board.getPiece(myPosition).getTeamColor())
                {
                    moveList.add(new ChessMove(myPosition, temp));
                    break;
                }

                //RUN INTO SAME COLOR
                if (board.getPiece(temp).getTeamColor() == board.getPiece(myPosition).getTeamColor()) break;
            }
        }

        ////DOWN
        for (int i = currRow - 1; i >= 1; i--)
        {
            ChessPosition temp = new ChessPosition(i, myPosition.getColumn());

            //NO PIECE
            if (board.getPiece(temp) == null) moveList.add(new ChessMove(myPosition, temp));

            //INTERFERENCE
            if (board.getPiece(temp) != null)
            {
                //RUN INTO DIFF COLOR
                if (board.getPiece(temp).getTeamColor() != board.getPiece(myPosition).getTeamColor())
                {
                    moveList.add(new ChessMove(myPosition, temp));
                    break;
                }

                //RUN INTO SAME COLOR
                if (board.getPiece(temp).getTeamColor() == board.getPiece(myPosition).getTeamColor()) break;
            }
        }

        //MOVE HORIZONTALLY
        int currCol = myPosition.getColumn();

        ////RIGHT
        for (int i = currCol + 1; i < 9; i++)
        {
            ChessPosition temp = new ChessPosition(myPosition.getRow(), i);

            //NO PIECE
            if (board.getPiece(temp) == null) moveList.add(new ChessMove(myPosition, temp));

            //INTERFERENCE
            if (board.getPiece(temp) != null)
            {
                //RUN INTO DIFF COLOR
                if (board.getPiece(temp).getTeamColor() != board.getPiece(myPosition).getTeamColor())
                {
                    moveList.add(new ChessMove(myPosition, temp));
                    break;
                }

                //RUN INTO SAME COLOR
                if (board.getPiece(temp).getTeamColor() == board.getPiece(myPosition).getTeamColor()) break;
            }
        }

        ////LEFT
        for (int i = currCol - 1; i >= 1; i--)
        {
            ChessPosition temp = new ChessPosition(myPosition.getRow(), i);

            //NO PIECE
            if (board.getPiece(temp) == null) moveList.add(new ChessMove(myPosition, temp));

            //INTERFERENCE
            if (board.getPiece(temp) != null)
            {
                //RUN INTO DIFF COLOR
                if (board.getPiece(temp).getTeamColor() != board.getPiece(myPosition).getTeamColor())
                {
                    moveList.add(new ChessMove(myPosition, temp));
                    break;
                }

                //RUN INTO SAME COLOR
                if (board.getPiece(temp).getTeamColor() == board.getPiece(myPosition).getTeamColor()) break;
            }
        }
        return moveList;
    }
}
