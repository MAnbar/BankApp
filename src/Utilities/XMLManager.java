package Utilities;

import bank.baseapp.Account;
import bank.baseapp.Transaction;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.xml.sax.SAXException;

public class XMLManager {
    //Members-------------------------------------------------------------------
    private String fileName;
    private Document currentDoc;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    //private ArrayList<Account> accountList = new ArrayList<>();
    private HashMap<String, Account> accountList= new HashMap<>();
    private ArrayList<Transaction> transactionList = new ArrayList<>();    
    //Methods-------------------------------------------------------------------
    public XMLManager(String fileName){
        this.fileName=fileName;
        try {
        //Get Document Builder
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        //Build Document
        currentDoc = builder.parse(new File( fileName ));
        }
        catch(ParserConfigurationException Exc) {
            System.out.println("Parser creation failed ..!");
        }
        catch(SAXException Exc) {
           System.out.println("Sax exception ..!"); 
        }
        catch(IOException Exc) {
           System.out.println("IO exception ..!"); 
        }
    }

    //Getters
    public ArrayList<Account> getAccountList(){
        
        Collection<Account> values = accountList.values();
         
        //Creating an ArrayList of values   
        ArrayList<Account> listOfValues = new ArrayList<>(values);
        return listOfValues;
    }
    public ArrayList<Transaction> getTransactionList(){
        return transactionList;
    }
    
    /**Parses the XML and loads it into the Transactions List*/
    public void parseTransationsXML(){
        //Clear AccountList
        transactionList.clear();
        
        //Normalize the XML Structure
        currentDoc.getDocumentElement().normalize();
        
        //Read root node
        Element root = currentDoc.getDocumentElement();
        
        //Get all transactions
        NodeList nList = currentDoc.getElementsByTagName("transaction");
        System.out.println("Parsing transactions");
          
        for (int temp = 0; temp < nList.getLength(); temp++){
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                //Load into DTO
                Element eElement = (Element) node;
                String tempID=eElement.getAttribute("id");
                String tempAmount=eElement.getElementsByTagName("amount").item(0).getTextContent();
                String tempbenefactorId=eElement.getElementsByTagName("benefactorId").item(0).getTextContent();
                String tempbeneficiaryId=eElement.getElementsByTagName("beneficiaryId").item(0).getTextContent();
                Transaction tempTransaction=new Transaction(tempID, Double.parseDouble(tempAmount), tempbeneficiaryId, tempbenefactorId);
                transactionList.add(tempTransaction);
                
               //Print each transaction's detail//Check
//               System.out.println("id : "    + eElement.getAttribute("id"));
//               System.out.println("amount : "    + eElement.getElementsByTagName("amount").item(0).getTextContent());
//               System.out.println("from : "      + eElement.getElementsByTagName("to").item(0).getTextContent());
//               System.out.println("to : "        + eElement.getElementsByTagName("from").item(0).getTextContent());
            }
        }  
        System.out.println("Transactions parsing complete");

    }

    /**Parses the XML and loads it into the Accounts List*/
    public void parseAccountsXML(){
        //Clear AccountList
        accountList.clear();
        
        //Normalize the XML Structure
        currentDoc.getDocumentElement().normalize();
        
        //Read root node
        Element root = currentDoc.getDocumentElement();
        
        //Get all transactions
        NodeList nList = currentDoc.getElementsByTagName("account");
        System.out.println("Parsing Accounts");
        for (int temp = 0; temp < nList.getLength(); temp++){
         Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                //Load into DTO
                Element eElement = (Element) node;
                String tempID=eElement.getAttribute("id");
                String tempHolder=eElement.getElementsByTagName("holder").item(0).getTextContent();
                String tempAmount=eElement.getElementsByTagName("amount").item(0).getTextContent();
                String tempCurrency=eElement.getElementsByTagName("currency").item(0).getTextContent();
                String tempSSN=eElement.getElementsByTagName("ssc").item(0).getTextContent();
                String tempMobile=eElement.getElementsByTagName("mobile").item(0).getTextContent();
                Account tempAccount=new Account(tempID,Double.parseDouble(tempAmount), tempHolder, tempCurrency, tempSSN, tempMobile);
                accountList.put(tempID,tempAccount);               
            }
        } 
        System.out.println("Accounts parsing complete");
    }
    
    /**Changes the current file/document*/
    public void changeCurrentDocument(String fileName){
        this.fileName=fileName;
        try{
        currentDoc = builder.parse(new File( fileName ));
        }
        catch(Exception Exc){
            System.out.println("Document change unsuccessful");
        }
        System.out.println("\nCurrent document successfully changed\n");
    }
    /**Prints the transactions to the console*/
    public void printTransactions(){
        if(transactionList.isEmpty()){
            System.out.println("Transaction List is empty");
        }
        else{
            System.out.println("Transactions::");
            for (Transaction tran : transactionList) {
                System.out.println(tran);
            }
        }
    }
    /**Prints the accounts to the console*/
    public void printAccounts(){
        if(accountList.isEmpty()){
            System.out.println("Account List is empty");
        }
        else{
            System.out.println("Accounts::");
            Set set = accountList.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.println(mentry.getValue());
            }
        }
    }
    /**Performs all the transactions on the accounts*/
    public void applyTransactions(){
        System.out.println("Applying transactions");
        String fromAccID;
        String fromCurr="";
        String toAccID;
        Account toAcc;
        Account fromAcc;
        Double amount;
        boolean fromFound=false;
        boolean toFound=false;
        
        for (int i=0; i<transactionList.size(); i++){
            applyTransaction(transactionList.get(i));
        }    
    }
    /**Performs a single transaction (sub-function)*/
    public void applyTransaction(Transaction currentTransaction){
        String fromAccID;
        String fromCurr="";
        String toAccID;
        Account toAcc;
        Account fromAcc;
        Double amount;
        boolean fromFound=false;
        boolean toFound=false;
        
        //Gets the from-accountId, to-accountID, and the amount of the transaction
        fromAccID=  currentTransaction.getFrom();
        toAccID=    currentTransaction.getTo();
        amount=     currentTransaction.getAmount();

        //Gets the account objects from the account hashmaps
        fromAcc =accountList.get(fromAccID);
        toAcc =accountList.get(toAccID);
        
        //Checks that account fetch was successful
        if(fromAcc != null){
            fromFound=true;
            fromCurr=fromAcc.getCurrency();
        }
        if(toAcc != null){
            toFound=true;
        }

        //If both accounts were found
        if(fromFound && toFound){
            //Checks that the senders account has enough money to procced with the transaction
            if(fromAcc.payTransaction(amount)){
                toAcc.addMoney(amount, fromCurr);//Performs the transfer at the receiver's side
                System.out.println("Transaction successful");
            }
            else{
                System.out.println("Transaction failed .. not enough money to complete the transaction");
            }
        }
        else{
            System.out.println("Transaction failed .. accounts not found");
        }
    }
}
