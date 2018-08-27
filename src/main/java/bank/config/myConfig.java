
package bank.config;

import bank.models.Transaction;
import java.util.concurrent.ArrayBlockingQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
public class myConfig{
    @Bean("transactionQueue")
    @Scope("singleton")
    public ArrayBlockingQueue<Transaction> getQueue() {
        System.out.println("ABQ Bean Created::::::::::::::::::::::::::::::::::::");
        return  new ArrayBlockingQueue<>(1024);
    }
}