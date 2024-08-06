package results;

public class JoinGameResult
{
    private String message;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public JoinGameResult(String message)
    {
        setMessage(message);
    }
}
