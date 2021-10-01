package Lesson2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerApp{
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","1234");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            throw new NullPointerException("Нет данных");
        }
    }
    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
