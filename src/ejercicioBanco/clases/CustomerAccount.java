/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.clases;

import java.io.Serializable;

/**
 * This class contains the relation between {@link Customer} and {@link Account}. It is formed
 * with the ids from both this classes. 
 * @author Endika Ubierna.
 */
public class CustomerAccount implements Serializable{
    /**
     * The account id.
     */
    private long accountId;
    /**
     * The customer id.
     */
    private long customerId;

    /**
     * Class constructo without parameters.
     */
    public CustomerAccount() {
    }
    /**
     * Class constructor with parameters.
     * @param accountId The account id.
     * @param customerId The customer id.
     */
    public CustomerAccount(long accountId, long customerId) {
        this.accountId = accountId;
        this.customerId = customerId;
    }
    //GETTER Y SETTER
    /**
     * Gets the account id.
     * @return The account id.
     */
    public long getAccountId() {
        return accountId;
    }
    /**
     * Gets the customer id.
     * @return The customer id.
     */
    public long getCustomerId() {
        return customerId;
    }
    /**
     * Sets the account id.
     * @param accountId The account id.
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    /**
     * Sets the customer id.
     * @param customerId The customer id.
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }  
}
