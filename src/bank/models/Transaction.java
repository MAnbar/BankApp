
package bank.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**Transaction DTO*/
public class Transaction {
    //Members-------------------------------------------------------------------
    private String identifier;
    private String beneficiaryIdentifier;
    private String benefactorIdentifier;
    private double amount;
    
    //Methods-------------------------------------------------------------------
    public Transaction(){
    }
    public Transaction(String identifier, double amount, String from, String to){
        this.identifier=identifier;
        this.amount=amount;
        this.beneficiaryIdentifier=to;
        this.benefactorIdentifier=from;
    }
    
    @Override
    public String toString()
    {
      return "Transaction [id=" + identifier + ", amount=" + amount  + ", benefactorId(from)=" + benefactorIdentifier + ", beneficiaryId(to)=" + beneficiaryIdentifier + "]";
    }
    
    //Setters
    public void setIdentifier(String identifier){
        this.identifier=identifier;
    }
    public void setAmount(double amount){
        this.amount=amount;
    }    
    public void setFrom(String benefactorIdentifier){
        this.benefactorIdentifier=benefactorIdentifier;
    }   
    public void setTo(String beneficiaryIdentifier){
        this.beneficiaryIdentifier=beneficiaryIdentifier;
    }

    //Getters
    @XmlAttribute(name="id")
    public String getIdentifier(){
        return identifier;
    }
    @XmlElement(name="amount")
    public double getAmount(){
        return amount;
    }
    @XmlElement(name="beneficiaryId")
    public String getTo(){
        return beneficiaryIdentifier;
    }
    @XmlElement(name="benefactorId")
    public String getFrom(){
        return benefactorIdentifier;
    }
}
