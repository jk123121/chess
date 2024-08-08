package facade;

import com.google.gson.Gson;
import exception.ResponseException;
import model.GameData;
import model.User;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import results.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerFacade
{
    private final String serverUrl;

    public ServerFacade(String serverUrl) { this.serverUrl = serverUrl; }

    public RegisterResult registerUser(User user) throws ResponseException
    {
        var path = "/user";
        return this.makeRequest("POST", path, null, user, RegisterResult.class);
    }

    public LoginResult login(User user) throws ResponseException
    {
        var path = "/session";
        return this.makeRequest("POST", path, null, user, LoginResult.class);
    }

    public LogoutResult logout(String authtoken) throws ResponseException
    {
        var path = "/session";
        LogoutResult result = this.makeRequest("DELETE", path, authtoken, null, LogoutResult.class);
        return result;
    }

    public CreateGameResult createGame(String authtoken, CreateGameRequest req) throws ResponseException
    {
        var path = "/game";
        return this.makeRequest("POST", path, authtoken, req, CreateGameResult.class);
    }

    public ListGamesResult listGames(String authtoken) throws ResponseException
    {
        var path = "/game";
        return this.makeRequest("GET", path, authtoken, null, ListGamesResult.class);
    }

    public JoinGameResult playGame(String authtoken, JoinGameRequest req) throws ResponseException
    {
        var path = "/game";
        return this.makeRequest("PUT", path, authtoken, req, JoinGameResult.class);
    }

    public ClearResult clearDatabase() throws ResponseException
    {
        var path = "/db";
        return this.makeRequest("DELETE", path, null, null, ClearResult.class);
    }

    private <T> T makeRequest(String method, String path, String authtoken, Object request, Class<T> responseClass) throws ResponseException
    {
        try
        {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeHeader(authtoken, http);
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex)
        {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static void writeHeader(String authtoken, HttpURLConnection http) throws IOException
    {
        if (authtoken != null)
        {
            http.setRequestProperty("authorization", authtoken);
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException
    {
        if (request != null)
        {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);

            try (OutputStream reqBody = http.getOutputStream())
            {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException
    {
        var status = http.getResponseCode();

        if (!isSuccessful(status))
        {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException
    {
        T response = null;
        if (http.getContentLength() < 0)
        {
            try (InputStream respBody = http.getInputStream())
            {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) { return status / 100 == 2; }
}
