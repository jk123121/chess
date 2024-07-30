package service;

import dataaccess.DBUserDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.Authtoken;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import results.LoginResult;

import java.util.UUID;

public class LoginService
{
    public LoginResult login (LoginRequest request, DBUserDAO userDAO, MemoryAuthDAO authDAO) throws DataAccessException {
        String username = request.getUsername();
        String password = request.getPassword();
        User user;
        try
        {
            if (userDAO.find(username) != null)
            {
                user = userDAO.find(username);
                if (BCrypt.checkpw(password, user.getPassword()))
                {
                    UUID uuid = UUID.randomUUID();
                    authDAO.insert(new Authtoken(uuid.toString(), username));
                    return new LoginResult(username, uuid.toString(), null);
                }
            }
            return new LoginResult(null, null, "Error: unauthorized");
        } catch (DataAccessException e)
        {
            return new LoginResult(null, null, e.getMessage());
        }
    }
}
