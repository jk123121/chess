package server;

import com.google.gson.Gson;
import dataaccess.*;
import results.ClearResult;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler
{
    public Object handle(Request req, Response res, UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO)
    {
        Gson gson = new Gson();
        ClearResult result = new ClearService().clear(userDAO, authDAO, gameDAO);

        if (result.getMessage() == null)
        {
            res.status(200);
            return gson.toJson(result);
        }
        res.status(500);
        return gson.toJson(result);
    }
}
