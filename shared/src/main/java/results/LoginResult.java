package results;

public class LoginResult
{
    private String username;
    private String authToken;
    private String message;

    public String getAuthToken() { return authToken; }
    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LoginResult(String username, String authToken, String message)
    {
        setAuthToken(authToken);
        setUsername(username);
        setMessage(message);
    }
}
