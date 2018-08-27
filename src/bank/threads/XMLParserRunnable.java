
package bank.threads;

import bank.managers.XMLManager;
import bank.models.Transaction;
import bank.models.Transactions;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Document;

public class XMLParserRunnable implements Runnable{
    private Document currentDoc;
    private ArrayList<Transaction> transactionList = new ArrayList<>();
    private ArrayBlockingQueue<Document> documentQueue;
    private ArrayBlockingQueue<Transaction> transactionQueue;
    
    public XMLParserRunnable(ArrayBlockingQueue<Document> documentQueue,ArrayBlockingQueue<Transaction> transactionQueue){
        this.documentQueue=documentQueue;
        this.transactionQueue=transactionQueue;
    }
    
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
    
    public void addTransactionsToQueue(){
        if(!transactionList.isEmpty()){
            System.out.println("Parser AddingAll..");
            System.out.println("Parser: "+transactionList.size());
            transactionQueue.addAll(transactionList);
            System.out.println("Parser Running..");
        }
    }
    
    @Override
    public void run() {
        while(true){
            try {
                if(documentQueue.isEmpty() && Thread.currentThread().isInterrupted()){
                    break;
                }
                System.out.println("Parser Taking..");
                currentDoc=documentQueue.take();
                System.out.println("Parser: "+currentDoc.getDocumentURI());
                System.out.println("Parser Running..");
            } 
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); 
                break;
            }
            parseTransationsXML();
            addTransactionsToQueue();
        }
        System.out.println("XML Parser Thread Terminating..");
    }
}
