package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final static String sqlCommandCreate = "CREATE TABLE users (id bigint, " +
            "name varchar(100), lastName varchar(100), age tinyint)";
    private final static String sqlCommandAddNew = "INSERT INTO users (id, name, lastName, age) VALUES (?,?,?,?)";
    private final static String sqlCommandDrop = "DROP TABLE IF EXISTS users;";
    private final static String sqlCommandGetAll = "SELECT * FROM users";
    private final static String sqlCommandTruncate = "TRUNCATE TABLE users";
    private final static String sqlCommandDeleteWhere = "DELETE FROM users WHERE id = ";
    private static Transaction transaction = null;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommandDrop);
            statement.executeUpdate(sqlCommandCreate);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommandDrop);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sqlCommandAddNew, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, 1L);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setInt(4, age);
            statement.execute();

            connection.commit();
            System.out.println(name + " added into database");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate(sqlCommandDeleteWhere + id);
        }
    }

    public List<User> getAllUsers() throws SQLException {

        List<User> list = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {

            ResultSet resultSet  = connection.createStatement().executeQuery(sqlCommandGetAll);
            while (resultSet.next()) {
                String name     = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age        = resultSet.getByte  ("age");
                list.add(new User(name, lastName, age));
            }
            return list;
        }
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommandTruncate);
        }
    }
}
