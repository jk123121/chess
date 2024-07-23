package results;

public class CreateGameResult
{
    private Integer gameID;
    private String message;

    public int getGameID() { return gameID; }
    public void setGameID(Integer gameID) { this.gameID = gameID; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public CreateGameResult(Integer gameID, String message)
    {
        setGameID(gameID);
        setMessage(message);
    }

    public CreateGameResult(String message)
    {
        setMessage(message);
    }
}
