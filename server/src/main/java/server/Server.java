package server;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import spark.*;

public class Server
{

    public int run(int desiredPort)
    {
        MemoryAuthDAO authDAO = new MemoryAuthDAO();
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();


        try
        {
            Spark.port(desiredPort);

            Spark.staticFiles.location("web");

            // Register your endpoints and handle exceptions here.
            createRoutes(authDAO, userDAO, gameDAO);

            //This line initializes the server and can be removed once you have a functioning endpoint
            //Spark.init();

            Spark.awaitInitialization();
            return Spark.port();
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException ex)
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

    private static void createRoutes(MemoryAuthDAO authDAO, MemoryUserDAO userDAO, MemoryGameDAO gameDAO)
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
}
