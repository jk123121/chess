package requests;

public class LogoutRequest
{
    String token;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public LogoutRequest(String token)
    {
        setToken(token);
    }
}
