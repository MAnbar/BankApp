
package bank.models;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**Transaction wrapper (for XML writing)*/
@XmlRootElement(name = "transactions")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Transactions {
    //Members-------------------------------------------------------------------
    private ArrayList<Transaction> transaction;  
    //Methods-------------------------------------------------------------------
    //Constructors
    public Transactions(){
    }
    public Transactions(ArrayList<Transaction> transaction){
        this.transaction=transaction;
    }
    //Setters
    public void setTransactions(ArrayList<Transaction> transaction){
        this.transaction=transaction;
    }
    //Getters
    @XmlElement(name = "transaction") 
    public ArrayList<Transaction> getTransactions(){
        return transaction;
    }
}
