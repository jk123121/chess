package dataaccess;

import model.Authtoken;

public class DBAuthDAO implements AuthDAO
{
    @Override
    public void insert(Authtoken token) throws DataAccessException
    {

    }

    @Override
    public void delete(String username) throws DataAccessException
    {

    }

    @Override
    public void deleteAll() throws DataAccessException
    {

    }

    @Override
    public Authtoken find(String username) throws DataAccessException
    {
        return null;
    }

    @Override
    public String getUser(String authtoken) throws DataAccessException
    {
        return "";
    }
}
