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
    private UserDAO uDAO = new DBUserDAO();
    private AuthDAO aDAO = new DBAuthDAO();
    private GameDAO gDAO = new DBGameDAO();
    private CreateGameRequest request = new CreateGameRequest("demo");
    int gameID;

    @BeforeEach
    void setUp() throws DataAccessException
    {
        uDAO.insert(new User("ash", "pika", "chu"));
        uDAO.insert(new User("notx", "pika", "chu"));
        //Add that user to authtokens + generate authtoken
        aDAO.insert(new Authtoken("1234", "ash"));
        aDAO.insert(new Authtoken("1235", "notx"));
        //Add new game
        gameID = gDAO.addGame("ash", "notx", "demoGame", new ChessGame());
    }

    @Test
    void positiveCreateGame()
    {
        CreateGameResult createGame = new CreateGameService().createGame(request, "1234", aDAO, gDAO);
        assertEquals(gameID + 1, createGame.getGameID());
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
