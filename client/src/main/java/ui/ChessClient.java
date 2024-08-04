package ui;


import exception.ResponseException;
import facade.ServerFacade;
import websocket.NotificationHandler;

import java.util.Arrays;

public class ChessClient
{
    private State state = State.SIGNEDOUT;
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;

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
        return "";
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
}
