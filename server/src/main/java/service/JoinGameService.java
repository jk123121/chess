package service;

import dataaccess.*;
import requests.JoinGameRequest;
import results.JoinGameResult;

public class JoinGameService
{
    public JoinGameResult joinGame(JoinGameRequest request, String authToken, AuthDAO authDAO, GameDAO gameDAO)
    {
        String color = request.getPlayerColor();
        int gameID = request.getGameID();

        try
        {
            String username = authDAO.getUser(authToken);

            if (username == null) { return new JoinGameResult("Error: unauthorized"); }

            if (gameDAO.find(gameID) == null)
            {
                return new JoinGameResult("Error: bad request");
            }

            if (color == null || color.equals("")) { return new JoinGameResult("Error: bad request"); }

            if ((color.equals("WHITE") &&
                    gameDAO.getWhiteUsername(gameID) != null &&
                    !gameDAO.getWhiteUsername(gameID).equals(username))
                    ||
                    (color.equals("BLACK") &&
                            gameDAO.getBlackUsername(gameID) != null &&
                            !gameDAO.getBlackUsername(gameID).equals(username)))
            {
                return new JoinGameResult("Error: already taken");
            }

            if (color.equals("WHITE") &&
                    (gameDAO.getWhiteUsername(gameID) == null || gameDAO.getWhiteUsername(gameID).equals(username)))
            {
                gameDAO.setWhiteUsername(gameID, username);
            }
            if (color.equals("BLACK") &&
                    (gameDAO.getBlackUsername(gameID) == null || gameDAO.getBlackUsername(gameID).equals(username)))
            {
                gameDAO.setBlackUsername(gameID, username);
            }
            return new JoinGameResult(null);
        } catch (DataAccessException e)
        {
            return new JoinGameResult(e.getMessage());
        }
    }
}
