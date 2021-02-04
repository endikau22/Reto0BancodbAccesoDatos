/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.excepciones;

/**
 * This class is thrown when a new user creation operation is executed and the email is stored in the database.
 * @author Endika Ubierna.
 */
public class EmailExistException extends Exception {
    /**
     * Class constructor. Calls the super class constructor. In this case the superclass is {@link Exception}
     */
    public EmailExistException(){
        super("The email already exists.");
    }
}
