
package bank.spring;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("bank")
public class Application {
    @Autowired private static BankBaseApp base;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);     
        System.out.println("Press Enter to Exit");
        try {
            System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(BankBaseApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }    
}