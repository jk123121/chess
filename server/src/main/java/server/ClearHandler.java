package server;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import results.ClearResult;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler
{
    public Object handle(Request req, Response res, MemoryUserDAO userDAO, MemoryAuthDAO authDAO, MemoryGameDAO gameDAO)
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
