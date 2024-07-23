package dataaccess;

import model.User;

import java.util.ArrayList;

public interface UserDAO
{
    public void insert(User user) throws DataAccessException;

    public void delete() throws DataAccessException;

    public void deleteAll() throws DataAccessException;

    public User find(String username) throws DataAccessException;
}
