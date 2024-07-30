package service;

import chess.ChessGame;
import dataaccess.*;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterServiceTest
{
    private DBUserDAO uDAO = new DBUserDAO();
    private MemoryAuthDAO aDAO = new MemoryAuthDAO();
    private MemoryGameDAO gDAO = new MemoryGameDAO();
    private RegisterRequest request = new RegisterRequest("noty", "notypassword", "noty@noty.com");

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
    void positiveRegisterTest()
    {
        RegisterResult result = new RegisterService().register(request, uDAO, aDAO);
        assertEquals("noty", result.getUsername());
    }

    @Test
    void negativeRegisterTest()
    {
        request = new RegisterRequest("ash", "pika", "chu");
        RegisterResult result = new RegisterService().register(request, uDAO, aDAO);
        assertEquals("Error: already taken", result.getMessage());
    }

    @AfterEach
    void tearDown()
    {
        new ClearService().clear(uDAO, aDAO, gDAO);
    }
}
