/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.clases;

import ejercicioBanco.utilidades.Utilidades;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class representing the movements made into an account.
 * @author Endika Ubierna.
 */
public class Movement implements Serializable{
    /**
     * The movement id. It´s also the database primary key for a movement.
     */
    private long movementId;
    /**
     * The account id. 
     */
    private long accountId;
    /**
     * Movement ammount. 
     */
    private double ammount;
    /**
     * Movement balance.
     */
    private double balance;
    /**
     * Movement description.
     */
    private String description;
    /**
     * Movement date.
     */
    private Timestamp databaseDate;

    //CONSTRUCTOR VACIO
    /**
     * Class constructor without parameters. 
     */
    public Movement() {
    }

    //CONSTRUCTOR SIN PARAMETROS
    /**
     * Class constructor with parameters.
     * @param movementId The movement id.
     * @param accountId The account id.
     * @param ammount The ammount of the movement.
     * @param balance The balance of the movement.
     * @param description The movement description.
     * @param databaseDate The movement date.
     */
    public Movement(long movementId, long accountId, double ammount, double balance, String description, Timestamp databaseDate) {
        this.movementId = movementId;
        this.accountId = accountId;
        this.ammount = ammount;
        this.balance = balance;
        this.description = description;
        this.databaseDate = databaseDate;
    }
    /**
     * This method allows to introduce movement information from console.
     * @param idAccount The id of an <code>Account</code>.
     */ 
    public void setDatos(Long idAccount){
        System.out.println("Introduce la cantidad: ");
        this.ammount = ejercicioBanco.utilidades.Utilidades.leerDouble(0,5000);
        System.out.println("Si desea efectuar un deposito marca 1, si es un pago marca 2: ");
        int opc = Utilidades.leerInt(1, 2);
        if(opc==1){
            this.description = "Deposit";
            this.balance += this.ammount;
        }else{
            this.description = "Payment";
            this.balance -= this.ammount;
        }
        this.databaseDate = Timestamp.valueOf(LocalDateTime.now()); 
        this.accountId = idAccount;     
    }
    
    /**
     * Method shows in console a movement information.
     */   
    public void getDatos(){
        System.out.println("Movimiento: "+this.movementId);
        System.out.println("Descripcion: "+this.description);
        System.out.println("Balance: "+this.balance);
        System.out.println("Cantidad: "+this.ammount);
        System.out.println("Tipo de movimiento: "+this.description);
        System.out.println("Fecha movimiento: "+this.databaseDate);        
    }
    /**
     * Gets the movement id.
     * @return A movement id.
     */
    public long getMovementId() {
        return movementId;
    }
    /**
     * Gets the account id.
     * @return The account id.
     */
    public long getAccountId() {
        return accountId;
    }
    /**
     * Gets the movement ammount.
     * @return The movement ammount
     */
    public double getAmmount() {
        return ammount;
    }
    /**
     * Gets the movement balance.
     * @return The movement balance.
     */
    public double getBalance() {
        return balance;
    }
    /**
     * Gets de movement description.
     * @return The movement description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Gets the movement date.
     * @return The movement date.
     */
    public Timestamp getDatabaseDate() {
        return databaseDate;
    }
    /**
     * Sets the movement id.
     * @param movementId The movement id.
     */
    public void setMovementId(long movementId) {
        this.movementId = movementId;
    }
    /**
     * Sets the account id.
     * @param accountId The account id.
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    /**
     * Sets the movement ammount.
     * @param ammount The movement ammount.
     */
    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }
    /**
     * Sets the movement balance.
     * @param balance The movement balance.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    /**
     * Sets a destription to the movement.
     * @param description The movement description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Sets the movement date.
     * @param databaseDate The movement date.
     */
    public void setDatabaseDate(Timestamp databaseDate) {
        this.databaseDate = databaseDate;
    }   
}
