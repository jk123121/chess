package client;

import exception.ResponseException;
import facade.ServerFacade;
import model.User;
import org.junit.jupiter.api.*;
import requests.CreateGameRequest;
import results.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init()
    {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void reset() throws ResponseException
    {
        facade.clearDatabase();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    void positiveRegister() throws Exception
    {
        var authData = facade.registerUser(new User("player1", "password", "p1@email.com"));
        assertTrue(authData.getAuthToken().length() > 0);
    }

    @Test
    void negativeRegister() throws Exception
    {
        RegisterResult authData = null;
        RegisterResult authData2 = null;
        try
        {
            authData = facade.registerUser(new User("player1", "password", "p1@email.com"));
            authData2 = facade.registerUser(new User("player1", "password", "p1@email.com"));
            assertTrue(false);
        } catch (ResponseException e)
        {
            assertNull(authData2);
        }
    }

    @Test
    void positiveLogin() throws Exception
    {
        facade.registerUser(new User("player1", "password", "p1@email.com"));
        LoginResult result = facade.login(new User("player1", "password", null));
        assertTrue(result.getAuthToken().length() > 0);
    }

    @Test
    void negativeLogin() throws Exception
    {
        facade.registerUser(new User("player1", "password", "p1@email.com"));
        LoginResult result = null;
        try
        {
            result = facade.login(new User("player1", "wrongpassword", null));
            assertTrue(false);
        } catch (ResponseException e)
        {
            assertNull(result);
        }
    }

    @Test
    void positiveLogout() throws Exception
    {
        var registerResult = facade.registerUser(new User("player1", "password", "p1@email.com"));
        String authtoken = registerResult.getAuthToken();
        LogoutResult result = facade.logout(authtoken);
        assertNotNull(result);
    }

    @Test
    void negativeLogout() throws Exception
    {
        LogoutResult result = null;
        try
        {
            var registerResult = facade.registerUser(new User("player1", "password", "p1@email.com"));
            String authtoken = "wrongAuthtoken";
            result = facade.logout(authtoken);
            assertTrue(false);
        } catch (ResponseException e)
        {
            assertNull(result);
        }
    }

    @Test
    void positiveCreateGame() throws Exception
    {
        var registerResult = facade.registerUser(new User("player1", "password", "p1@email.com"));
        String authtoken = registerResult.getAuthToken();
        var createGameResult = facade.createGame(authtoken, new CreateGameRequest("TestGame"));
        int gameID = createGameResult.getGameID();
        assertNotNull(gameID);
    }

    @Test
    void negativeCreateGame() throws Exception
    {
        CreateGameResult result = null;
        try
        {
            var registerResult = facade.registerUser(new User("player1", "password", "p1@email.com"));
            String authtoken = "wrongauthtoken";
            result = facade.createGame(authtoken, new CreateGameRequest("TestGame"));
            int gameID = result.getGameID();
            assertTrue(false);
        } catch (ResponseException e)
        {
            assertNull(result);
        }

    }

    @Test
    void positiveListGames() throws Exception
    {
        var registerResult = facade.registerUser(new User("player1", "password", "p1@email.com"));
        String authtoken = registerResult.getAuthToken();
        facade.createGame(authtoken, new CreateGameRequest("TestGame"));
        ListGamesResult result = facade.listGames(authtoken);
        assertNotNull(result.getGames());
    }

    @Test
    void negativeListGames() throws Exception
    {
        ListGamesResult result = null;
        try
        {
            var registerResult = facade.registerUser(new User("player1", "password", "p1@email.com"));
            String authtoken = "wrongauthtoken";
            facade.createGame(authtoken, new CreateGameRequest("TestGame"));
            result = facade.listGames(authtoken);
            assertTrue(false);
        } catch (ResponseException e)
        {
            assertNull(result);
        }

    }

    @Test
    void playGame() throws Exception
    {

    }

}
