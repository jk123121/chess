package requests;

public class CreateGameRequest
{
    private String gameName;

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public CreateGameRequest(String gameName) { setGameName(gameName); }
}
