/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.excepciones;

/**
 * This class is thrown when a customer search operation is executed and the customer is not found.
 * @author Endika Ubierna.
 */
public class CustomerNotExistException extends Exception{
    /**
     * Class constructor. Calls the super class constructor. In this case the superclass is {@link Exception}
     */
    public CustomerNotExistException(){
        super("The customer is not registered.");
    }    
}
