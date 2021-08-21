package com.jwd_admission.byokrut.dao;

import com.jwd_admission.byokrut.connection.ConnectionPool;
import com.jwd_admission.byokrut.dao.schemas.RequestSchema;
import com.jwd_admission.byokrut.entity.FacultyName;
import com.jwd_admission.byokrut.entity.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDao extends BaseDao<Integer, Request> {
    private static final String userIdField = RequestSchema.user_id.name();
    private static final String requestIdField = RequestSchema.id.name();
    private static final String approvedField = RequestSchema.approved.name();
    private static final String scoreField = RequestSchema.score.name();
    private static final String facultyIdField = RequestSchema.faculty_id.name();


    private static final Logger logger = LogManager.getLogger();
    private static final String CREATE_REQUEST = "INSERT INTO request (score, user_id,faculty_id) VALUES(?,?,?);";
    private static final String APPROVE_REQUEST = "UPDATE request SET approved=1 where user_id=?;";
    private static final String SELECT_REQUEST_BY_USER_ID = "SELECT * FROM request WHERE user_id=?";
    private static final String SELECT_ALL_REQUESTS = "SELECT * FROM request ;";
    private static final String UPDATE_REQUEST = "UPDATE request set score=?,faculty_id=? where user_id=?";
    private static final String DELETE_REQUEST_BY_USER_ID = "DELETE FROM request WHERE user_id=?;";
    private static final String SELECT_REQUESTS = "select user_id,score from request where faculty_id=? AND approved=1 order by score DESC LIMIT ?;";
    private static FacultyDao facultyDao=new FacultyDao();

    @Override
    public Request findEntityById(Integer id) {
        Request request = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REQUEST_BY_USER_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int requestId = rs.getInt(requestIdField);
            int facultyId = rs.getInt(facultyIdField);
            int score = rs.getInt(scoreField);
            int approved = rs.getInt(approvedField);
            int userId = rs.getInt(userIdField);
            request = new Request(requestId, facultyId, userId, score, approved);
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return request;
    }

    @Override
    public List<Request> findAll() {
        List<Request> requests = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_REQUESTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(requestIdField);
                int facultyId = rs.getInt(facultyIdField);
                int userId = rs.getInt(userIdField);
                int score = rs.getInt(scoreField);
                int approved = rs.getInt(approvedField);
                requests.add(new Request(id, facultyId, userId, score, approved));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return requests;
    }

    public List<Request> findAllPassed(FacultyName facultyName) {
        List<Request> requests = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REQUESTS)) {
            preparedStatement.setInt(1, facultyName.getId());
            preparedStatement.setInt(2, facultyDao.findEntityById(facultyName.getId()).getNumberOfStudents());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt(userIdField);
                int score = rs.getInt(scoreField);
                requests.add(new Request(facultyName.getId(), userId, score));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return requests;
    }


    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REQUEST_BY_USER_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }

    @Override
    public boolean create(Request request) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_REQUEST)) {
            preparedStatement.setInt(1, request.getScore());
            preparedStatement.setInt(2, request.getUserId());
            preparedStatement.setInt(3, request.getFacultyId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }

    @Override
    public Request update(Request request) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REQUEST)) {
            preparedStatement.setInt(1, request.getScore());
            preparedStatement.setInt(2, request.getFacultyId());
            preparedStatement.setInt(3, request.getUserId());
            preparedStatement.executeUpdate();
            request = findEntityById(request.getUserId());
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return request;
    }

    public boolean approve(int userId) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(APPROVE_REQUEST)) {
            preparedStatement.setInt(1, userId);
            int result = preparedStatement.executeUpdate();
            return (result == 1);
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }

    public Request findRequestByUser(int userId) {
        Request request = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REQUEST_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(requestIdField);
                int facultyId = rs.getInt(facultyIdField);
                int score = rs.getInt(scoreField);
                int approved = rs.getInt(approvedField);
                request = (new Request(id, facultyId, score, approved));
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return request;
    }
}
