
package bank.threads;

import bank.models.Transaction;
import bank.services.DatabaseService;
import java.util.concurrent.ArrayBlockingQueue;


public class TransactionApplierRunnable  implements Runnable{
    private ArrayBlockingQueue<Transaction> transactionQueue;
    
    public TransactionApplierRunnable(ArrayBlockingQueue<Transaction> transactionQueue){
        this.transactionQueue=transactionQueue;
    }

    @Override
    public void run() {
         
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
