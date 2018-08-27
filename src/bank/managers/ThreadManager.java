
package bank.managers;

public class ThreadManager {
    private Runnable runnable;
    private int size;
    private Thread[] threads;
    public ThreadManager(Runnable runnable,int size){
        this.size=size;
        this.runnable=runnable;
        
        threads=new Thread[size];
        for(int i=0;i<size;i++){
            threads[i]=new Thread(runnable);
        }
    }
    public void startThreads(){
        for(int i=0;i<size;i++){
            threads[i].start();
        }
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
