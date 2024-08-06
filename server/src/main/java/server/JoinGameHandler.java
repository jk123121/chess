package server;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import requests.JoinGameRequest;
import results.JoinGameResult;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler
{
    public Object handle(Request req, Response res, AuthDAO authDAO, GameDAO gameDAO)
    {
        Gson gson = new Gson();
        String authToken = req.headers("authorization");
        JoinGameRequest request = gson.fromJson(req.body(), JoinGameRequest.class);
        JoinGameResult result = new JoinGameService().joinGame(request, authToken, authDAO, gameDAO);

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
        if (result.getMessage() == "Error: already taken")
        {
            res.status(403);
            return gson.toJson(result);
        }
        res.status(500);
        return gson.toJson(result);
    }
}
