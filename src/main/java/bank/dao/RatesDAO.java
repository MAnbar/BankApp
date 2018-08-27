
package bank.dao;

import bank.exceptions.RollbackException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RatesDAO {     
    
    public static boolean updateInsertRate(Connection connection, String rateName, float rate) throws RollbackException{
        //String statementSQL="INSERT INTO `assetbank`.`rates`(`ratename`,`value`)VALUES('"+rateName+"',"+rate+") ON DUPLICATE KEY UPDATE  `value`=values(`value`);";
        
        if(connection==null){
            return false;
        }
        
        CallableStatement statement = null;
        boolean status=false;
        try {
            String sql = "INSERT INTO `assetbank`.`rates`(`ratename`,`value`)VALUES(?,?)ON DUPLICATE KEY UPDATE `value`=values(`value`);";
            statement=connection.prepareCall(sql);
        } 
        catch (SQLException ex) {
                System.out.println("Connection failed: Rollingback");
                Logger.getLogger(StoredProceduresDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new RollbackException();
        }
        try{
            //Setting IN parameters
            statement.setString(1, rateName);
            statement.setFloat(2, rate);

            status=statement.execute();
        }
        catch (SQLException ex) {        
            System.out.println("Statement execute failed..");
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
