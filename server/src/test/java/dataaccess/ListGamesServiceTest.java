package dataaccess;

import chess.ChessGame;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import results.ListGamesResult;
import service.ClearService;
import service.ListGamesService;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListGamesServiceTest
{
    private UserDAO uDAO = new DBUserDAO();
    private AuthDAO aDAO = new DBAuthDAO();
    private GameDAO gDAO = new DBGameDAO();

    @BeforeEach
    void setUp() throws DataAccessException
    {
        uDAO.insert(new User("ash", "pika", "chu"));
        uDAO.insert(new User("notx", "pika", "chu"));
        //Add that user to authtokens + generate authtoken
        aDAO.insert(new Authtoken("1234", "ash"));
        aDAO.insert(new Authtoken("1235", "notx"));
        //Add new game
        gDAO.addGame("ash", "notx", "demoGame", new ChessGame());
    }

    @Test
    void positiveListGamesTest() throws DataAccessException {
        ListGamesResult result = new ListGamesService().listGames("1234", aDAO, gDAO);
        assertArrayEquals(gDAO.listGames().toArray(), result.getGames().toArray());
    }

    @Test
    void negativeListGamesTest()
    {
        ListGamesResult result = new ListGamesService().listGames("1236", aDAO, gDAO);
        assertEquals("Error: unauthorized", result.getMessage());
    }

    @AfterEach
    void tearDown()
    {
        new ClearService().clear(uDAO, aDAO, gDAO);
    }
}
