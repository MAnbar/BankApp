/*v4 
 Added created a new subfunction (apply transaction)
*/
package bank.baseapp;

import Utilities.PropertiesManager;
import Utilities.XMLManager;
import Utilities.XMLWriter;

public class BankBaseApp {

    public static void main(String[] args) {
        
        PropertiesManager PropManager=new PropertiesManager();
        
        PropManager.readProperties("config.properties");
        
        XMLManager  XMLR= new XMLManager("Transactions.xml");
                //Transactions
        XMLR.parseTransationsXML();
        
        XMLR.printTransactions();
        
        XMLR.changeCurrentDocument("Accounts.xml");
        
        XMLR.parseAccountsXML();
        
        XMLR.printAccounts();
        
        XMLR.applyTransactions();
        
        XMLR.printAccounts();
        
        XMLWriter XMLW=new XMLWriter(XMLR.getAccountList(), XMLR.getTransactionList());
        
        XMLW.printAccountsToXML("OutAcc");
        XMLW.printTransactionsToXML("OutTran");
        
        
    }
    
}
