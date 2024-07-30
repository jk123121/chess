package service;

import chess.ChessGame;
import dataaccess.*;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import results.CreateGameResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateGameServiceTest
{
    private DBUserDAO uDAO = new DBUserDAO();
    private MemoryAuthDAO aDAO = new MemoryAuthDAO();
    private MemoryGameDAO gDAO = new MemoryGameDAO();
    private CreateGameRequest request = new CreateGameRequest("demo");

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
    void positiveCreateGame()
    {
        CreateGameResult createGame = new CreateGameService().createGame(request, "1234", aDAO, gDAO);
        assertEquals(2, createGame.getGameID());
        assertEquals(null, createGame.getMessage());
    }

    @Test
    void negativeCreateGame()
    {
        CreateGameResult createGame = new CreateGameService().createGame(request, "1236", aDAO, gDAO);
        assertEquals("Error: unauthorized", createGame.getMessage());
    }

    @AfterEach
    void tearDown()
    {
        new ClearService().clear(uDAO, aDAO, gDAO);
    }
}
