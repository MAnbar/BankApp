
package bank.main;

import bank.models.Transaction;
import bank.managers.DatabaseManager;
import bank.services.DatabaseService;
import bank.threads.FileListenerRunnable;
import bank.managers.PropertiesManager;
import bank.managers.ThreadManager;
import bank.threads.TransactionApplierRunnable;
import bank.threads.XMLParserRunnable;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

public class BankBaseApp {
    
    private final Thread pathListener;
    private final ThreadManager  parseThreads;
    private final ThreadManager  applyThreads;
    
    private boolean downed;
            
    private final ArrayBlockingQueue<Document> documentQueue;
    private final ArrayBlockingQueue<Transaction> transactionQueue;
    
    public BankBaseApp(){
        downed=false;
        //Create queues
        documentQueue=new ArrayBlockingQueue<>(1024);
        transactionQueue=new ArrayBlockingQueue<>(1024);
        
        //Get and update rates
        PropertiesManager.readProperties("config.properties");
        DatabaseManager.getInstance();
        
        DatabaseService.updateRates();

        //Get thread numbers
        int nParseThreads=PropertiesManager.getnParseThreads();
        int nApplyThreads=PropertiesManager.getnApplyThreads();
        
        //Create runnables
        FileListenerRunnable FLR= new FileListenerRunnable(documentQueue,"./Transactions");
        XMLParserRunnable XMLPR = new XMLParserRunnable(documentQueue, transactionQueue); 
        TransactionApplierRunnable TAR = new TransactionApplierRunnable(transactionQueue);
    
        //Create threads
        pathListener = new Thread(FLR);
        parseThreads = new ThreadManager(XMLPR, nParseThreads);
        applyThreads = new ThreadManager(TAR, nApplyThreads);
    }

    public boolean isDowned() {
        return downed;
    }

    
    public void start(){
        pathListener.start();
        parseThreads.startThreads();
        applyThreads.startThreads();    
    }
    
    public void shutdown() {
        System.out.println("Shutting Down");
        pathListener.interrupt();
        try {
            pathListener.join();
        } 
        catch (InterruptedException ex) {
            pathListener.interrupt();
        }
        System.out.println("Path Listener Joined");
        
        parseThreads.interruptThreads();
        applyThreads.interruptThreads();
        parseThreads.joinThreads();
        System.out.println("Parsers Joined");
        applyThreads.joinThreads();
        System.out.println("Transaction Appliers Joined");
        downed=true;
    }
    
    public static void main(String[] args) {
        BankBaseApp base=new BankBaseApp();
        
        Runtime.getRuntime().addShutdownHook(new Thread(){
          public void run()
          {
            if(base.isDowned()){
                System.out.println("Shutdown Hook: System is already downed !");
            }
            else{
                System.out.println("Shutdown Hook: is running !");
                base.shutdown();
                System.out.println("Shutdown Hook: System Downed !");
            }
          }
        });
        
        
        base.start();;

        
         
 
        
        System.out.println("Press Enter to Exit");
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(BankBaseApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //base.shutdown();
        
        System.out.println("Main Terminated...");
        System.exit(0);
    }
}
