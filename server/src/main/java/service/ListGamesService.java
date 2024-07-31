package service;

import dataaccess.*;
import results.ListGamesResult;

public class ListGamesService
{
    public ListGamesResult listGames(String authToken, AuthDAO authDAO, GameDAO gameDAO)
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
