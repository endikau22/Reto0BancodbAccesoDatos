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
    
    /**
     * Introducir los datos de un movimiento.
     */
    
    public void setDatos(){
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
        System.out.println("Introduce el id de la cuenta bancaria ");
        this.accountId = (long) Utilidades.leerInt();     
    }
    
    /**
     * Muestra la información de un movimiento
     */
    
    public void getDatos(){
        System.out.println("Movimiento: "+this.movementId);
        System.out.println("Descripcion: "+this.description);
        System.out.println("Balance: "+this.balance);
        System.out.println("Cantidad: "+this.ammount);
        System.out.println("Tipo de movimiento: "+this.description);
        System.out.println("Fecha movimiento: "+this.databaseDate);        
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

    public void setDatos(long idCuenta) {
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
        this.accountId = idCuenta;     
    }
    
    
    
}
