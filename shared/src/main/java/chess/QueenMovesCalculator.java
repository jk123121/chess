package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator
{
    public static Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        ArrayList<ChessMove> moveList = new ArrayList<>();

        moveList.addAll(RookMovesCalculator.pieceMoves(board, myPosition));
        moveList.addAll(BishopMovesCalculator.pieceMoves(board, myPosition));

        return moveList;
    }
}
