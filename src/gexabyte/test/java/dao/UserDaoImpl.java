package gexabyte.test.java.dao;


import gexabyte.test.java.config.ConnectionBuilder;
import gexabyte.test.java.exception.DaoException;
import gexabyte.test.java.model.Auth;
import gexabyte.test.java.model.User;
import gexabyte.test.java.util.EncodeDecodeTool;
import gexabyte.test.java.util.SerializeDeserializeTool;

import javax.xml.bind.JAXBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoImpl implements UserDao {
    private static final String INSERT_USER = "INSERT INTO users (login,password,username,iin_number,last_name) " +
            "VALUES(?,?,?,?,?)";
    private static final String GET_USER_BY_LOGIN_PASSWORD = "SELECT * FROM users WHERE login=? AND password=?";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    @Override
    public User save(String content) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionBuilder.build();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER,
                     new String[]{"id", "login", "password", "username", "iin_number", "last_name"})) {
            user = SerializeDeserializeTool.toUser(content);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserName());
            statement.setString(4, user.getIinNumber());
            statement.setString(5, user.getLastName());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user = createUser(rs);
            }
        } catch (SQLException | JAXBException ex) {
            throw new DaoException(ex.getMessage());
        }
        return user;
    }

    @Override
    public String auth(String content) throws DaoException {
        String result = null;
        try (Connection connection = ConnectionBuilder.build();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN_PASSWORD)) {
            Auth auth = SerializeDeserializeTool.toAuth(content);
            statement.setString(1, auth.getLogin());
            statement.setString(2, auth.getPassword());
            boolean execute = statement.execute();
            if (!execute) {
                throw new DaoException("Вы не зарегестрированы");
            }
            String loginPassword = auth.getLogin() + ":" + auth.getPassword();
            result = EncodeDecodeTool.encodeString(loginPassword);
        } catch (SQLException | JAXBException ex) {
            throw new DaoException(ex.getMessage());
        }
        return result;
    }

    private User createUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setUserName("username");
        user.setIinNumber("iin_number");
        user.setLastName("last_name");
        return user;
    }

    @Override
    public User getUser(String login, String password) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionBuilder.build();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage());
        }
        return user;
    }
}
