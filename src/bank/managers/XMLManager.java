package bank.managers;

import bank.managers.DatabaseManager;
import bank.models.Account;
import bank.models.Accounts;
import bank.models.Transaction;
import bank.models.Transactions;
import java.beans.PropertyVetoException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;

public class XMLManager {
    private DatabaseManager dbm;
    //Members-------------------------------------------------------------------
    private Document currentDoc;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    //private ArrayList<Account> accountList = new ArrayList<>();
    private HashMap<String, Account> accountList= new HashMap<>();
    private ArrayList<Transaction> transactionList = new ArrayList<>();    
    //Methods-------------------------------------------------------------------
    public XMLManager(String fileName){
        dbm=DatabaseManager.getInstance();
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
    
    //Parse XML into DTO Objects
    /**Parses the XML and loads it into the Transactions List*/
    public void parseTransationsXML(){
        try {
            JAXBContext jc = JAXBContext.newInstance(Transactions.class);
            
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Transactions transactions = (Transactions) unmarshaller.unmarshal(currentDoc);
            transactionList=transactions.getTransactions();
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //marshaller.marshal(transactions, System.out);
        } catch (JAXBException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void parseTransationsXMLLegacy(){
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
                Transaction tempTransaction=new Transaction(tempID, Double.parseDouble(tempAmount), tempbenefactorId, tempbeneficiaryId);
                transactionList.add(tempTransaction);
            }
        }  
        System.out.println("Transactions parsing complete");

    }
    /**Parses the XML and loads it into the Accounts List*/
    
    /**Changes the current file/document*/
    public void changeCurrentDocument(String fileName){
        try{
        currentDoc = builder.parse(new File( fileName ));
        }
        catch(Exception Exc){
            System.out.println("Document change unsuccessful");
        }
        System.out.println("\nCurrent document successfully changed\n");
    }
    
    //Print to console
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
    
    //Write to XML files
    public void writeAccountsToXML(String fileName){
        Accounts account=new Accounts(getAccountList());
        try {
            File file = new File(fileName+".xml");
            
            JAXBContext jaxbContext = JAXBContext.newInstance(Accounts.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            // output printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            jaxbMarshaller.marshal(account, file);  
        } 
        catch (JAXBException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void writeTransactionsToXML(String fileName){
        Transactions transactions=new Transactions(transactionList);
        try {
            File file = new File(fileName+".xml");
            
            JAXBContext jaxbContext = JAXBContext.newInstance(Transactions.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            jaxbMarshaller.marshal(transactions, file);  
        } 
        catch (JAXBException ex) {
            Logger.getLogger(XMLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}