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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class has the methods with the actions required against the database in the bank manager project.
 * @author Endika Ubierna.
 */
public class DaoImplementation implements Dao{
    /**
     * Connection object. This object creates a new connection with the SQL database.
     */
    private Connection con = null;
    /**
     * Statement class object. This object is used to executed the queries and actions against the database.
     */
    private PreparedStatement stmt = null;
    /**
     * This object reads a Java properties file information.
     */   
    private ResourceBundle fichero;
    
    //Estas variables son querys a la BBDD que vamos a usar luego. Mejor aqui para que el codigo sea mas limpio.
    private final String insertarCustomer = "Insert into customer (city,email,firstName,lastName,middleInitial,"
               + "phone,state,street,zip) values (?,?,?,?,?,?,?,?,?)";
    private final String buscarCustomerEmail = "Select * from customer where email = ?";
    private final String selectCustomer = "Select * from customer where email = ?";
    private final String consultarCuentasDeUnCliente = "select * from account where id in"
            + "(select accounts_id from customer_account where customers_id = ? )";
    private final String insertCustomerAccount = "Insert into customer_account values (?,?)";
    private final String crearCuentaInsert = "Insert into account (balance,beginBalance,beginBalanceTimestamp,creditLine"
               + ",description,type) values (?,?,?,?,?,?)";
    private final String insertarMovimiento = "Insert into movement (amount,balance,description,timestamp,account_id) values"
               + "(?,?,?,?,?)";
    private final String updateMovimientoPago = "Update account set balance = balance - ? where id = ?";
    private final String updateMovimienteIngreso = "Update account set balance = balance + ? where id = ?";
    private final String listarTodosCustomer = "Select * from customer";
    private final String selectCuenta = "Select * from account where id = ?";
    private final String selectMovementsAccount = "Select * from movement where account_id = ?";
   
    /**
     * Class constructor.
     */
    public DaoImplementation() {
    }

    /**
     * Opens a new database connection.
     */
    private void openConnection() {
        fichero = ResourceBundle.getBundle("ejercicioBanco.controlador.config");
        //dbBanco = fichero.getString("DB");
        String url = fichero.getString("Conn");
        String user = fichero.getString("DBUser");
        String passwd = fichero.getString("DBPass");
        String driver = fichero.getString("Driver");
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, passwd);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No se conecta");
        }
    }
    /**
     * Close database connection.
     * @throws SQLException A SQL exception.
     */
    private void closeConnection() throws SQLException {
        stmt.close();
        con.close();
    }

    /**
     * Creates a new bank customer.
     * @param customer The new customer.
     * @return The client id.
     * @throws Exception A generic exception.
     * @throws EmailExistException Exception thrown when the email is registered.
     */     
    @Override
    public long crearNuevoCliente(Customer customer) throws Exception, EmailExistException {
        long idGeneradoCliente = 0;
        try {  
            //Vamos a devolver el id generado por SQL al cliente. Para eso usamos la variable idGeneradoCliente
            openConnection();
            //Primero mirar si el email está registrado
            stmt = con.prepareStatement(buscarCustomerEmail);
            //Metemeos el email donde en la query hemos puesto el signo ?
            stmt.setString(1, customer.getEmail());
            //Ejecutar la query
            ResultSet rs = stmt.executeQuery();
            //Mirar los resultados. Si ha devuelto algo, el email existe lanzar el error
            if (rs.next()) {
                throw new EmailExistException();
            }
            //Si no se ha lanzado la excepción el email no está y hay que hacer el registro.
            stmt = con.prepareStatement(insertarCustomer, stmt.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getCity());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getFirstName());
            stmt.setString(4, customer.getLastName());
            stmt.setString(5, customer.getLastName().toUpperCase().substring(0, 1).concat("."));
            stmt.setLong(6, customer.getPhone());
            stmt.setString(7, customer.getState());
            stmt.setString(8, customer.getStreet());
            stmt.setInt(9, customer.getZip());
            stmt.executeUpdate();
            //Ahora guardamos el id en la variable para devolver el id del nuevo cliente.
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGeneradoCliente = rs.getLong(1);
            }
            closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error SQL.");
        }
        return idGeneradoCliente;
    }
    /**
     * Gets the client searched by email.
     * @throws Exception A generic exception.
     */
    @Override
    public Customer consultarCliente(String email) throws  Exception,CustomerNotExistException {
        Customer unCustomer = null;
        try {    
            openConnection();
            stmt = con.prepareStatement(selectCustomer);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                unCustomer = new Customer();
                unCustomer.setCity(rs.getString("city"));
                unCustomer.setCustomerId(rs.getLong("id"));
                unCustomer.setEmail(email);
                unCustomer.setFirstName(rs.getString("firstName"));
                unCustomer.setLastName(rs.getString("lastName"));
                unCustomer.setMiddleInitial(rs.getString("middleInitial"));
                unCustomer.setStreet(rs.getString("street"));
                unCustomer.setPhone(rs.getLong("phone"));
                unCustomer.setZip(rs.getInt("zip"));
                unCustomer.setState(rs.getString("state"));
            } else {
                throw new CustomerNotExistException();
            }
            closeConnection();   
        } catch (SQLException e) {
            System.out.println("Error SQL.");
        }
        return unCustomer;
    }
    /**
     * This method gets the accounts of a customer.
     * @param email A customer email.
     * @throws Exception A generic exception.
     */
    @Override
    public List<Account> consultarCuentasCliente(String email)throws Exception {
        ArrayList<Account> cuentasCliente = new ArrayList<>();
        try{    
            Customer customer = null;
            Account account = null;
            //Pedir al metodo de consultar cliente que nos devuelva el cliente
            customer = consultarCliente(email);
            openConnection();
            stmt = con.prepareStatement(consultarCuentasDeUnCliente);
            stmt.setLong(1, customer.getCustomerId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getLong("id"));
                account.setBalance(rs.getDouble("balance"));
                account.setBeginBalance(rs.getDouble("beginBalance"));
                account.setBeginBalanceTimestamp(rs.getTimestamp("beginBalanceTimestamp"));
                account.setCreditLine(rs.getDouble("creditLine"));
                account.setDescription(rs.getString("description"));
                account.setType(rs.getInt("type"));
                cuentasCliente.add(account);
            }
            closeConnection();
                  
        } catch (SQLException e) {
            System.out.println("Error SQL.");
        }
        return cuentasCliente;    
    }
    
    /**
     * Creates a new <code>Account</code>. The account is asociated to a bank {@link Customer}.
     * @param customer A customer.
     * @param account An account.
     * @throws Exception A generic exception.
     */
    @Override
    public void crearCuenta(Customer customer,Account account)throws Exception {
        Long accountId = null;
        openConnection();
        //Crear la nueva cuenta
        stmt = con.prepareStatement(crearCuentaInsert,stmt.RETURN_GENERATED_KEYS);
        stmt.setDouble(1,account.getBalance());
        stmt.setDouble(2,account.getBeginBalance());
        stmt.setTimestamp(3,account.getBeginBalanceTimestamp());
        stmt.setDouble(4, account.getCreditLine());
        stmt.setString(5, account.getDescription());
        stmt.setInt(6, account.getType());
        //Ejecutar el insert
        stmt.executeUpdate();
        //Recoger el id que se ha generado de la cuenta
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
            accountId = rs.getLong(1);
        }
        //Ahora inserta los datos de las pk de cuenta y cliente en la tabla relacional customer account.
        stmt = con.prepareStatement(insertCustomerAccount);
        stmt.setLong(1,customer.getCustomerId());
        stmt.setLong(2,accountId);
        stmt.executeUpdate();
        closeConnection();         
    }
    /**
     * Adds a new <code>Customer</code> to an {@link Account}.
     * @param account An <code>Account</code>.
     * @param customer A <code>Customer</code>.
     * @throws Exception A generic exception.
     */
    @Override
    public void agregarClienteCuenta(Account account,Customer customer)throws Exception{
        Long accountId = null;
        Long customerId = null;
        Boolean registrada = false;
        try{
            openConnection();
            stmt = con.prepareStatement(consultarCuentasDeUnCliente);
            stmt.setLong(1, customer.getCustomerId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                accountId = rs.getLong(1);
                if(accountId == account.getAccountId()){
                    System.out.println("La cuenta "+account.getAccountId()+" y el cliente "+customer.getEmail()+" ya esta registrada.");
                    registrada = true;
                }
            }
            if(!registrada){
                stmt = con.prepareStatement(insertCustomerAccount);
                stmt.setLong(1,customer.getCustomerId());
                stmt.setLong(2,account.getAccountId());
                stmt.executeUpdate();
            }
            closeConnection();
        }catch(SQLException e){
            System.out.println("No se ha podido actualizar");
        }
    }
    /**
     * Gets an Account information.
     * @param idCuenta An <code>Account</code> id.
     * @throws Exception A generic exception.
     * @throws AccountNotExistException Exception thrown when the account don´t exist.
     * @return An account which id is the one passed as parameter.
     */
    @Override
    public Account consultarDatosCuenta(Long idCuenta) throws Exception,AccountNotExistException{
        Account account = null;
        openConnection ();
        stmt = con.prepareStatement(selectCuenta);
        stmt.setLong(1,idCuenta);
        ResultSet rs = stmt.executeQuery(); 
        if(!rs.next())
            throw new AccountNotExistException();
        else{
            account = new Account();
            account.setAccountId(rs.getLong("id"));
            account.setBalance(rs.getDouble("balance"));
            account.setBeginBalance(rs.getDouble("beginBalance"));
            account.setBeginBalanceTimestamp(rs.getTimestamp("beginBalanceTimestamp"));
            account.setCreditLine(rs.getDouble("creditLine"));
            account.setDescription(rs.getString("description"));
            account.setType(rs.getInt("type"));
       }
        closeConnection(); 
        return account;
    }
    /**
     * Creates a <code>Movement</code> into an <code>Account</code>
     * @param movement The movement to be registered.
     * @param account The account subject from the movement.
     * @throws Exception A generic exception.
     */
    @Override
    public void movimientoCuenta(Movement movement,Account account)throws Exception {
        try {
            openConnection();
            stmt = con.prepareStatement(insertarMovimiento, stmt.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, movement.getAmmount());
            stmt.setDouble(2, movement.getBalance());
            stmt.setString(3, movement.getDescription());
            stmt.setTimestamp(4, movement.getDatabaseDate());
            stmt.setLong(5, movement.getAccountId());
            stmt.executeUpdate();
            //Ahora actualizar el balance de la cuenta coger la descripcion para sumar o restar balance cuenta amoount
            if (movement.getDescription().equalsIgnoreCase("Deposit")) {
                account.setBalance(account.getBalance() + movement.getAmmount());
                stmt = con.prepareStatement(updateMovimienteIngreso);
            } else {
                account.setBalance(account.getBalance() - movement.getAmmount());
                stmt = con.prepareStatement(updateMovimientoPago);
            }
            stmt.setDouble(1, movement.getAmmount());
            stmt.setDouble(2, account.getAccountId());
            stmt.executeUpdate();
            System.out.println("La cuenta "+account.getAccountId()+" se actualizo. Saldo actual = "+account.getBalance());
            closeConnection();
        } catch (SQLException e) {
            System.out.println("Error SQL.");
        }
    }

    /**
     * Gets the information of movements from an <code>Account</code>.
     * @param account An account.
     * @throws Exception A generic exception.
     * @return A list with an account movements.
     */
    @Override
    public List<Movement> consultarMovimientos(Account account) throws Exception {
        List<Movement> movimentosCuenta = new ArrayList<>();
        try {
            openConnection();
            //Preparar la query
            stmt = con.prepareStatement(selectMovementsAccount);
            stmt.setLong(1, account.getAccountId());
            ResultSet rs = stmt.executeQuery();
            //Recoger los datos de la query
            Movement movement = null;
            while (rs.next()) {
                //Importante hacer el new cada vez en el bucle. Si se hace fuera se copian encima los datos.
                movement = new Movement();
                movement.setAccountId(rs.getLong("account_id"));
                movement.setAmmount(rs.getDouble("amount"));
                movement.setDescription(rs.getString("description"));
                movement.setBalance(rs.getDouble("balance"));
                movement.setDatabaseDate(rs.getTimestamp("timestamp"));
                movement.setMovementId(rs.getLong("id"));
                //Añadir a la lista
                movimentosCuenta.add(movement);
            }
            closeConnection();
        } catch (SQLException e) {
            System.out.println("Error SQL.");
        }
        return movimentosCuenta;
    }
    /**
     * Gets a list of the bank {@link Customer}.
     */
    @Override
    public List<Customer> listarCustomers() throws Exception{
        //
        ArrayList<Customer> customers = new ArrayList<>();
        Customer customer = null;
        openConnection ();
        stmt = con.prepareStatement(listarTodosCustomer);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            customer = new Customer();
            customer.setCity(rs.getString("city"));
            customer.setCustomerId(rs.getLong("id"));
            customer.setEmail(rs.getString("email"));
            customer.setFirstName(rs.getString("firstName"));
            customer.setLastName(rs.getString("lastName"));
            customer.setMiddleInitial(rs.getString("middleInitial"));
            customer.setStreet(rs.getString("street"));
            customer.setPhone(rs.getLong("phone"));
            customer.setZip(rs.getInt("zip"));
            customer.setState(rs.getString("state"));
            customers.add(customer);
        }       
        closeConnection();
        return customers;        
    }
}
