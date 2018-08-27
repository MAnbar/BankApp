
package bank.models;

import bank.managers.PropertiesManager;
import javax.xml.bind.annotation.*;
/**Account DTO*/
@XmlType(propOrder = { "holder", "value", "currency", "SSN", "mobile"})
public class Account {
    //Members-------------------------------------------------------------------
    private String  identifier;
    private String  holder;
    private double  accountValue;
    private String  accountCurrency;
    private String  socialSecurityNumber;
    private String  mobile;
    
    //Methods-------------------------------------------------------------------
    public Account(String identifier, double accountValue, String holder, String cur, String SSN, String mobile ){
        this.identifier=identifier;
        this.accountValue=accountValue;
        this.holder=holder;
        this.accountCurrency=cur;
        this.socialSecurityNumber=SSN;
        this.mobile=mobile;
    }
    @Override
    public String toString()
    {
      return "Account [id=" + identifier + ", Holder=" + holder + ", Value=" + accountValue+ ", Currency=" + accountCurrency+ ", SSN=" + socialSecurityNumber+ ", Mobile=" + mobile +"]";
    }

    //Setters
    public void setIdentifier(String identifier){
        this.identifier=identifier;
    }
    public void setValue(double accountValue){
        this.accountValue=accountValue;
    }    
    public void setHolder(String holder){
        this.holder=holder;
    }
    public void setCurrency(String cur){
        this.accountCurrency=cur;
    }
    public void setSSN(String SSN){
        this.socialSecurityNumber=SSN;
    }
    public void setMobile(String mobile){
        this.mobile=mobile;
    }
    
    //Getters
    @XmlAttribute(name = "id")
    public String getIdentifier(){
        return identifier;
    }
    @XmlElement(name = "amount")
    public double getValue(){
        return accountValue;
    }
    public String getHolder(){
        return holder;
    }
    public String getCurrency(){
        return accountCurrency;
    }
    public String getSSN(){
        return socialSecurityNumber;
    }
    public String getMobile(){
        return mobile;
    }
}
