
package Utilities;

import bank.baseapp.Account;
import bank.baseapp.Accounts;
import bank.baseapp.Transaction;
import bank.baseapp.Transactions;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLWriter {
    //Members-------------------------------------------------------------------
    private Accounts account;
    private Transactions transaction;
    
    //Methods-------------------------------------------------------------------
    public XMLWriter(List<Account> accountList, List<Transaction> transactionList){
        account=new Accounts(accountList);
        transaction=new Transactions(transactionList);
    }
    
    public void printAccountsToXML(String fileName){
        try {
            File file = new File(fileName+".xml");
            
            JAXBContext jaxbContext = JAXBContext.newInstance(Accounts.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            // output printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            jaxbMarshaller.marshal(account, file);  
        } 
        catch (JAXBException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printTransactionsToXML(String fileName){
        try {
            File file = new File(fileName+".xml");
            
            JAXBContext jaxbContext = JAXBContext.newInstance(Transactions.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            jaxbMarshaller.marshal(transaction, file);  
        } 
        catch (JAXBException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Setters
    public void setAccounts(List<Account> accountList){
        account=new Accounts(accountList);
    }
    public void setTransactions(List<Transaction> transactionList){
        transaction=new Transactions(transactionList);
    }

    //Getters
    public Accounts getAccountsOBJ(){
        return account;
    }
    public Transactions getTransactionsOBJ(){
        return transaction;
    }
}