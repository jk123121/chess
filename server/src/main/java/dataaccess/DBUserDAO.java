package dataaccess;

import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUserDAO implements UserDAO
{
    @Override
    public void insert(User user) throws DataAccessException
    {
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (var conn = DatabaseManager.getConnection())
        {
            conn.setCatalog("chess");

            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, email);

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete() throws DataAccessException
    {

    }

    @Override
    public void deleteAll() throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM user"))
            {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(String username) throws DataAccessException
    {
        try (var conn = DatabaseManager.getConnection())
        {
            conn.setCatalog("chess");

            try (var preparedStatement = conn.prepareStatement("SELECT * FROM user WHERE username=?"))
            {
                preparedStatement.setString(1, username);

                try (var result = preparedStatement.executeQuery())
                {
                    if (result.next())
                    {
                        int id = result.getInt("id");
                        return new User(result.getString("username"), result.getString("password"), result.getString("email"));
                    }
                    else { return null; }
                }
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
