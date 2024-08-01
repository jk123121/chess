package server;

import dataaccess.*;
import spark.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Server
{

    public int run(int desiredPort)
    {
        AuthDAO authDAO = new DBAuthDAO();
        UserDAO userDAO = new DBUserDAO();
        GameDAO gameDAO = new DBGameDAO();


        try
        {
            Spark.port(desiredPort);

            Spark.staticFiles.location("web");

            // Register your endpoints and handle exceptions here.
            createRoutes(authDAO, userDAO, gameDAO);

            //This line initializes the server and can be removed once you have a functioning endpoint
            //Spark.init();

            prepareTables();
            Spark.awaitInitialization();
            return Spark.port();
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException | SQLException | DataAccessException ex)
        {
            System.err.println("Specify the port number as a command line parameter");
        }

        return 0;
    }

    public void stop()
    {
        Spark.stop();
        Spark.awaitStop();
    }

    private static void createRoutes(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO)
    {
        Spark.get("/", (req, res) -> null);
        Spark.post("/user", (req, res) -> new RegisterHandler().handle(req, res, userDAO, authDAO));
        Spark.post("/session", (req, res) -> new LoginHandler().handle(req, res, userDAO, authDAO));
        Spark.delete("/session", (req, res) -> new LogoutHandler().handle(req, res, authDAO));
        Spark.delete("/db", (req, res) -> new ClearHandler().handle(req, res, userDAO, authDAO, gameDAO));
        Spark.get("/game", (req, res) -> new ListGamesHandler().handle(req, res, authDAO, gameDAO));
        Spark.post("/game", (req, res) -> new CreateGameHandler().handle(req, res, authDAO, gameDAO));
        Spark.put("/game", (req, res) -> new JoinGameHandler().handle(req, res, authDAO, gameDAO));
    }

    private void prepareTables() throws SQLException, DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = getConnection())
        {
            var createUserTable = """
                    CREATE TABLE IF NOT EXISTS user
                    (
                        id INT NOT NULL AUTO_INCREMENT,
                        username varchar(32) NOT NULL,
                        password varchar(72) NOT NULL,
                        email varchar(32) NOT NULL,
                        PRIMARY KEY (id),
                        INDEX (username)
                    );""";
            var createAuthTable = """
                    CREATE TABLE IF NOT EXISTS authtoken
                    (
                        id INT NOT NULL AUTO_INCREMENT,
                        username varchar(32) NOT NULL,
                        authToken varchar(36) NOT NULL,
                        PRIMARY KEY (id),
                        INDEX (username)
                    );""";
            var createGameTable = """
                    CREATE TABLE IF NOT EXISTS gamedata
                    (
                        gameid INT NOT NULL AUTO_INCREMENT,
                        whiteusername varchar(32) NULL,
                        blackusername varchar(32) NULL,
                        gamename varchar(32) NOT NULL,
                        chessgame varchar(1500) NOT NULL,
                        PRIMARY KEY (gameID),
                        INDEX (gameName)
                    );""";

            try (var createTableStatement = conn.prepareStatement(createUserTable))
            {
                createTableStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement(createAuthTable))
            {
                createTableStatement.executeUpdate();
            }
            try (var createTableStatement = conn.prepareStatement(createGameTable))
            {
                createTableStatement.executeUpdate();
            }
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    Connection getConnection() throws DataAccessException
    {
        return DatabaseManager.getConnection();
    }
}
