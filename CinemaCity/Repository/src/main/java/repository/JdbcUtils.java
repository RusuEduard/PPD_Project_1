package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private Properties jdbcProp;

    private Connection intance = null;

    public JdbcUtils(Properties jdbcProp) {
        this.jdbcProp = jdbcProp;
    }

    private Connection getNewConnection(){
        String url = jdbcProp.getProperty("jdbc.url");
        String user = jdbcProp.getProperty("jdbc.user");
        String password = jdbcProp.getProperty("jdbc.password");

        Connection con = null;

        try{
            if (user!=null && password!=null)
                con= DriverManager.getConnection(url,user,password);
            else
                con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("url"+url);
            System.out.println("user"+user);
            System.out.println("password"+password);
            System.out.println("Error la jdbcUtils");
        }

        return con;
    }

    public Connection getConnection(){
        try {
            if(intance==null || intance.isClosed())
                intance = getNewConnection();

        } catch (Exception e) {
            System.out.println("error la getConnection!");
        }
        return intance;
    }


}
