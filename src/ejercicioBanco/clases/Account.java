package ejercicioBanco.clases;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class representing the bank account for customers.
 * @version 1.0
 * @author Endika Ubierna.
 */
public class Account implements Serializable{
    /**
     * Account identifier.
     */
    private long accountId;
    /**
     * Account description.
     */
    private String description;
    /**
     * Account current balance.
     */
    private double balance;
    /**
     * Account current credit line.
     */
    private double creditLine;
    /**
     * Account begin balance.
     */
    private double beginBalance;
    /**
     * Account begin balance date.
     */
    private Timestamp beginBalanceTimestamp;
    /**
     * Account type.
     */
    private int type;
    
    /**
     * Class constructor without parameters
     */
     public Account() {
    }
    /**
     * Class Account constructor with parameters.
     * @param description Account description.
     * @param beginBalance Account current balance.
     * @param creditLine Account current credit line.
     * @param balance Account begin balance.
     * @param beginBalanceTimestamp Account begin balance date.
     * @param type Account type.
     */
    public Account(String description, double balance, double creditLine, double beginBalance, Timestamp beginBalanceTimestamp, int type) {
        this.description = description;
        this.balance = balance;
        this.creditLine = creditLine;
        this.beginBalance = beginBalance;
        this.beginBalanceTimestamp = beginBalanceTimestamp;
        this.type = type;
    }
    /**
     * This method allows to introduce account information from console.
     */
    public void setDatos(){
        System.out.println("Introduce la descripcion de la cuenta: ");
        this.description = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        System.out.println("Introduce balance inicial: ");
        this.beginBalance = ejercicioBanco.utilidades.Utilidades.leerDouble();
        System.out.println("Introduce el tipo de cuenta: ");
        this.type = ejercicioBanco.utilidades.Utilidades.leerInt(0, 1);
        System.out.println("Introduce linea de cuenta: ");
        this.creditLine = ejercicioBanco.utilidades.Utilidades.leerDouble();
        this.beginBalanceTimestamp = Timestamp.valueOf(LocalDateTime.now()); 
        this.balance = beginBalance;
    }
    
    /**
     * Method shows in console an account information.
     */
    public void getDatos(){
        System.out.println("Los datos del la cuenta: "+this.accountId);
        System.out.println("Descripcion: "+this.description);
        System.out.println("Balance: "+this.balance);
        System.out.println("Cuenta Bancaria: "+this.creditLine);
        System.out.println("Tipo de cuenta: "+this.type);
        System.out.println("Fecha apertura: "+this.beginBalanceTimestamp.toLocalDateTime().toString());
    }
     
    //GETTERS Y SETTERS

    /**
     * Gets an account id.
     * @return The account id number. It is also the account database primary key.
     */
    public long getAccountId() {
        return accountId;
    }
    /**
     * Gets the account description.
     * @return The account description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Gets the account balance
     * @return The account balance.
     */
    public double getBalance() {
        return balance;
    }
    /**
     * Gets the account creditline.
     * @return The account creditline.
     */
    public double getCreditLine() {
        return creditLine;
    }
    /**
     * Gets the account begin balance.
     * @return The account begin balance.
     */
    public double getBeginBalance() {
        return beginBalance;
    }
    /**
     * Gets the account beginbalance date.
     * @return The account beginbalance date.
     */
    public Timestamp getBeginBalanceTimestamp() {
        return beginBalanceTimestamp;
    }
    /**
     * Gets the account type.
     * @return The account type.
     */
    public int getType() {
        return type;
    } 
    /**
     * Sets the account id.
     * @param accountId The account id.
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    /**
     * Sets the account description.
     * @param description The account description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Sets the account balance.
     * @param balance the account balance.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    /**
     * Sets the account credit line.
     * @param creditLine The account credit line.
     */
    public void setCreditLine(double creditLine) {
        this.creditLine = creditLine;
    }

    /**
     * Sets the account begin balance.
     * @param beginBalance The account begin balance.
     */
    public void setBeginBalance(double beginBalance) {
        this.beginBalance = beginBalance;
    }

    /**
     * Sets the account begin balance date.
     * @param beginBalanceTimestamp The account begin balance date.
     */
    public void setBeginBalanceTimestamp(Timestamp beginBalanceTimestamp) {
        this.beginBalanceTimestamp = beginBalanceTimestamp;
    }

    /**
     * Sets the account type.
     * @param type The account type.
     */
    public void setType(int type) {
        this.type = type;
    }

     
}

