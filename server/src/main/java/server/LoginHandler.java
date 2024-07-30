package server;

import com.google.gson.Gson;
import dataaccess.DBUserDAO;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import requests.LoginRequest;
import results.LoginResult;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler
{
    public Object handle(Request req, Response res, DBUserDAO userDAO, MemoryAuthDAO authDAO) throws DataAccessException
    {
        Gson gson = new Gson();
        LoginRequest request = gson.fromJson(req.body(), LoginRequest.class);
        LoginResult result = new LoginService().login(request, userDAO, authDAO);

        if (result.getMessage() == "Error: unauthorized")
        {
            res.status(401);
            return gson.toJson(result);
        }
        if (result.getAuthToken() != null)
        {
            res.status(200);
            return gson.toJson(result);
        }
        res.status(500);
        return gson.toJson(result);
    }
}
