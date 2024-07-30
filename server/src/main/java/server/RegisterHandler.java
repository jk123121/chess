package server;

import com.google.gson.Gson;
import dataaccess.DBUserDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import requests.RegisterRequest;
import results.RegisterResult;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler
{
    public Object handle(Request req, Response res, DBUserDAO userDAO, MemoryAuthDAO authDAO)
    {
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(req.body(), RegisterRequest.class);
        RegisterResult result = new RegisterService().register(request, userDAO, authDAO);

        if (result.getMessage() == "Error: already taken")
        {
            res.status(403);
            return gson.toJson(result);
        }
        if (result.getMessage() == "Error: bad request")
        {
            res.status(400);
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
