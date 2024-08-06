package ui;


import exception.ResponseException;
import facade.ServerFacade;
import model.User;
import results.RegisterResult;
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

            return switch (command)
            {
                case "login" -> login(parameters);
                case "register" -> register(parameters);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String login(String... params) throws ResponseException
    {
        if (params.length == 2)
        {
            String username = params[0];
            String password = params[1];
        }
        return "";
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
            return "";
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public String help() {
        if (state == State.SIGNEDOUT)
        {
            return """
                    - Login <username> <password>
                    - Register <username> <password> <email>
                    - Help
                    - Quit
                    
                    Type the command (such as "login") followed by required fields (if indicated)
                    """;
        }
        return """
                - CreateGame
                - ListGame
                - PlayGame
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
