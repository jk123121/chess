package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDAO
{
    public int addGame(String whiteUsername, String blackUsername, String gameName, ChessGame game) throws DataAccessException;

    public void endGame(int gameID) throws DataAccessException;

    public void delete() throws DataAccessException;

    public void deleteAll() throws DataAccessException;

    public String getWhiteUsername(int gameID) throws DataAccessException;

    public void setWhiteUsername(int gameID, String username) throws DataAccessException;

    public String getBlackUsername(int gameID) throws DataAccessException;

    public void setBlackUsername(int gameID, String username) throws DataAccessException;

    public GameData find(int gameID) throws DataAccessException;

    public ArrayList<GameData> listGames ();
}
