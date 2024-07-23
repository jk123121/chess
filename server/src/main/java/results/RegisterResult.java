package results;

public class RegisterResult
{
    private String username;
    private String authToken;
    private String message;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAuthToken() { return authToken; }
    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public RegisterResult(String username, String authToken, String message)
    {
        setUsername(username);
        setAuthToken(authToken);
        setMessage(message);
    }
}
