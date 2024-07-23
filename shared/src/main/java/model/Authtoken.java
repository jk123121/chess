package model;

import java.util.Objects;

public class Authtoken
{
    private String authToken;
    private String username;

    public String getAuthToken() { return authToken; }
    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Authtoken(String authToken, String username)
    {
        setAuthToken(authToken);
        setUsername(username);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtoken authtoken = (Authtoken) o;
        return Objects.equals(authToken, authtoken.authToken) && Objects.equals(username, authtoken.username);
    }

    @Override
    public int hashCode() { return Objects.hash(authToken, username); }
}
