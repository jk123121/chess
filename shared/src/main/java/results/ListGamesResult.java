package results;

import model.GameData;
import java.util.ArrayList;

public class ListGamesResult
{
    ArrayList<GameData> games;
    private String message;

    public ArrayList<GameData> getGames() { return games; }

    public void setGames(ArrayList<GameData> games) { this.games = games; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public ListGamesResult(ArrayList<GameData> games, String message)
    {
        setGames(games);
        setMessage(message);
    }

    public ListGamesResult(String message)
    {
        setMessage(message);
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        for (GameData game : games)
        {
            str.append("Game ID: " + game.getGameID() + "\n");
            str.append("Game Name: " + game.getGameName() + "\n");
            str.append("White Username: " + game.getWhiteUsername() + "\n");
            str.append("Black Username: " + game.getBlackUsername() + "\n");
            str.append("\n");
        }

        return str.toString();
    }
}
