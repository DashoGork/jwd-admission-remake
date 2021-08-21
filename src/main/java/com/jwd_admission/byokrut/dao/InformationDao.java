package com.jwd_admission.byokrut.dao;

import com.jwd_admission.byokrut.connection.ConnectionPool;
import com.jwd_admission.byokrut.dao.schemas.InformationSchema;
import com.jwd_admission.byokrut.entity.PersonalInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InformationDao extends BaseDao<Integer, PersonalInformation> {
    private static final Logger logger = LogManager.getLogger();
    private static final String nameField = InformationSchema.name.name();
    private static final String middlenameField = InformationSchema.middlename.name();
    private static final String lastnameField = InformationSchema.lastname.name();
    private static final String passportIdField = InformationSchema.passport_id.name();
    private static final String idField = InformationSchema.id.name();

    private static final String SELECT_INFORMATION_ID_BY_PASSPORT_ID = "SELECT id FROM information WHERE passport_id=?;";
    private static final String CREATE_USER_INF = "INSERT INTO information (name, lastname," +
            "middlename, passport_id) VALUES (?,?,?,?);";
    private static final String SELECT_ALL_INF = "SELECT * FROM information";
    private static final String SELECT_INF_BY_ID = "SELECT * FROM information WHERE id=?";
    private static final String DELETE_USER_INF_BY_ID = "DELETE FROM information WHERE id=?;";
    private static final String UPDATE_USER_INF_BY_ID = "UPDATE information SET name=?,lastname=?,middlename=?, passport_id=?" +
            " WHERE id=?;";

    @Override
    public PersonalInformation findEntityById(Integer id) {
        PersonalInformation personalInformation = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INF_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            String name = rs.getString(nameField);
            String middlename = rs.getString(middlenameField);
            String lastname = rs.getString(lastnameField);
            String passportId = rs.getString(passportIdField);
            int infId = rs.getInt(idField);
            personalInformation = new PersonalInformation(infId, name, middlename, lastname, passportId);
        } catch (SQLException e) {
            logger.error(e);
        }
        return personalInformation;
    }


    @Override
    public List<PersonalInformation> findAll() {
        List<PersonalInformation> personalInformation = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_INF)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString(nameField);
                String middlename = rs.getString(middlenameField);
                String lastname = rs.getString(lastnameField);
                String passportId = rs.getString(passportIdField);
                int infId = rs.getInt(idField);
                personalInformation.add(new PersonalInformation(infId, name, middlename, lastname, passportId));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return personalInformation;
    }


    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_INF_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }


    public boolean create(PersonalInformation personalInformation) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(CREATE_USER_INF)) {
            preparedStatement1.setString(1, personalInformation.getFirstName());
            preparedStatement1.setString(2, personalInformation.getMiddleName());
            preparedStatement1.setString(3, personalInformation.getLastName());
            preparedStatement1.setString(4, personalInformation.getPassportId());
            preparedStatement1.executeUpdate();
            personalInformation.setId(findIdByPassportId(personalInformation.getPassportId()));
            return true;
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return false;
    }

    public PersonalInformation update(PersonalInformation personalInformation) {
        try (
                Connection connection = ConnectionPool.INSTANCE.getConnection(); PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_USER_INF_BY_ID)) {
            preparedStatement1.setString(1, personalInformation.getFirstName());
            preparedStatement1.setString(2, personalInformation.getLastName());
            preparedStatement1.setString(3, personalInformation.getMiddleName());
            preparedStatement1.setString(4, personalInformation.getPassportId());
            preparedStatement1.setInt(5, personalInformation.getId());
            preparedStatement1.executeUpdate();
            personalInformation = (findEntityById(personalInformation.getId()));
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return personalInformation;
    }


    public int findIdByPassportId(String passportId) {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INFORMATION_ID_BY_PASSPORT_ID)) {
            preparedStatement.setString(1, passportId);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            return ((resultSet.next()) ? resultSet.getInt(idField) : -1);
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return -1;
    }
}
