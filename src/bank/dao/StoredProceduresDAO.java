
package bank.dao;

import bank.exceptions.RollbackException;
import bank.models.Transaction;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoredProceduresDAO {
    
    public static boolean applyTransaction(Connection connection,Transaction transaction) throws RollbackException{
        
        System.out.println("DAO: " +transaction);
        if(connection==null || transaction==null){
            return false;
        }
        
        String id=transaction.getIdentifier();
        String to=transaction.getTo();
        String from=transaction.getFrom();
        double amount=transaction.getAmount();
        
        CallableStatement statement = null;
        boolean status=false;
        try {
            String sql = "{call assetbank.applyTransaction(?,?,?,?,?)}";
            statement=connection.prepareCall(sql);          
        } 
        catch (SQLException ex) {
            System.out.println("Connection failed: Rollingback");
            Logger.getLogger(StoredProceduresDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RollbackException();
        }
        try{
                        //Setting IN parameters
            statement.setString(1, id);
            statement.setDouble(2, amount);
            statement.setString(3, from);
            statement.setString(4, to);
            //Set OUT parameter
            statement.registerOutParameter(5, Types.VARCHAR);
            status=statement.execute();
            String success = statement.getString(5);
            switch (success) {
                case "F":
                    System.out.println("Transaction has already been performed (ID exists in the database)");
                    break;
                case "Y":
                    System.out.println("Transaction was successful");
                    status=true;
                    break;
                case "N":
                    System.out.println("Transaction failed (accounts not found or not enough money)");
                    break;
                default:
                    System.out.println("Unexpected response");
                    break;
            }
        }
        catch (SQLException ex) {      
            System.out.println("Statement failed");
            Logger.getLogger(StoredProceduresDAO.class.getName()).log(Level.SEVERE, null, ex);
        }      
        finally{
            if(statement!=null){
                try{
                    statement.close();
                } 
                catch (SQLException ex) {
                    System.out.println("Statement closure failed..");
                    Logger.getLogger(StoredProceduresDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return status;
    }
    
}
