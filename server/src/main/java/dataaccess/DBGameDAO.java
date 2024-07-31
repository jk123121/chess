package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.DriverManager;
import java.sql.SQLException;
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
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "one2three!@!M"))
        {
            conn.setCatalog("chess");

            try (var preparedStatement = conn.prepareStatement("SELECT * FROM chess.game WHERE gameID=?"))
            {
                preparedStatement.setString(1, String.valueOf(gameID));

                try (var result = preparedStatement.executeQuery())
                {
                    if (result.next())
                    {
                        Gson gson = new Gson();
                        return new GameData(result.getInt("gameID"),
                                            result.getString("whiteUsername"),
                                            result.getString("blackUsername"),
                                            result.getString("gameName"),
                                            gson.fromJson(result.getString("chessGame"), new ChessGame().getClass()));
                    }
                }
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<GameData> listGames()
    {
        return null;
    }
}
