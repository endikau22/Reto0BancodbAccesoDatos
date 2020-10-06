/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.controlador;

import ejercicioBanco.clases.Account;
import ejercicioBanco.clases.Customer;
import ejercicioBanco.clases.Movement;
import ejercicioBanco.utilidades.Utilidades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Encargada de conectar nuestra aplicación con la base de datos.
 * @author 2dam
 */
public class Dao {
    private Connection con = null;
    private PreparedStatement stmt = null;
    
    
    private ResourceBundle fichero;
    //private String dbBanco;
    private String url;
    private String user;
    private String passwd;
    private String driver;
   
    /**
     * Constructor de la clase, Lee datos de un fichero de propiedades por medio 
     * del método estático de la clase ResourceBundle. Guarda la información en variables.
     */
    public Dao() {
       fichero = ResourceBundle.getBundle("ejercicioBanco.controlador.config");
       //dbBanco = fichero.getString("DB");
       url = fichero.getString("Conn");
       user = fichero.getString("DBUser");
       passwd = fichero.getString("DBPass");
       driver = fichero.getString("Driver");
    }
    
    /**
     * Abre una conexión con la BBDD. 
     */
	private void openConnection() {
            try{
                Class.forName(driver);
		con = DriverManager.getConnection(url,user,passwd);
            }catch(Exception e){
                System.out.println("No se conecta");
            }
	}

	private void closeConnection() throws SQLException {
		stmt.close();
		con.close();
	}

    /**
     * Inserta un nuevo cliente en la base de datos del banco.
     * @throws Exception 
     */
    public void crearNuevoCliente() throws Exception {
       Customer oneCustomer = new Customer();
       oneCustomer.setDatos();
    
       String insert = "Insert into customer (city,email,firstName,lastName,middleInitial,"
               + "phone,state,street,zip) values (?,?,?,?,?,?,?,?,?)";
       
       
       openConnection();
                stmt = con.prepareStatement(insert);
                stmt.setString(1,oneCustomer.getCity());
                stmt.setString(2,oneCustomer.getEmail());
                stmt.setString(3,oneCustomer.getFirstName());
                stmt.setString(4,oneCustomer.getLastName());
                stmt.setString(5,oneCustomer.getLastName().toUpperCase().substring(0, 1).concat("."));
                stmt.setLong(6, oneCustomer.getPhone());
                stmt.setString(7, oneCustomer.getState());
                stmt.setString(8, oneCustomer.getStreet());
                stmt.setInt(9, oneCustomer.getZip());

                stmt.executeUpdate();
       
       closeConnection();
    }
    /**
     * Consultar los datos de un cliente.
     * @throws Exception 
     */
    public void consultarCliente()throws Exception {
        long id = clienteAConsultar();
        String select = "Select * from customer where id = ?";
        openConnection();
        stmt = con.prepareStatement(select);
        stmt.setLong(1,id);
        ResultSet rs = stmt.executeQuery();
        Customer unCustomer = new Customer();
        if(rs.next()){//Está el cliente con el id introducido
            unCustomer.setCity(rs.getString("city"));
            unCustomer.setCustomerId(rs.getLong("id"));
            unCustomer.setEmail(rs.getString("email"));
            unCustomer.setFirstName(rs.getString("firstName"));
            unCustomer.setLastName(rs.getString("lastName"));
            unCustomer.setMiddleInitial(rs.getString("middleInitial"));
            unCustomer.setStreet(rs.getString("street"));
            unCustomer.setPhone(rs.getLong("phone"));
            unCustomer.setZip(rs.getInt("zip"));
            unCustomer.setState(rs.getString("state"));
            
            unCustomer.getDatos();
        }else
            System.out.println("No se ha encontrado el cliente.");
        rs.close();
        closeConnection();
    }
    /**
     * Consultar los datos de las cuentas de un cliente
     * @throws Exception 
     */
    public void consultarCuentasCliente()throws Exception {
        long id;
        String select;
        
        System.out.println("Introduce el ID del cliente");
        id = (long) ejercicioBanco.utilidades.Utilidades.leerInt();
        select = "select * from account where id in(select accounts_id from customer_account where customers_id = ? )";
        
        openConnection ();
        
        stmt = con.prepareStatement(select);
        stmt.setLong(1,id);
        ResultSet rs = stmt.executeQuery();
        
        Account acc = new Account();
        if(!rs.next())
            System.out.println("El cliente no tiene cuentas");
        else{
            while(rs.next()){
                 acc.setAccountId(rs.getLong("id"));
                 acc.setBalance(rs.getDouble("balance"));
                 acc.setBeginBalance(rs.getDouble("beginBalance"));
                 acc.setBeginBalanceTimestamp(rs.getTimestamp("beginBalanceTimestamp"));
                 acc.setCreditLine(rs.getDouble("creditLine"));
                 acc.setDescription(rs.getString("description"));
                 acc.setType(rs.getInt("type"));
             
                acc.getDatos();
         }
        }
    }
    
    /**
     * Crear una cuenta nueva y asociar la cuenta con un cliente en la tabla CustomerAccount
     * @throws Exception 
     */
    public void crearCuenta()throws Exception {       
        openConnection();
        System.out.println("Vamos a crear una cuenta.");
        Account account = new Account();
        account.setDatos();
    
        String insert = "Insert into account (balance,beginBalance,beginBalanceTimestamp,creditLine"
               + ",description,type) values (?,?,?,?,?,?)";
        openConnection();
        
        stmt = con.prepareStatement(insert);
        stmt.setDouble(1,account.getBalance());
        stmt.setDouble(2,account.getBeginBalance());
        stmt.setTimestamp(3,account.getBeginBalanceTimestamp());
        stmt.setDouble(4, account.getCreditLine());
        stmt.setString(5, account.getDescription());
        stmt.setInt(6, account.getType());
        
        stmt.executeUpdate();
        
        System.out.println("Introduce el id del cliente al que añadir a la cuenta creada: ");
        long customerId = (long) Utilidades.leerInt();
        /*
        String select = "Select * from customer where id = ?";
        stmt = con.prepareStatement(select);
        stmt.setLong(1, customerId);
        ResultSet rs = stmt.executeQuery();
        */
        //Ahora customerAccount actualizar;Este me dice que la cuenta no esta metida  pero si esta no se 
        actualizarCustomerAccount(customerId,account.getAccountId());
     
        closeConnection();
    }
    /**
     * Agregar un cliente a una cuenta ya existente
     * @throws Exception 
     */
    public void agregarClienteCuenta()throws Exception {
        long idCliente, idCuenta;
        boolean hayCuenta,hayCliente;
        System.out.println("Introduce el id del cliente: ");
        idCliente = (long) Utilidades.leerDouble();
        System.out.println("Introduce el id de la cuenta: ");
        idCuenta  = (long) Utilidades.leerDouble();
       
        hayCliente = existeCliente(idCliente);
        hayCuenta = existeCuenta(idCuenta);
        if(hayCliente && hayCuenta){
            String insert = "Insert into customer_account values(?,?)";
            openConnection();
            stmt=con.prepareStatement(insert);
            stmt.setLong(1,idCuenta);
            stmt.setLong(2,idCliente);
        
            stmt.executeUpdate();   
        }else{
            System.out.println("No se ha podido realizar la operacion");
        }   
    }
    /**
     * Consultar datos de una cuenta.
     * @throws Exception 
     */
    public void consultarDatosCuenta() throws Exception{
        long idCuenta;
        
        System.out.println("Introduce el id de la cuenta: ");
        idCuenta  = (long) Utilidades.leerDouble();
        
        String select = "Select * from account where id = ?";
        openConnection ();
        
        stmt = con.prepareStatement(select);
        stmt.setLong(1,idCuenta);
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next())
            System.out.println("La cuenta "+idCuenta+" no existe");
        else{
            Account acc = new Account();
            
            acc.setAccountId(rs.getLong("id"));
            acc.setBalance(rs.getDouble("balance"));
            acc.setBeginBalance(rs.getDouble("beginBalance"));
            acc.setBeginBalanceTimestamp(rs.getTimestamp("beginBalanceTimestamp"));
            acc.setCreditLine(rs.getDouble("creditLine"));
            acc.setDescription(rs.getString("description"));
            acc.setType(rs.getInt("type"));
             
            acc.getDatos();
       }
    }
    /**
     * Realizar un movimiento a una cuenta
     * @throws Exception 
     */
    public void movimientoCuenta()throws Exception {
       long idCuenta;
        
       System.out.println("Introduce el id de la cuenta: ");
       idCuenta  = (long) Utilidades.leerDouble();
       
       Movement aMovement = new Movement();
       aMovement.setDatos(idCuenta);
       
       String insert = "Insert into movement (amount,balance,description,account_id) values"
               + "(?,?,?,?)";
       openConnection();
       stmt = con.prepareStatement(insert);
       stmt.setDouble(1, aMovement.getAmmount());
       stmt.setDouble(2, aMovement.getAmmount());
       stmt.setString(3, aMovement.getDescription());
       stmt.setLong(4, aMovement.getAccountId());
       
       stmt.executeUpdate();
       //AHora actualizar el balance de la cuenta coger la descripcion para sumar o restar balance cuenta amoount
       String update;
       if(aMovement.getDescription().equalsIgnoreCase("Payment")){
            update = "Update account set balance = balance - ? where id = ?";
       }else
           update = "Update account set balance = balance + ? where id = ?";
       stmt = con.prepareStatement(update);
       stmt.setDouble(1, aMovement.getAmmount());
       stmt.setLong(2, idCuenta);
       
       stmt.executeUpdate();
    }

    /**
     * Consultar los movimientos de una cuenta
     * @throws Exception 
     */
    public void consultarMovimientos() throws Exception{
       long idCuenta;
        
       System.out.println("Introduce el id de la cuenta: ");
       idCuenta  = (long) Utilidades.leerDouble();
       
       String select = "Select * from movement where account_id = ?";
       openConnection ();
       stmt = con.prepareStatement(select);
       stmt.setLong(1, idCuenta);
       
       ResultSet rs = stmt.executeQuery();
       Movement oneMovement = new Movement();
       if(!rs.next())
            System.out.println("La cuenta "+idCuenta+" no ha realizado ningun movimiento");
       else{
         while (rs.next()){
           oneMovement.setAccountId(rs.getLong("account_id"));
           oneMovement.setAmmount(rs.getDouble("amount"));
           oneMovement.setDescription(rs.getString("description"));
           oneMovement.setBalance(rs.getDouble("balance"));
           oneMovement.setDatabaseDate(rs.getTimestamp("timestamp"));
           oneMovement.setMovementId(rs.getLong("id"));
           
           oneMovement.getDatos();
         }
       }
       
        closeConnection();
    }
/**
 * Método que es invocado por el método crear nuevo cliente.
 * Pide datos de un usuario, utilizando el método setDatos() de la clase Customer.
 * @return Un string, el insert into.
 */
    private String usuarioNuevo() {
       Customer oneCustomer = new Customer();
       oneCustomer.setDatos();
       long id = oneCustomer.getCustomerId();
       String ciudad = oneCustomer.getCity();
       String email = oneCustomer.getEmail();
       String nombre = oneCustomer.getFirstName();
       String apellido = oneCustomer.getLastName();
       String inicial = oneCustomer.getLastName().toUpperCase().substring(0, 1).concat(".");
       long telefono = oneCustomer.getPhone();
       String provincia = oneCustomer.getState();
       String calle = oneCustomer.getStreet();
       int codigoPostal = oneCustomer.getZip();
       String insert = "Insert into customer (id,city,email,firstName,lastName,middleInitial,"
               + "phone,state,street,zip) values (?,?,?,?,?,?,?,?,?,?)";
       return insert;
    }
/**
 * Pedir por consola el apellido de un cliente. Utilizado en el método consultar cliente.
 * @return El apellido de un cliente 
 */
    private long clienteAConsultar(){
        long id;
        
        System.out.println("Introduce el DNI del cliente que buscas");
        id = ejercicioBanco.utilidades.Utilidades.leerInt();
        return id;
    }

    private long devolverIdCliente() throws Exception{
        String select = "Select * from customer"; 
        long idCustomer = 0;
        openConnection();
        stmt = con.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        Customer unCustomer = new Customer();
        while(rs.next()){
            idCustomer = rs.getLong("id");
        }
        rs.close();
        closeConnection();
        return idCustomer;
    }

    private boolean existeCliente(long idCliente) {
        boolean esta = false;
        String select = "Select * from customer where id = ?";
        try{
            openConnection();
            stmt = con.prepareStatement(select);
            stmt.setLong(1,idCliente);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())//Está el cliente con el id introducido
                esta = true;
        }catch(Exception e){
            e.printStackTrace();
        }     
        return esta;
    }

    private boolean existeCuenta(long idCuenta) {
        boolean esta = false;
        String select = "Select * from account where id = ?";
        try{
            openConnection();
            stmt = con.prepareStatement(select);
            stmt.setLong(1,idCuenta);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())//Está el cliente con el id introducido
                esta = true;
        }catch(Exception e){
            e.printStackTrace();
        }     
        return esta;
    }

    private void actualizarCustomerAccount(long customerId, long accountId) {
        try{
            openConnection();
            String insertCustomerAccount = "Insert into customer_account values (?,?)";
            stmt = con.prepareStatement(insertCustomerAccount);
            stmt.setLong(1,customerId);
            stmt.setLong(2,accountId);
            stmt.executeUpdate();
            closeConnection();
        }catch(Exception e){
            System.out.println("No se ha podido actualizar");
        }
    }
}
