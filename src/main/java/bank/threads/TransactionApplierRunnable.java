
package bank.threads;

import bank.models.Transaction;
import bank.services.DatabaseService;
import java.util.concurrent.ArrayBlockingQueue;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("TAR")
public class TransactionApplierRunnable  implements Runnable{
    @Autowired 
    private ArrayBlockingQueue<Transaction> transactionQueue;
    
    @PostConstruct public void init(){
        System.out.println("TAR Created:::::::::::::::::::::::::::::::::::::::::");
        System.out.println("ABQ is Null");
        System.out.println(transactionQueue==null);
    }
    
    @Override
    public void run() {
        System.out.println("TAR Thread "+Thread.currentThread().getId()+" is Running");
        while(true){
            try {
                Transaction currentTransaction;
                if(transactionQueue.isEmpty() && Thread.currentThread().isInterrupted()){
                    break;
                }
                System.out.println("Applier Taking..");
                System.out.println("Applier Queue Size "+transactionQueue.size());
                currentTransaction=transactionQueue.take();
                System.out.println("Applier: "+currentTransaction);
                System.out.println("Applier Running..");
                DatabaseService.makeTransaction(currentTransaction);
            } 
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); 
                break;
            }
        }
        System.out.println("Transaction Applier Thread Terminating..");
    }
}
