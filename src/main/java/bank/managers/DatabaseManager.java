package bank.managers;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Database connection pool manager(Singleton)*/
public class DatabaseManager{
    private static DatabaseManager databaseManager;
    private static ComboPooledDataSource dataSource;

    private DatabaseManager() {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:MySQL://localhost:3306/assetbank");
            dataSource.setUser("root"); 
            dataSource.setPassword("123456");
        } catch (PropertyVetoException ex) {
            System.out.println("Database connection failed");
        }
    }
    @Override
    public void finalize()
    {
        dataSource.close();
    }
    public static DatabaseManager getInstance(){
        if (databaseManager == null){
            databaseManager = new DatabaseManager();
            return databaseManager;
        } 
        else{
            return databaseManager;
        }
    }
    public static Connection getConnection() {
        try {      
            System.out.println("DBM: GetConnection");
            Connection conn=dataSource.getConnection();
            System.out.println("DBM: Return Connection");
            return conn;
        } catch (SQLException ex) {
            System.err.println("SQL Connection Failed");
            return null;
        }
    }
    public static void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}