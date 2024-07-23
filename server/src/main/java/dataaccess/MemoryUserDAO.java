package dataaccess;

import model.User;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO
{
    ArrayList<User> users = new ArrayList<>();

    public MemoryUserDAO() {}

    public void insert(User user) throws DataAccessException
    {
        users.add(user);
    }

    public void delete() throws DataAccessException
    {

    }

    public void deleteAll() throws DataAccessException { users.clear(); }

    public User find(String username) throws DataAccessException
    {
        for (User user : users)
        {
            if (user.getUsername().equals(username))
            {
                return user;
            }
        }
        return null;
    }
}
