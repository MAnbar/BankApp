
package bank.models;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**Account wrapper (for XML writing)*/
@XmlRootElement
public class Accounts {
    //Members-------------------------------------------------------------------
    private List<Account> account;  
    //Methods-------------------------------------------------------------------
    //Constructors
    public Accounts(){
    }
    public Accounts(List<Account> account){
        this.account=account;
    }
    //Setters
    public void setAccounts(List<Account> account){
        this.account=account;
    }
    //Getters
    @XmlElement(name="account")
    public List<Account> getAccounts(){
        return account;
    }
}
