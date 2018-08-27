
package bank.spring;

import bank.managers.DatabaseManager;
import bank.services.DatabaseService;
import bank.managers.PropertiesManager;
import bank.managers.ThreadManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankBaseApp {
    @Autowired private ThreadManager TM;
    private boolean downed;

    @PostConstruct public void initialize(){
        downed=false;
        System.out.println("Initializing BaseApp::::::::::::::::::::::::::::::::");
        //Get and update rates
        PropertiesManager.readProperties("config.properties");
        DatabaseManager.getInstance();
        
        DatabaseService.updateRates();
    }

    public boolean isDowned() {
        return downed;
    }  
    public void shutdown() {
        System.out.println("Shutting Down");
        TM.interruptThreads();
        TM.joinThreads();
        System.out.println("Transaction Appliers Joined");
        downed=true;
    }
    @PreDestroy void destroy(){
        if(isDowned()){
            System.out.println("Shutdown Hook: System is already downed !");
        }
        else{
            System.out.println("Shutdown Hook: is running !");
            shutdown();
            System.out.println("Shutdown Hook: System Downed !");
        }
    }
}
