package service;

import chess.ChessGame;
import dataaccess.*;
import requests.CreateGameRequest;
import results.CreateGameResult;

public class CreateGameService
{
    public CreateGameResult createGame(CreateGameRequest request, String authToken, AuthDAO authDAO, GameDAO gameDAO)
    {
        String gameName = request.getGameName();

        try
        {
            if (authDAO.getUser(authToken) != null)
            {
                if (gameName == null || gameName.equals(""))
                {
                    return new CreateGameResult("Error: bad request");
                }

                ChessGame game = new ChessGame();
                game.getBoard().resetBoard();
                Integer gameID = gameDAO.addGame(null, null, gameName, game);
                return new CreateGameResult(gameID, null);
            }
            return new CreateGameResult(null, "Error: unauthorized");
        } catch (DataAccessException e)
        {
            return new CreateGameResult(null, e.getMessage());
        }
    }
}
