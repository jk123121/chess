package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class DBGameDAO implements GameDAO
{
    @Override
    public int addGame(String whiteUsername, String blackUsername, String gameName, ChessGame game) throws DataAccessException
    {
        return 0;
    }

    @Override
    public void endGame(int gameID) throws DataAccessException
    {

    }

    @Override
    public void delete() throws DataAccessException
    {

    }

    @Override
    public void deleteAll() throws DataAccessException
    {

    }

    @Override
    public String getWhiteUsername(int gameID) throws DataAccessException
    {
        return "";
    }

    @Override
    public void setWhiteUsername(int gameID, String username) throws DataAccessException
    {

    }

    @Override
    public String getBlackUsername(int gameID) throws DataAccessException
    {
        return "";
    }

    @Override
    public void setBlackUsername(int gameID, String username) throws DataAccessException
    {

    }

    @Override
    public GameData find(int gameID) throws DataAccessException
    {
        return null;
    }

    @Override
    public ArrayList<GameData> listGames()
    {
        return null;
    }
}
