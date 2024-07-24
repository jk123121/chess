package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.JoinGameRequest;
import results.JoinGameResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinGameServiceTest
{
    private MemoryUserDAO uDAO = new MemoryUserDAO();
    private MemoryAuthDAO aDAO = new MemoryAuthDAO();
    private MemoryGameDAO gDAO = new MemoryGameDAO();
    private JoinGameRequest request = new JoinGameRequest("WHITE", 1);

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
    void positiveJoinGameTest()
    {
        JoinGameResult joinGame = new JoinGameService().joinGame(request, "1234", aDAO, gDAO);
        assertEquals(null, joinGame.getMessage());
    }

    @Test
    void negativeJoinGameTest()
    {
        JoinGameResult joinGame = new JoinGameService().joinGame(request, "1236", aDAO, gDAO);
        assertEquals("Error: unauthorized", joinGame.getMessage());
    }

    @AfterEach
    void tearDown()
    {
        new ClearService().clear(uDAO, aDAO, gDAO);
    }
}
