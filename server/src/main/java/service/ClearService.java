package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import results.ClearResult;

public class ClearService
{
    public ClearResult clear(MemoryUserDAO userDAO, MemoryAuthDAO authDAO, MemoryGameDAO gameDAO)
    {
        try
        {
            userDAO.deleteAll();
            authDAO.deleteAll();
            gameDAO.deleteAll();
            return new ClearResult(null);
        } catch (DataAccessException e)
        {
            return new ClearResult(e.getMessage());
        }
    }
}
