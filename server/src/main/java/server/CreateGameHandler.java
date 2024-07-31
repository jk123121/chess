package server;

import com.google.gson.Gson;
import dataaccess.*;
import requests.CreateGameRequest;
import results.CreateGameResult;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler
{
    public Object handle(Request req, Response res, AuthDAO authDAO, GameDAO gameDAO) throws DataAccessException {
        Gson gson = new Gson();
        CreateGameRequest request = gson.fromJson(req.body(), CreateGameRequest.class);
        String authToken = req.headers("authorization");
        CreateGameResult result = new CreateGameService().createGame(request, authToken, authDAO, gameDAO);

        if (result.getMessage() == null)
        {
            res.status(200);
            return gson.toJson(result);
        }
        if (result.getMessage() == "Error: bad request")
        {
            res.status(400);
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
