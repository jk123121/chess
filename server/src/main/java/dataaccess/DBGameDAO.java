package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBGameDAO implements GameDAO
{
    @Override
    public int addGame(String whiteUsername, String blackUsername, String gameName, ChessGame game) throws DataAccessException
    {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "one2three!@!M"))
        {
            conn.setCatalog("chess");

            try (var preparedStatement = conn.prepareStatement("INSERT INTO chess.gamedata (whiteusername, blackusername, gamename, chesgame) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setString(1, whiteUsername);
                preparedStatement.setString(2, blackUsername);
                preparedStatement.setString(3, gameName);
                preparedStatement.setString(4, new Gson().toJson(game));

                preparedStatement.executeUpdate();

                return preparedStatement.getGeneratedKeys().getInt("gameID");
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
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
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "one2three!@!M"))
        {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM chess.authtoken"))
            {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getWhiteUsername(int gameID) throws DataAccessException
    {
        GameData game = find(gameID);
        return game.getWhiteUsername();
    }

    @Override
    public void setWhiteUsername(int gameID, String username) throws DataAccessException
    {
        GameData game = find(gameID);
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "one2three!@!M"))
        {
            conn.setCatalog("chess");

            try (var preparedStatement = conn.prepareStatement("UPDATE chess.authtoken SET whiteUsername=? WHERE id=?"))
            {
                preparedStatement.setString(1, game.getWhiteUsername());
                preparedStatement.setInt(2, game.getGameID());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBlackUsername(int gameID) throws DataAccessException
    {
        GameData game = find(gameID);
        return game.getBlackUsername();
    }

    @Override
    public void setBlackUsername(int gameID, String username) throws DataAccessException
    {
        GameData game = find(gameID);
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "one2three!@!M"))
        {
            conn.setCatalog("chess");

            try (var preparedStatement = conn.prepareStatement("UPDATE chess.authtoken SET blackUsername=? WHERE id=?"))
            {
                preparedStatement.setString(1, game.getBlackUsername());
                preparedStatement.setInt(2, game.getGameID());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
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
        ArrayList<GameData> gameList = new ArrayList<>();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "one2three!@!M"))
        {
            conn.setCatalog("chess");

            try (var preparedStatement = conn.prepareStatement("SELECT * FROM chess.game"))
            {
                try (var result = preparedStatement.executeQuery())
                {
                    while (result.next())
                    {
                        Gson gson = new Gson();
                        gameList.add(new GameData(result.getInt("gameID"),
                                result.getString("whiteUsername"),
                                result.getString("blackUsername"),
                                result.getString("gameName"),
                                gson.fromJson(result.getString("chessGame"), new ChessGame().getClass())));
                    }
                    return gameList;
                }
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
