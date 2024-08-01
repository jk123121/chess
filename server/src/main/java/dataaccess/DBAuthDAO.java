package dataaccess;

import model.Authtoken;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAuthDAO implements AuthDAO
{
    @Override
    public void insert(Authtoken token) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO authtoken (username, authtoken) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setString(1, token.getUsername());
                preparedStatement.setString(2, token.getAuthToken());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String username) throws DataAccessException
    {
        Authtoken token = find(username);
        if (token != null)
        {
            try (var conn = DatabaseManager.getConnection())
            {
                try (var preparedStatement = conn.prepareStatement("DELETE FROM authtoken WHERE authtoken=?"))
                {
                    preparedStatement.setString(1, token.getAuthToken());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteAll() throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM authtoken"))
            {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Authtoken find(String username) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM authtoken WHERE username=? LIMIT 1"))
            {
                preparedStatement.setString(1, username);

                try (var result = preparedStatement.executeQuery())
                {
                    if (result.next())
                    {
                        //int id = result.getInt("id");
                        return new Authtoken(result.getString("authtoken"), result.getString("username"));
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
    public String getUser(String authtoken) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM authtoken WHERE authtoken=?"))
            {
                preparedStatement.setString(1, authtoken);

                try (var result = preparedStatement.executeQuery())
                {
                    if (result.next())
                    {
                        //int id = result.getInt("id");
                        return result.getString("username");
                    }
                }
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }
}
