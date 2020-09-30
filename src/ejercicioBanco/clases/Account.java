package ejercicioBanco.clases;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * La clase Account se utiliza para leer y escribir cuentas bancarias en una base de datos de un Banco.
 * @version 1.0
 * @author 2dam
 * 
 */
public class Account implements Serializable{
    /**identificador de la cuenta*/
    private long accountId;
    /**descripcion de la cuenta*/
    private String description;
    /**identificador de la cuenta*/
    private double balance;
    private double creditLine;
    private double beginBalance;
    private LocalDate beginBalanceTimestamp;//o LOCALDATE o Timestamp
    /**Tipo de la cuenta*/
    private int type;
    
    /**CONSTRUCTOR VACIO*/
     public Account() {
    }
    /**Este constructor recibe todos los parametros de los atributos de la clas
     * @param accountId
     * @param description
     * @param beginBalance
     * @param creditLine
     * @param balance
     * @param beginBalanceTimestamp
     * @param type*/
    public Account(long accountId, String description, double balance, double creditLine, double beginBalance, LocalDate beginBalanceTimestamp, int type) {
        this.accountId = accountId;
        this.description = description;
        this.balance = balance;
        this.creditLine = creditLine;
        this.beginBalance = beginBalance;
        this.beginBalanceTimestamp = beginBalanceTimestamp;
        this.type = type;
    }
    /**
     * Introducir datos de una cuenta.
     */
    public void setDatos(){
        System.out.println("Introduce id de la cuenta: ");
        this.accountId = ejercicioBanco.utilidades.Utilidades.leerInt();
        System.out.println("Introduce la descripcion de la cuenta: ");
        this.description = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        System.out.println("Introduce balance inicial: ");
        this.beginBalance = ejercicioBanco.utilidades.Utilidades.leerDouble();
    }
     
    //GETTERS Y SETTERS

    /**
     *
     * @return un numero con el identificador de la cuenta
     */

    public long getAccountId() {
        return accountId;
    }

    /**
     *
     * @return La descripcion de la cuenta
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return El balance de la cuenta.
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     * @return La linea de credito de la cuenta
     */
    public double getCreditLine() {
        return creditLine;
    }

    /**
     *
     * @return 
     */
    public double getBeginBalance() {
        return beginBalance;
    }

    /**
     *
     * @return La fecha de inicio de la cuenta.
     */
    public LocalDate getBeginBalanceTimestamp() {
        return beginBalanceTimestamp;
    }

    /**
     *
     * @return El tipo de la cuenta.
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @param accountId
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @param balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     *
     * @param creditLine
     */
    public void setCreditLine(double creditLine) {
        this.creditLine = creditLine;
    }

    /**
     *
     * @param beginBalance
     */
    public void setBeginBalance(double beginBalance) {
        this.beginBalance = beginBalance;
    }

    /**
     *
     * @param beginBalanceTimestamp
     */
    public void setBeginBalanceTimestamp(LocalDate beginBalanceTimestamp) {
        this.beginBalanceTimestamp = beginBalanceTimestamp;
    }

    /**
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

     
}

