/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.clases;

import java.io.Serializable;

/**
 * Asocia las cuentas de los clientes del banco.
 * @author 2dam
 */
public class CustomerAccount implements Serializable{
    
    private long accountId;
    private long customerId;

    //CONSTRUCTOR
    public CustomerAccount() {
    }

    public CustomerAccount(long accountId, long customerId) {
        this.accountId = accountId;
        this.customerId = customerId;
    }
    
    public void getDatos(){
        
    }
    
    //GETTER Y SETTER
    public long getAccountId() {
        return accountId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    
    
}
