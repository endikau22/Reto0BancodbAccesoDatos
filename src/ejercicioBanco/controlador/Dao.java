/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.controlador;

import ejercicioBanco.clases.Account;
import ejercicioBanco.clases.Customer;
import ejercicioBanco.clases.Movement;
import ejercicioBanco.excepciones.AccountNotExistException;
import ejercicioBanco.excepciones.CustomerNotExistException;
import ejercicioBanco.excepciones.EmailExistException;
import java.util.List;

/**
 * Dao interface. Contains the methods for the bank management. The information is stored in a SQL database.
 * The following methods excute queries agaist the database.
 * @author Endika Ubierna.
 */
public interface Dao{
    /**
     * Creates a new bank customer.
     * @param customer The new customer.
     * @return The client id.
     * @throws Exception A generic exception.
     * @throws EmailExistException Thrown when the email is registered.
     */
    public long crearNuevoCliente(Customer customer) throws Exception,EmailExistException;
    /**
     * Gets a {@link Customer} information.
     * @param email The customer email.
     * @return A <code>Customer</code>.
     * @throws Exception A generic exception.
     * @throws CustomerNotExistException Thrown when the customer is not registered.
     */
    public Customer consultarCliente(String email) throws Exception,CustomerNotExistException;
    /**
     * Gets all the accounts of a {@link Customer}.
     * @param email The customer email.
     * @return A List of accounts.
     * @throws Exception A generic exception.
     */
    public List<Account> consultarCuentasCliente(String email)throws Exception;
    /**
     * Creates a new <code>Account</code>. The account is asociated to a bank {@link Customer}.
     * @param customer A customer.
     * @param account An account.
     * @throws Exception A generic exception.
     */
    public void crearCuenta(Customer customer,Account account)throws Exception;
    /**
     * Adds a new <code>Customer</code> to an {@link Account}.
     * @param account An <code>Account</code>.
     * @param customer A <code>Customer</code>.
     * @throws Exception A generic exception.
     */
    public void agregarClienteCuenta(Account account,Customer customer)throws Exception;
    /**
     * Gets an Account information.
     * @param idCuenta An <code>Account</code> id.
     * @throws Exception A generic exception.
     * @throws AccountNotExistException Exception thrown when the account don´t exist.
     * @return An account which id is the one passed as parameter.
     */
    public Account consultarDatosCuenta(Long idCuenta) throws Exception,AccountNotExistException;
    /**
     * Creates a <code>Movement</code> into an <code>Account</code>
     * @param movement The movement to be registered.
     * @param account The account subject from the movement.
     * @throws Exception A generic exception.
     */
    public void movimientoCuenta(Movement movement,Account account)throws Exception;
    /**
     * Gets the information of movements from an <code>Account</code>.
     * @param account An account.
     * @throws Exception A generic exception.
     * @return A list with an account movements.
     */
    public List<Movement> consultarMovimientos(Account account) throws Exception;
    /**
     * Gets a list of the bank {@link Customer}.
     * @return A <code>List</code> of {@link Customer}.
     * @throws Exception A generic exception.
     */
    public List<Customer> listarCustomers()throws Exception;
    
}
