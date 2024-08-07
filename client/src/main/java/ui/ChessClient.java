package ui;


import chess.ChessBoard;
import chess.ChessPiece;
import exception.ResponseException;
import facade.ServerFacade;
import model.GameData;
import model.User;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import results.*;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.ArrayList;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class ChessClient
{
    private State state = State.SIGNEDOUT;
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    private String authtoken;
    private ArrayList<GameData> games = new ArrayList<>();
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

                //Delete previous list of games and add all games
                games.clear();
                games.addAll(server.listGames(authtoken).getGames());

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

                //Delete previous list of games and add all games
                games.clear();
                games.addAll(server.listGames(authtoken).getGames());

                //Build string for list
                StringBuilder str = new StringBuilder();

                int i = 1;
                for (GameData game : games)
                {
                    str.append("Game ID: " + i + "\n");
                    str.append("Game Name: " + game.getGameName() + "\n");
                    str.append("White Username: " + game.getWhiteUsername() + "\n");
                    str.append("Black Username: " + game.getBlackUsername() + "\n");
                    str.append("\n");
                    i++;
                }

                return str.toString() + help();
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
                int gameID = games.get(Integer.parseInt(params[0])-1).getGameID();
                JoinGameResult result = server.playGame(authtoken, new JoinGameRequest(desiredColor, gameID));

                return "Successfully joined game: " + Integer.parseInt(params[0]) + "!\n" + help() + "\n" + drawWhiteBoard(gameID) + "\n" + drawBlackBoard(gameID);
            }
        } catch (ResponseException e)
        {
            throw new ResponseException(400, "Invalid game ID or color. Try again");
        }
        throw new ResponseException(400, "Expected: playgame <gameID> <desired color>");
    }

    public String observeGame(String... params) throws ResponseException
    {
        try
        {
            if (params.length == 1)
            {
                int gameID = games.get(Integer.parseInt(params[0])-1).getGameID();
                return "Successfully observing game: " + gameID + "!\n" + help() + "\n" + drawWhiteBoard(gameID) + "\n" + drawBlackBoard(gameID);
            }
        } catch (NumberFormatException e)
        {
            throw new ResponseException(400, "Invalid game ID. Try again");
        }
        throw new ResponseException(400, "Expected: observegame <gameID>");
    }

    public String drawWhiteBoard(int gameID)
    {
        ArrayList<ArrayList<ChessPiece>> board = null;
        for (GameData game : games)
        {
            if (game.getGameID() == gameID)
            {
                board = game.getGame().getBoard().getChessArray();
            }
        }

        //Draw Board
        StringBuilder whiteBoard = new StringBuilder();
        whiteBoard.append(SET_TEXT_COLOR_WHITE + "White board:\n");
        whiteBoard.append(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + "    a  b  c  d  e  f  g  h    " + RESET_BG_COLOR + "\n");

        boolean isWhiteBg = true;
        for (int i = 7; i >= 0; i--)
        {
            whiteBoard.append(SET_BG_COLOR_BLACK + " " + (i+1) + " ");
            for (int j = 0; j < 8; j++)
            {
                //Alternate BG Colors
                if (isWhiteBg)
                {
                    whiteBoard.append(SET_BG_COLOR_LIGHT_GREY);
                }
                else
                {
                    whiteBoard.append(SET_BG_COLOR_DARK_GREY);
                }

                if (board.get(i).get(j) == null)
                {
                    whiteBoard.append("   ");
                }
                else
                {
                    switch (board.get(i).get(j).getTeamColor())
                    {
                        case WHITE:
                            whiteBoard.append(SET_TEXT_COLOR_WHITE);
                            switch(board.get(i).get(j).getPieceType())
                            {
                                case PAWN:
                                    whiteBoard.append(" P ");
                                    break;
                                case ROOK:
                                    whiteBoard.append(" R ");
                                    break;
                                case KNIGHT:
                                    whiteBoard.append(" N ");
                                    break;
                                case BISHOP:
                                    whiteBoard.append(" B ");
                                    break;
                                case QUEEN:
                                    whiteBoard.append(" Q ");
                                    break;
                                case KING:
                                    whiteBoard.append(" K ");
                                    break;
                            }
                            break;
                        case BLACK:
                            whiteBoard.append(SET_TEXT_COLOR_BLACK);
                            switch(board.get(i).get(j).getPieceType())
                            {
                                case PAWN:
                                    whiteBoard.append(" p ");
                                    break;
                                case ROOK:
                                    whiteBoard.append(" r ");
                                    break;
                                case KNIGHT:
                                    whiteBoard.append(" n ");
                                    break;
                                case BISHOP:
                                    whiteBoard.append(" b ");
                                    break;
                                case QUEEN:
                                    whiteBoard.append(" q ");
                                    break;
                                case KING:
                                    whiteBoard.append(" k ");
                                    break;
                            }
                            break;
                    }
                }
                isWhiteBg = !isWhiteBg;
            }
            isWhiteBg = !isWhiteBg;
            whiteBoard.append(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + " " + (i+1) + " " + RESET_BG_COLOR + "\n");
        }
        whiteBoard.append(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + "    a  b  c  d  e  f  g  h    " + RESET_BG_COLOR + "\n" + RESET_TEXT_COLOR);

        return whiteBoard.toString();
    }

    public String drawBlackBoard(int gameID)
    {
        ArrayList<ArrayList<ChessPiece>> board = null;
        for (GameData game : games)
        {
            if (game.getGameID() == gameID)
            {
                board = game.getGame().getBoard().getChessArray();
            }
        }

        //Draw Board
        StringBuilder blackBoard = new StringBuilder();
        blackBoard.append(SET_TEXT_COLOR_WHITE + "Black board:\n");
        blackBoard.append(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + "    h  g  f  e  d  c  b  a    " + RESET_BG_COLOR + "\n");

        boolean isWhiteBg = true;
        for (int i = 0; i < 8; i++)
        {
            blackBoard.append(SET_BG_COLOR_BLACK + " " + (i+1) + " ");
            for (int j = 7; j >= 0; j--)
            {
                //Alternate BG Colors
                if (isWhiteBg)
                {
                    blackBoard.append(SET_BG_COLOR_LIGHT_GREY);
                }
                else
                {
                    blackBoard.append(SET_BG_COLOR_DARK_GREY);
                }

                if (board.get(i).get(j) == null)
                {
                    blackBoard.append("   ");
                }
                else
                {
                    switch (board.get(i).get(j).getTeamColor())
                    {
                        case WHITE:
                            blackBoard.append(SET_TEXT_COLOR_WHITE);
                            switch(board.get(i).get(j).getPieceType())
                            {
                                case PAWN:
                                    blackBoard.append(" P ");
                                    break;
                                case ROOK:
                                    blackBoard.append(" R ");
                                    break;
                                case KNIGHT:
                                    blackBoard.append(" N ");
                                    break;
                                case BISHOP:
                                    blackBoard.append(" B ");
                                    break;
                                case QUEEN:
                                    blackBoard.append(" Q ");
                                    break;
                                case KING:
                                    blackBoard.append(" K ");
                                    break;
                            }
                            break;
                        case BLACK:
                            blackBoard.append(SET_TEXT_COLOR_BLACK);
                            switch(board.get(i).get(j).getPieceType())
                            {
                                case PAWN:
                                    blackBoard.append(" p ");
                                    break;
                                case ROOK:
                                    blackBoard.append(" r ");
                                    break;
                                case KNIGHT:
                                    blackBoard.append(" n ");
                                    break;
                                case BISHOP:
                                    blackBoard.append(" b ");
                                    break;
                                case QUEEN:
                                    blackBoard.append(" q ");
                                    break;
                                case KING:
                                    blackBoard.append(" k ");
                                    break;
                            }
                            break;
                    }
                }
                isWhiteBg = !isWhiteBg;
            }
            isWhiteBg = !isWhiteBg;
            blackBoard.append(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + " " + (i+1) + " " + RESET_BG_COLOR + "\n");
        }
        blackBoard.append(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + "    h  g  f  e  d  c  b  a    " + RESET_BG_COLOR + "\n" + RESET_TEXT_COLOR);

        return blackBoard.toString();
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
                - ObserveGame <gameID>
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
