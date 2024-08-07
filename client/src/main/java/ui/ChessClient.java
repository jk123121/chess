package ui;


import exception.ResponseException;
import facade.ServerFacade;
import model.GameData;
import model.User;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import results.*;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

public class ChessClient
{
    private State state = State.SIGNEDOUT;
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    private String authtoken;
    //private WebSocketFacade ws;

    public ChessClient(String serverUrl, NotificationHandler notificationHandler)
    {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    public String eval(String in)
    {
        try
        {
            String[] tokens = in.toLowerCase().split(" ");
            String command = (tokens.length > 0) ? tokens[0] : "help";
            String[] parameters = Arrays.copyOfRange(tokens, 1, tokens.length);

            if (state.equals(State.SIGNEDOUT))
            {
                return switch (command)
                {
                    case "login" -> login(parameters);
                    case "register" -> register(parameters);
                    case "quit" -> "quit";
                    default -> help();
                };
            }
            else
            {
                return switch (command)
                {
                    case "creategame" -> createGame(parameters);
                    case "listgames" -> listGames(parameters);
                    case "playgame" -> playGame(parameters);
                    case "observegame" -> observeGame(parameters);
                    case "logout" -> logout(parameters);
                    default -> help();
                };
            }
        } catch (ResponseException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String login(String... params) throws ResponseException
    {
        try
        {
            if (params.length == 2)
            {
                String username = params[0];
                String password = params[1];

                LoginResult result = server.login(new User(username, password, null));
                authtoken = result.getAuthToken();
                state = State.SIGNEDIN;

                return "Successfully logged in as: " + result.getUsername() + "\n" + help();
            }
        } catch (ResponseException e)
        {
            throw new ResponseException(400, "Wrong username or password. Try again");
        }
        throw new ResponseException(400, "Expected: login <username> <password>");
    }

    public String register(String... params) throws ResponseException
    {
        try
        {
            if (params.length == 3)
            {
                String username = params[0];
                String password = params[1];
                String email = params[2];

                RegisterResult result = server.registerUser(new User(username, password, email));
                authtoken = result.getAuthToken();
                state = State.SIGNEDIN;

                return "Successfully registered!\n Logged in as: " + result.getUsername() + "\n" + help();
            }
        } catch (ResponseException e)
        {
            throw new ResponseException(400, "Username is already taken. Try again");
        }

        throw new ResponseException(400, "Expected: <username> <password> <email>");
    }

    public String logout(String... params) throws ResponseException
    {
        try
        {
            if (params.length == 0)
            {
                server.logout(authtoken);
                authtoken = null;
                state = State.SIGNEDOUT;

                return "Successfully logged out!\n" + help();
            }
        } catch (ResponseException e)
        {
            throw new ResponseException(400, "Invalid Authtoken");
        }
        throw new ResponseException(400, "Expected: logout");
    }

    public String createGame(String... params) throws ResponseException
    {
        try
        {
            if (params.length == 1)
            {
                CreateGameResult result = server.createGame(authtoken, new CreateGameRequest(params[0]));
                int gameID = result.getGameID();

                return "Successfully created game: " + gameID + "!\n" + help();
            }
        } catch (ResponseException e)
        {
            throw new ResponseException(400, "Invalid game name. Try again");
        }
        throw new ResponseException(400, "Expected: creategame <gamename>");
    }

    public String listGames(String... params) throws ResponseException
    {
        try
        {
            if (params.length == 0)
            {
                ListGamesResult result = server.listGames(authtoken);
                return result.toString() + help();
            }
        } catch (ResponseException e)
        {
            throw new ResponseException(400, "Invalid game name. Try again");
        }
        throw new ResponseException(400, "Expected: creategame <gamename>");
    }

    public String playGame(String... params) throws ResponseException
    {
        try
        {
            if (params.length == 2)
            {
                String desiredColor = params[1].toUpperCase();
                JoinGameResult result = server.playGame(authtoken, new JoinGameRequest(desiredColor, Integer.parseInt(params[0])));
                return "Successfully joined game: " + Integer.parseInt(params[0]) + "!\n" + help();
            }
        } catch (ResponseException e)
        {
            throw new ResponseException(400, "Invalid game ID or color. Try again");
        }
        throw new ResponseException(400, "Expected: playgame <gameID> <desired color>");
    }

    public String observeGame(String... params) throws ResponseException
    {
        return "";
    }

    public String help() {
        if (state == State.SIGNEDOUT)
        {
            return """
                    
                    Options:
                    - Login <username> <password>
                    - Register <username> <password> <email>
                    - Help
                    - Quit
                    
                    Type the command (such as "login") followed by required fields (if indicated)
                    """;
        }
        return """
                
                Options:
                - CreateGame <game name>
                - ListGames
                - PlayGame <gameID> <desired color>
                - ObserveGame
                - Logout
                - Help
                """;
    }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }

    public ServerFacade getServer() { return server; }

    public String getServerUrl() { return serverUrl; }

    public NotificationHandler getNotificationHandler() { return notificationHandler; }

    public String getAuthtoken() { return authtoken; }
    public void setAuthtoken(String authtoken) { this.authtoken = authtoken; }
}
