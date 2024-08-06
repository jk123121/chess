package results;

public class LogoutResult
{
    private String message;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LogoutResult(String message) { setMessage(message); }
}
