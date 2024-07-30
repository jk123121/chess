package service;

import dataaccess.DBUserDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.Authtoken;
import model.User;
import requests.RegisterRequest;
import results.RegisterResult;

import java.util.UUID;

public class RegisterService
{
    public RegisterResult register(RegisterRequest request, DBUserDAO userDAO, MemoryAuthDAO authDAO)
    {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        if (username == null || username.equals("") || password == null || password.equals("") || email == null || email.equals(""))
        {
            return new RegisterResult(null, null, "Error: bad request");
        }

        try
        {
            if (userDAO.find(username) != null)
            {
                return new RegisterResult(null, null, "Error: already taken");
            }
            UUID uuid = UUID.randomUUID();
            userDAO.insert(new User(username, password, email));
            authDAO.insert(new Authtoken(uuid.toString(), username));
            return new RegisterResult(username, uuid.toString(), null);
        } catch (DataAccessException e)
        {
            return new RegisterResult(null, null, e.getMessage());
        }
    }
}
