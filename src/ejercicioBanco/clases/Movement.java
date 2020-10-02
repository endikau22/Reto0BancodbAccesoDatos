/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.clases;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Movimientos de las cuentas del banco.
 * @author 2dam
 */
public class Movement implements Serializable{
    private long movementId;
    private long accountId;
    private double ammount;
    private double balance;
    private String description;
    private Timestamp databaseDate;

    //CONSTRUCTOR VACIO
    public Movement() {
    }

    //CONSTRUCTOR SIN PARAMETROS
    public Movement(long movementId, long accountId, double ammount, double balance, String description, Timestamp databaseDate) {
        this.movementId = movementId;
        this.accountId = accountId;
        this.ammount = ammount;
        this.balance = balance;
        this.description = description;
        this.databaseDate = databaseDate;
    }
    
    public void setDatos(){
        System.out.println("Introduce la cantidad: ");
        this.ammount = ejercicioBanco.utilidades.Utilidades.leerDouble(0,5000);
    }
    
    public void getDatos(){
        
    }

    public long getMovementId() {
        return movementId;
    }

    public long getAccountId() {
        return accountId;
    }

    public double getAmmount() {
        return ammount;
    }

    public double getBalance() {
        return balance;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDatabaseDate() {
        return databaseDate;
    }

    public void setMovementId(long movementId) {
        this.movementId = movementId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDatabaseDate(Timestamp databaseDate) {
        this.databaseDate = databaseDate;
    }
    
    
    
}
