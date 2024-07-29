package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO
{
    ArrayList<GameData> games = new ArrayList<>();

    public MemoryGameDAO() {}

    public int addGame(String whiteUsername, String blackUsername, String gameName, ChessGame game) throws DataAccessException
    {
        int gameID = games.size() + 1;
        games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
        return gameID;
    }

    public void endGame(int gameID) throws DataAccessException
    {

    }

    public void delete() throws DataAccessException
    {
    }

    public void deleteAll() throws DataAccessException { games.clear(); }

    public String getWhiteUsername(int gameID) throws DataAccessException
    {
        GameData game = find(gameID);
        return game.getWhiteUsername();
    }

    public void setWhiteUsername(int gameID, String username) throws DataAccessException
    {
        GameData game = find(gameID);
        game.setWhiteUsername(username);
    }

    public String getBlackUsername(int gameID) throws DataAccessException
    {
        GameData game = find(gameID);
        return game.getBlackUsername();
    }

    public void setBlackUsername(int gameID, String username) throws DataAccessException
    {
        GameData game = find(gameID);
        game.setBlackUsername(username);
    }

    public GameData find(int gameID) throws DataAccessException
    {
        for (GameData game : games)
        {
            if (game.getGameID() == gameID)
            {
                return game;
            }
        }
        return null;
    }

    public ArrayList<GameData> listGames ()
    {
        ArrayList<GameData> gameList = new ArrayList<>();
        for (GameData game : games)
        {
            gameList.add(new GameData(game.getGameID(), game.getWhiteUsername(), game.getBlackUsername(), game.getGameName()));
        }
        return gameList;
    }
}
