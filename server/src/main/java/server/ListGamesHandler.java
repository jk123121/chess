package server;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import results.ListGamesResult;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGamesHandler
{
    public Object handle(Request req, Response res, MemoryAuthDAO authDAO, MemoryGameDAO gameDAO)
    {
        Gson gson = new Gson();
        String authToken = req.headers("authorization");
        ListGamesResult result = new ListGamesService().listGames(authToken, authDAO, gameDAO);

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
