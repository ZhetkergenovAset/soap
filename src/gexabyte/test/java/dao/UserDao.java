package gexabyte.test.java.dao;


import gexabyte.test.java.exception.DaoException;
import gexabyte.test.java.model.User;

public interface UserDao {

    User save(String content) throws DaoException;

    String auth(String content) throws DaoException;

    User getUser(String login,String password) throws DaoException;

}
