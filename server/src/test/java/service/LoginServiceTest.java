package service;

import chess.ChessGame;
import dataaccess.*;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import results.LoginResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginServiceTest
{
    private DBUserDAO uDAO = new DBUserDAO();
    private MemoryAuthDAO aDAO = new MemoryAuthDAO();
    private MemoryGameDAO gDAO = new MemoryGameDAO();
    private LoginRequest request = new LoginRequest("ash", "pika");

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
    void positiveLoginTest() throws DataAccessException
    {
        LoginResult result = new LoginService().login(request, uDAO, aDAO);
        assertEquals("ash", result.getUsername());
    }

    @Test
    void negativeLoginTest() throws DataAccessException
    {
        request = new LoginRequest("ash", "pikachu");
        LoginResult result = new LoginService().login(request, uDAO, aDAO);
        assertEquals("Error: unauthorized", result.getMessage());
    }

    @AfterEach
    void tearDown()
    {
        new ClearService().clear(uDAO, aDAO, gDAO);
    }
}
