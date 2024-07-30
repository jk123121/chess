package service;

import chess.ChessGame;
import dataaccess.*;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import results.ClearResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearServiceTest
{
    private DBUserDAO uDAO = new DBUserDAO();
    private MemoryAuthDAO aDAO = new MemoryAuthDAO();
    private MemoryGameDAO gDAO = new MemoryGameDAO();
    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //Add new User
        uDAO.insert(new User("ash", "pika", "chu"));
        uDAO.insert(new User("notx", "pika", "chu"));
        //Add that user to authtokens + generate authtoken
        aDAO.insert(new Authtoken("1234", "ash"));
        aDAO.insert(new Authtoken("1235", "notx"));
        //Add new game
        gDAO.addGame("ash", "notx", "demoGame", new ChessGame());
    }

    @Test
    public void positiveClearTest() throws DataAccessException
    {
        ClearResult clear = new ClearService().clear(uDAO, aDAO, gDAO);
        assertEquals(null, clear.getMessage());
    }
}
