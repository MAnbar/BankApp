
package bank.baseapp;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**Transaction wrapper (for XML writing)*/
@XmlRootElement  
public class Transactions {
    //Members-------------------------------------------------------------------
    private List<Transaction> transaction;  
    //Methods-------------------------------------------------------------------
    //Constructors
    public Transactions(){
    }
    public Transactions(List<Transaction> transaction){
        this.transaction=transaction;
    }
    //Setters
    public void setAccounts(List<Transaction> transaction){
        this.transaction=transaction;
    }
    //Getters
    @XmlElement  
    public List<Transaction> getTransactions(){
        return transaction;
    }
}
