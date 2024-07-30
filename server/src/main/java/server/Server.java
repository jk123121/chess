package server;

import dataaccess.DBUserDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import spark.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Server
{

    public int run(int desiredPort)
    {
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        DBUserDAO userDAO = new DBUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();


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
        catch(ArrayIndexOutOfBoundsException | NumberFormatException | SQLException ex)
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

    private static void createRoutes(MemoryAuthDAO authDAO, DBUserDAO userDAO, MemoryGameDAO gameDAO)
    {
        Spark.get("/", (req, res) -> null);
        Spark.post("/user", (req, res) -> new RegisterHandler().handle(req, res, userDAO, authDAO));
        Spark.post("/session", (req, res) -> new LoginHandler().handle(req, res, userDAO, authDAO));
        Spark.delete("/session", (req, res) -> new LogoutHandler().handle(req, res, authDAO));
        //Spark.delete("/db", (req, res) -> new ClearHandler().handle(req, res, userDAO, authDAO, gameDAO));
        Spark.get("/game", (req, res) -> new ListGamesHandler().handle(req, res, authDAO, gameDAO));
        Spark.post("/game", (req, res) -> new CreateGameHandler().handle(req, res, authDAO, gameDAO));
        Spark.put("/game", (req, res) -> new JoinGameHandler().handle(req, res, authDAO, gameDAO));
    }

    private void prepareTables() throws SQLException
    {
        try (var conn = getConnection())
        {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
            createDbStatement.executeUpdate();

            conn.setCatalog("chess");

            var createUserTable = """
                    CREATE TABLE IF NOT EXISTS User
                    (
                        id INT NOT NULL AUTO_INCREMENT,
                        username varchar(32) NOT NULL,
                        password varchar(72) NOT NULL,
                        email varchar(32) NOT NULL,
                        PRIMARY KEY (id),
                        INDEX (username)
                    );""";
            var createAuthTable = """
                    CREATE TABLE IF NOT EXISTS AuthToken
                    (
                        id INT NOT NULL AUTO_INCREMENT,
                        Username varchar(32) NOT NULL,
                        authToken varchar(32) NOT NULL,
                        PRIMARY KEY (id),
                        INDEX (username)
                    );""";
            var createGameTable = """
                    CREATE TABLE IF NOT EXISTS GameData
                    (
                        gameID varchar(32) NOT NULL,
                        whiteUsername varchar(32) NOT NULL,
                        blackUsername varchar(32) NOT NULL,
                        gameName varchar(32) NOT NULL,
                        game varchar(32) NOT NULL
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
        }
    }

    Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "one2three!@!M");
    }
}
