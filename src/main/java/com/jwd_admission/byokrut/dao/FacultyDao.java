package com.jwd_admission.byokrut.dao;


import com.jwd_admission.byokrut.connection.ConnectionPool;
import com.jwd_admission.byokrut.dao.schemas.FacultySchema;
import com.jwd_admission.byokrut.dao.schemas.FacultySubjectSchema;
import com.jwd_admission.byokrut.entity.Faculty;
import com.jwd_admission.byokrut.entity.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyDao extends BaseDao<Integer, Faculty> {
    private static final Logger logger = LogManager.getLogger();

    private static final String numberOfStudentsFromSchema= FacultySchema.number_of_students.name();
    private static final String nameFromSchema=FacultySchema.name.name();
    private static final String subjectIdFromSchema= FacultySubjectSchema.subject_id.name();

    private static final String SELECT_ALL_FACULTUES = "SELECT * FROM faculty";
    private static final String SELECT_ALL_FACULTUES_ID = "SELECT id FROM faculty";
    private static final String SELECT_FACULTY_BY_ID_FROM_FACULTY = "SELECT * FROM faculty WHERE id=?";
    private static final String SELECT_SUBJECT_ID_NAME_BY_FACULTY_ID_FROM_FAC_SUB = "select name, subject_id from subject inner join faculty_subject fs on subject.id = fs.subject_id where faculty_id=?";

    @Override
    public Faculty findEntityById(Integer id) {
        Faculty faculty = new Faculty(id);
//        ArrayList<Subject> subjects = findSubjectInfo(id);
        faculty.setSubjects(findSubjectInfo(id));
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FACULTY_BY_ID_FROM_FACULTY)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int numberOfStudents = rs.getInt(numberOfStudentsFromSchema);
            String name = rs.getString(nameFromSchema);
            faculty.setName(name);
            faculty.setNumberOfStudents(numberOfStudents);
        } catch (SQLException e) {
            logger.error(e);
        }
        return faculty;
    }

    ArrayList<Subject> findSubjectInfo(Integer facultyId) {
        ArrayList<Subject> subjects = new ArrayList<>(3);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SUBJECT_ID_NAME_BY_FACULTY_ID_FROM_FAC_SUB)) {
            preparedStatement.setInt(1, facultyId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int subjectId = rs.getInt(subjectIdFromSchema);
                String name = rs.getString(nameFromSchema);
                subjects.add(new Subject(subjectId, name));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return subjects;
    }

    @Override
    public List findAll() {
        List<Faculty> faculties = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FACULTUES)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                faculties.add(findEntityById(id));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return faculties;
    }

    @Override
    public boolean create(Faculty faculty) {
        return false;
    }

    @Override
    public Faculty update(Faculty faculty) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}

