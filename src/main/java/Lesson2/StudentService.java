package Lesson2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentService {

    // 1 запрос 1 connection тут же закрывать.
    // execute ничего не возвращает.
    // execute
    public List<Student> findAll() {
        Connection connection = DriverManagerApp.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                studentList.add(new Student(resultSet.getLong("id")
                        , resultSet.getString("name")
                        , resultSet.getString("name"))
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DriverManagerApp.close(connection);
        }
        return Collections.emptyList();
    }

    // хорошая обертка для возможно пустых данных
    public Optional<Student> findById(long id) {

        Connection connection = DriverManagerApp.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students where id = ?");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return Optional.of(new Student(resultSet.getLong("id")
                        , resultSet.getString("name")
                        , resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DriverManagerApp.close(connection);
        }
        return Optional.empty();
    }

    public void save(Student student) {

    }

    public void delete(Student student) {

    }

}
