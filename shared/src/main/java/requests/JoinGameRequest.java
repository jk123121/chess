package requests;

public class JoinGameRequest
{
    private String playerColor;
    private int gameID;

    public String getPlayerColor() { return playerColor; }
    public void setPlayerColor(String playerColor) { this.playerColor = playerColor; }

    public int getGameID() { return gameID; }
    public void setGameID(int gameID) { this.gameID = gameID; }

    public JoinGameRequest(String playerColor, int gameID)
    {
        setPlayerColor(playerColor);
        setGameID(gameID);
    }

    public JoinGameRequest(String playerColor)
    {
        setPlayerColor(playerColor);
    }
}
