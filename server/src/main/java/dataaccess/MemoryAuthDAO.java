package dataaccess;

import model.Authtoken;

import java.util.ArrayList;

public class MemoryAuthDAO implements AuthDAO
{
    ArrayList<Authtoken> authTokens = new ArrayList<>();

    public MemoryAuthDAO() {}

    public void insert(Authtoken token) throws DataAccessException
    {
        for (int i = 0; i < authTokens.size(); i++)
        {
            if (authTokens.get(i).getUsername().equals(token.getUsername()))
            {
                authTokens.set(i, token);
                return;
            }
        }
        authTokens.add(token);
    }

    public void delete(String username) throws DataAccessException
    {
        Authtoken token = find(username);
        if (token != null) { authTokens.remove(token); }
    }

    public void deleteAll() throws DataAccessException { authTokens.clear(); }

    public Authtoken find(String username) throws DataAccessException
    {
        for (Authtoken token : authTokens)
        {
            if (token.getUsername().equals(username))
            {
                return token;
            }
        }
        return null;
    }

    public String getUser(String authtoken) throws DataAccessException
    {
        for (Authtoken token : authTokens)
        {
            if (token.getAuthToken().equals(authtoken))
            {
                return token.getUsername();
            }
        }
        return null;
    }
}
