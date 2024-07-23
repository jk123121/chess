package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import results.ListGamesResult;

public class ListGamesService
{
    public ListGamesResult listGames(String authToken, MemoryAuthDAO authDAO, MemoryGameDAO gameDAO)
    {
        try
        {
            if (authDAO.getUser(authToken) != null)
            {
                return new ListGamesResult(gameDAO.listGames(), null);
            }
            return new ListGamesResult("Error: unauthorized");
        } catch (DataAccessException e)
        {
            return new ListGamesResult(e.getMessage());
        }
    }
}
