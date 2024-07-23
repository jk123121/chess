package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import requests.LogoutRequest;
import results.LogoutResult;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler
{
    public Object handle(Request req, Response res, MemoryAuthDAO authDAO) throws DataAccessException
    {
        Gson gson = new Gson();
        String authToken = req.headers("authorization");
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutResult result = new LogoutService().logout(request, authDAO);

        if (result.getMessage() == null)
        {
            res.status(200);
            return gson.toJson(result);
        }
        if (result.getMessage() == "Error: unauthorized")
        {
            res.status(401);
            return gson.toJson(result);
        }
        res.status(500);
        return gson.toJson(result);
    }
}
