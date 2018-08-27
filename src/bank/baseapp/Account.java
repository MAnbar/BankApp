
package bank.baseapp;

import Utilities.PropertiesManager;
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
    /**Compares the parameter ID to the Account's ID*/
    public boolean is(String ID){
        return identifier.equals(ID);
    }
    /**Adds the money to the account and handles the currency difference*/
    public double addMoney(double money, String currency){
        if(accountCurrency.equals(currency)){
            accountValue+=money;
        }
        else if(currency.equals("EGP")&&accountCurrency.equals("USD")){
            accountValue+=(money*PropertiesManager.EGPtoUSD);
            System.out.println(money*PropertiesManager.EGPtoUSD);
        }
        else if(currency.equals("USD")&&accountCurrency.equals("EGP")){
            accountValue+=(money*PropertiesManager.USDtoEGP);
            System.out.println(money*PropertiesManager.USDtoEGP);
        }
        //System.out.println("To= "+accountValue);

        return accountValue;
    }
    /**Deducts the money form the account and returns the success flag (Checks if the account has enough money)*/
    public boolean payTransaction(double money){
        if(money>accountValue){
            System.out.println("Transaction failed .. not enough money");
            return false;
        }
        else
        {
            accountValue-=money;
            System.out.println("Transaction successful");
            //System.out.println("From= "+accountValue);
            return true;
        }
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
