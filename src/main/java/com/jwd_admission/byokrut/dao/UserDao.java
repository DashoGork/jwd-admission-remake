package com.jwd_admission.byokrut.dao;

import com.jwd_admission.byokrut.connection.ConnectionPool;
import com.jwd_admission.byokrut.dao.schemas.UserSchema;
import com.jwd_admission.byokrut.entity.PersonalInformation;
import com.jwd_admission.byokrut.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao<Integer, User> {
    private static final Logger logger = LogManager.getLogger();
    private static final String loginField = UserSchema.login.name();
    private static final String passwordField = UserSchema.password.name();
    private static final String informationIdField = UserSchema.information_id.name();
    private static final String idField = UserSchema.id.name();
    private static final String roleIdField = UserSchema.role_id.name();

    private static final String SELECT_USER_ID_BY_LOGIN = "SELECT id FROM user WHERE login=? ;";
    private static final String CREATE_USER = "INSERT INTO user" +
            "  (login, password, information_id) VALUES " +
            " (?,?,?);";
    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT id FROM user WHERE login=? AND password=?;";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id=?;";
    private static final String UPDATE_USER_BY_ID = "update user set login = ?, password= ?  where id = ?;";
    private static final String SELECT_ALL_USERS = "select * from user";
    private static final String DELETE_USER = "DELETE FROM user WHERE id=?;";
    private static final String SELECT_USER_ROLE_ID = "SELECT role_id FROM user WHERE login=? AND password=?;";
    private static final String SELECT_USER_BY_INF_ID = "SELECT * FROM user WHERE information_id=?;";

    @Override
    public User findEntityById(Integer id) {
        User user = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            String login = rs.getString(loginField);
            String password = rs.getString(passwordField);
            int infId = rs.getInt(informationIdField);
            user = new User(id, login, password, new PersonalInformation(infId));
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }


    public User findUserByInfId(Integer infId) {
        User user = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_INF_ID)) {
            preparedStatement.setInt(1, infId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            String login = rs.getString(loginField);
            String password = rs.getString(passwordField);
            int id = rs.getInt(idField);
            user = new User(id, login, password, new PersonalInformation(infId));
        } catch (SQLException e) {
            logger.error(e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(idField);
                int infId = rs.getInt(informationIdField);
                String login = rs.getString(loginField);
                String password = rs.getString(passwordField);
                users.add(new User(id, login, password, new PersonalInformation(infId)));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return users;
    }

    @Override
    public boolean delete(Integer id) {
        boolean rowDeleted = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return rowDeleted;
    }

    @Override
    public boolean create(User user) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getPersonalInformation().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            logger.error(throwables);

        }
        return false;
    }

    @Override
    public User update(User user) {
        try (
                Connection connection = ConnectionPool.INSTANCE.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getId());
            user = findEntityById(user.getId());
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return user;
    }

    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return rowUpdated;
    }


    public int findUserId(User user) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_ID_BY_LOGIN)) {
            statement.setString(1, user.getLogin());
            statement.execute();
            ResultSet res = statement.getResultSet();
            res.next();
            return (res.getInt(idField));
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return -1;
    }


    public int findUserByLoginAndPassword(User user) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) return resultSet.getInt(idField);
            else return -1;
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return -1;
    }


    public int findUserRoleId(User user) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ROLE_ID)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
            ResultSet res = preparedStatement.getResultSet();
            res.next();
            return (res.getInt(roleIdField));
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return -1;
    }
}

