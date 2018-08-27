
package bank.services;

import bank.dao.RatesDAO;
import bank.dao.StoredProceduresDAO;
import bank.exceptions.RollbackException;
import bank.managers.DatabaseManager;
import bank.managers.PropertiesManager;
import bank.models.Transaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseService {
    
    public static Connection getConnection(){
        return DatabaseManager.getInstance().getConnection();
    }
    
    private static void closeConnection(Connection connection){
        DatabaseManager.closeConnection(connection);
    }
    
    public static boolean makeTransaction(Transaction transaction)
    {   
        System.out.println("Applying "+transaction);
        boolean status=false;
        Connection connection=null;
        try {
            connection = DatabaseManager.getConnection();

            status = StoredProceduresDAO.applyTransaction(connection,transaction);
            //System.out.println("Commiting Transaction..");
            //connection.commit();
            //System.out.println("Commit Successful..");
        } catch (RollbackException ex) {
            try {
                if(connection!=null){
                System.out.println("Rollingback transaction..");
                connection.rollback();
                }
            } catch (SQLException ex1) {
                System.out.println("Rollback failed");
                Logger.getLogger(StoredProceduresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        finally{
            System.out.println("Closing Connection");
            //closeConnection();
        }
        return status;
    }
    
    public static boolean updateRates(){
        System.out.println("DBS: Updating Rates");
        if(!PropertiesManager.isInitialized()){
            PropertiesManager.readProperties("config.properties");
        }
        System.out.println("DBS: Getting Connection");
        getConnection();
        Connection connection = null;
        System.out.println("DBS: Updating");
        boolean etu=false;
        boolean ute=false;
        try {
            connection = DatabaseManager.getConnection();
            etu = RatesDAO.updateInsertRate(connection,"egptousd", PropertiesManager.getEGPtoUSD());
            ute = RatesDAO.updateInsertRate(connection,"usdtoegp", PropertiesManager.getUSDtoEGP());
            System.out.println("Commiting Rates..");
            //connection.commit();
            System.out.println("Commit Successful..");
        } 
        catch (RollbackException ex) {
            try {
                if(connection!=null){
                    System.out.println("Rollingback..");
                    connection.rollback();
                }
            } catch (SQLException ex1) {
                System.out.println("Rollback failed");
                Logger.getLogger(StoredProceduresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        finally{
            System.out.println("Closing Connection");
            //closeConnection();
        }
        return (etu && ute);
    }
}
