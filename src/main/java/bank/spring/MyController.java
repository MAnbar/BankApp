
package bank.spring;

import bank.models.Transaction;
import bank.services.DatabaseService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    
    @Autowired 
    private ArrayBlockingQueue<Transaction> transactionQueue;
    //private final ArrayBlockingQueue<bank.models.Transaction> transactionQueue;
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }

    @RequestMapping("/applyOff")
    public String applyOff(@RequestParam String id,@RequestParam String from,@RequestParam String to,@RequestParam double amount)
    {
        Transaction transaction = new Transaction(id, amount, from, to);
        try {
            transactionQueue.put(transaction);
            System.out.println(transactionQueue.size());
        } catch (InterruptedException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  "Queued "+transaction;
    }
    @RequestMapping("/applyOn")
    public String applyOn(@RequestParam String id,@RequestParam String from,@RequestParam String to,@RequestParam double amount)
    {
        Transaction transaction = new Transaction(id, amount, from, to);
        boolean success= DatabaseService.makeTransaction(transaction);
        if(success){
            return  "Successful "+transaction;
        }
        return  "Failed "+transaction;
    }
}
