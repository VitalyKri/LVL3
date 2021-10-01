package Lesson2.Homework.OnlineChat.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthService {

    public static Connection getConnect() {
        // обращение к драйверу, для его инициализации
        try {
            Class.forName("org.sqlite.JDBC"); // инициализация драйвера
            return DriverManager.getConnection("jdbc:sqlite:src/main/java/Lesson2/Homework/OnlineChat/main.db"); // инициализация соединения
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Not fount connection.");
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        Connection connection = getConnect();
        try {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT nickname, password FROM users WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            // 106438208
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect(connection);
        }
        throw new NullPointerException("Не верный пароль");
    }

    public List<String> getBlackList(String userInBlackList) {
        List<String> nickList = new ArrayList<>();
        String sql = String.format("SELECT user_nickname,\n" +
                "       userBL_nickname\n" +
                "  FROM blackList\n" +
                "  where userBL_nickname = '%s'", userInBlackList);
        Connection connection = getConnect();
        try {

            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                nickList.add(resultSet.getString("nickname_bl"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new NullPointerException();
        } finally {
            disconnect(connection);
        }
        return nickList;
    }

    public static  String addUsersInBL(String thisUserNick, String usersInBlackList) {

        StringBuilder stringBuilder = new StringBuilder();
        String[] usersBL = usersInBlackList.split(" ");
        String sql;
        Connection connection = getConnect();
        try {
            connection.setAutoCommit(false);
            for (String userBL : usersBL
            ) {
                if (thisUserNick == userBL) {
                    stringBuilder.append("Нельзя добавлять себя в БЛ.\n");
                    continue;
                }
                sql = String.format("INSERT INTO blackList (\n" +
                        "                          user_nickname,\n" +
                        "                          userBL_nickname\n" +
                        "                      )\n" +
                        "                      VALUES (\n" +
                        "                          '%s',\n" +
                        "                          '%s'\n" +
                        "                      );\n", thisUserNick, userBL);
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.executeUpdate();
                stringBuilder.append("Пользователь (" + userBL + ") добавлен в БЛ.\n");
            }
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect(connection);
        }

        return stringBuilder.toString();
    }

    public static String removeUsersInBL(String thisUserNick, String usersInBlackList) {

        StringBuilder nickList = new StringBuilder();
        String[] usersBL = usersInBlackList.split(" ");
        String sql;
        Connection connection = getConnect();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps;
            for (String userBL : usersBL
            ) {
                connection.setAutoCommit(false);
                sql = String.format("DELETE FROM blackList\n" +
                        "      WHERE user_nickname = '%s' AND \n" +
                        "            userBL_nickname = '%s'", thisUserNick, userBL);

                ps = connection.prepareStatement(sql);
                ps.execute();
                nickList.append("Пользователь (" + userBL + ") удален из вашего БЛ.\n");
            }
            connection.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            nickList.append(throwables.toString() + "\n");
        } finally {
            disconnect(connection);
        }
        return nickList.toString();
    }

    public static void addUser(String login, String pass, String nick) {
        Connection connection = getConnect();
        try {
            String query = "INSERT INTO users (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            try {
                throw new Exception("Ошибка записи пользователя");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } finally {
            disconnect(connection);
        }
    }

    public static String updateNick(String login,  String nick) {
        Connection connection = getConnect();
        try {
            String query = "UPDATE users set nickname = ? where  login = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nick);
            ps.setString(2, login);
            ps.executeUpdate();
            return "Ник изменился на "+nick;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect(connection);
        }

        return "Ошибка обновления";
    }


    public static void disconnect(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
