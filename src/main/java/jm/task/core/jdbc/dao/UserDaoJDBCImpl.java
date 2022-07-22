package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final static String sqlCommandCreate = "CREATE TABLE IF NOT EXISTS users (id bigint, " +
            "name varchar(100), lastName varchar(100), age tinyint)";
    private final static String sqlCommandAddNew = "INSERT INTO users (id, name, lastName, age) VALUES (?,?,?,?)";
    private final static String sqlCommandDrop = "DROP TABLE IF EXISTS users;";
    private final static String sqlCommandGetAll = "SELECT * FROM users";
    private final static String sqlCommandTruncate = "TRUNCATE TABLE users";
    private final static String sqlCommandDeleteWhere = "DELETE FROM users WHERE id = ";
    private static Transaction transaction = null;
    private Connection connection = null;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlCommandCreate);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void dropUsersTable()  {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlCommandDrop);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlCommandAddNew);
            statement.setLong(1, 1L);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setInt(4, age);
            statement.execute();

            connection.commit();
            System.out.println(name + " added into database");

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlCommandDeleteWhere + id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet  = statement.executeQuery(sqlCommandGetAll);
            while (resultSet.next()) {

                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte  ("age"));

                list.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sqlCommandTruncate);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
