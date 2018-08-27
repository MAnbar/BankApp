
package bank.baseapp;

/**Transaction DTO*/
public class Transaction {
    //Members-------------------------------------------------------------------
    private String identifier;
    private String beneficiaryIdentifier;
    private String benefactorIdentifier;
    private double amount;
    private boolean  applied;
    
    //Methods-------------------------------------------------------------------
    public Transaction(String identifier, double amount, String to, String from){
        this.identifier=identifier;
        this.amount=amount;
        this.beneficiaryIdentifier=to;
        this.benefactorIdentifier=from;
        this.applied=false;
    }
    
    @Override
    public String toString()
    {
      return "Transaction [id=" + identifier + ", amount=" + amount + ", beneficiaryId(to)=" + beneficiaryIdentifier + ", benefactorId(from)=" + benefactorIdentifier +"]";
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
    public void setApplied(boolean applied){
        this.applied=applied;
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
    public boolean getApplied(){
        return applied;
    }
}
