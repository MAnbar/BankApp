
package bank.managers;

import bank.threads.TransactionApplierRunnable;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("TM")
public class ThreadManager {
    @Autowired 
    private TransactionApplierRunnable TAR;
    private int size;
    private Thread[] threads;

    @PostConstruct 
    public void init(){
        System.out.println("Thread Manager Initialized::::::::::::::::::::::::::");
        System.out.println("TAR is NULL? ");
        System.out.println(TAR==null);
        PropertiesManager.readProperties("config.properties");
        int nThreads=PropertiesManager.getnApplyThreads();
        this.size=nThreads;
        threads=new Thread[size];
        System.out.println("Threads Starting::::::::::::::::::::::::::::::::::::");
        for(int i=0;i<size;i++){
            threads[i]=new Thread(TAR);
            threads[i].start();
        }
        System.out.println("Threads Started=====================================");
    }
    
    public void interruptThreads(){
        for(int i=0;i<size;i++){
            threads[i].interrupt();
        }
    }
    public boolean joinThreads(){
        for(int i=0;i<size;i++){
            try {
                threads[i].join();
            } 
            catch (InterruptedException ex) {
                threads[i].interrupt();
                return false;
            }
        }
        return true;
    }
}
