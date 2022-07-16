package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

         UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
         userDao.dropUsersTable();
         userDao.createUsersTable();
         userDao.saveUser("John","Mike", (byte) 31);
         userDao.removeUserById(0);
         userDao.getAllUsers();
         userDao.cleanUsersTable();

    }
}
