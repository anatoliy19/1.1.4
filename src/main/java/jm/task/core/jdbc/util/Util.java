package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {

    private static final String Connection_Url = "jdbc:mysql://localhost:3306/test?" +
            "&serverTimeZone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String User = "root";
    private static final String Password = "admin";
    public static Connection getConnection() {

        Connection connection = null;

        try
        {
            connection = DriverManager.getConnection( Connection_Url,User,Password);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static SessionFactory getSessionFactory() {

        SessionFactory sessionFactory = null;

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                Properties properties = configuration.getProperties();

                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/test?useLegacyDatetimeCode=false&serverTimeZone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "admin");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.HBM2DDL_AUTO, "create-drop");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = (ServiceRegistry) new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory((org.hibernate.service.ServiceRegistry) serviceRegistry);
            } catch (Exception e) {
                System.out.println("Exception on creating configuration:" + e);
            }
        }
        return  sessionFactory;
    }
}
