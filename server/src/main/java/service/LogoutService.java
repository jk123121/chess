package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import requests.LogoutRequest;
import results.LogoutResult;

public class LogoutService
{
    public LogoutResult logout(LogoutRequest request, AuthDAO authDAO) throws DataAccessException {
        String authtoken = request.getToken();

        try
        {
            String username = authDAO.getUser(authtoken);
            if (username != null)
            {
                authDAO.delete(username);
                return new LogoutResult(null);
            }
            return new LogoutResult("Error: unauthorized");
        } catch (DataAccessException e)
        {
            return new LogoutResult(e.getMessage());
        }
    }
}
