package dataaccess;

import model.Authtoken;

import java.util.ArrayList;

public interface AuthDAO
{
    public void insert(Authtoken token) throws DataAccessException;

    public void delete(String username) throws DataAccessException;

    public void deleteAll() throws DataAccessException;

    public Authtoken find(String username) throws DataAccessException;

    public String getUser(String authtoken) throws DataAccessException;
}
