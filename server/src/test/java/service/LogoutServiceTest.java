package service;

import chess.ChessGame;
import dataaccess.*;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LogoutRequest;
import results.LogoutResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutServiceTest
{
    private DBUserDAO uDAO = new DBUserDAO();
    private MemoryAuthDAO aDAO = new MemoryAuthDAO();
    private MemoryGameDAO gDAO = new MemoryGameDAO();
    private LogoutRequest request = new LogoutRequest("1234");

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
    void positiveLogoutTest() throws DataAccessException
    {
        LogoutResult result = new LogoutService().logout(request, aDAO);
        assertEquals(null, result.getMessage());
    }

    @Test
    void negativeLogoutTest() throws DataAccessException
    {
        request = new LogoutRequest("1236");
        LogoutResult result = new LogoutService().logout(request, aDAO);
        assertEquals("Error: unauthorized", result.getMessage());
    }

    @AfterEach
    void tearDown()
    {
        new ClearService().clear(uDAO, aDAO, gDAO);
    }
}
