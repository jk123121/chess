package service;

import dataaccess.*;
import results.ClearResult;

public class ClearService
{
    public ClearResult clear(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO)
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
