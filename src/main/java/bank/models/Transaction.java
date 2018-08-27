
package bank.models;

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
    public String getIdentifier(){
        return identifier;
    }
    public double getAmount(){
        return amount;
    }
    public String getTo(){
        return beneficiaryIdentifier;
    }
    public String getFrom(){
        return benefactorIdentifier;
    }
}
