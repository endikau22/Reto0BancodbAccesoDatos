/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.excepciones;

/**
 * This class is thrown when an account search operation is executed and the account is not found.
 * @author Endika Ubierna.
 */
public class AccountNotExistException extends Exception{
    /**
     * Class constructor. Calls the super class constructor. In this case the superclass is {@link Exception}
     */
    public AccountNotExistException(){
        super("The account you are searching for cannot be find.");
    }
}
