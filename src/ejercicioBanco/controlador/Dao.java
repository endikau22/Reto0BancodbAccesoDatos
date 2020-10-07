
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
    
    //Estas variables son querys a la BBDD que vamos a usar luego
    private final String insertarCustomer = "Insert into customer (city,email,firstName,lastName,middleInitial,"
               + "phone,state,street,zip) values (?,?,?,?,?,?,?,?,?)";
    private final String selectCustomer = "Select * from customer where id = ?";
    private final String consultarCuentasDeUnCliente = "select * from account where id in"
            + "(select accounts_id from customer_account where customers_id = ? )";
    private final String crearCuentaInsert = "Insert into account (balance,beginBalance,beginBalanceTimestamp,creditLine"
               + ",description,type) values (?,?,?,?,?,?)";
    private final String insertarMovimiento = "Insert into movement (amount,balance,description,account_id) values"
               + "(?,?,?,?)";
   
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
     * @return Un long Variable que identifica a un cliente
     * @throws Exception 
     */
    public long crearNuevoCliente() throws Exception {
       Customer oneCustomer = new Customer();
       oneCustomer.setDatos();
       long idGeneradoCliente= 0;
      //Vamos a devolver el id generado por SQL al cliente. Para eso usamos la variable idGeneradoCliente
       openConnection();
       //Necesitamos al ser un into generar las keys para recuperar el id
       idGeneradoCliente = introducirClienteBBDD(oneCustomer);
               
       closeConnection();
       return idGeneradoCliente;
    }
    /**
     * Consultar los datos de un cliente.
     * @throws Exception 
     */
    public void consultarCliente()throws Exception {
        long id = clienteAConsultar();
  
        openConnection();
        stmt = con.prepareStatement(selectCustomer);
        stmt.setLong(1,id);
        
        ResultSet rs = stmt.executeQuery();
        mostarDatosCliente(rs);
        
        closeConnection();
    }
    /**
     * Consultar los datos de las cuentas de un cliente
     * @throws Exception 
     */
    public void consultarCuentasCliente()throws Exception {
        long id;//Variable para guardar el id del cliente que pedimos por pantalla para buscar en la bbdd.
        
        System.out.println("Introduce el ID del cliente");
        id = (long) ejercicioBanco.utilidades.Utilidades.leerInt();
              
        openConnection ();
        
        stmt = con.prepareStatement(consultarCuentasDeUnCliente);
        stmt.setLong(1,id);
        ResultSet rs = stmt.executeQuery();
        
        mostarCuentasCliente(rs); 
        
        closeConnection();
    }
    
    /**
     * Crear una cuenta nueva y asociar la cuenta con un cliente en la tabla CustomerAccount
     * @throws Exception 
     */
    public void crearCuenta()throws Exception {
        long idCuenta;//Variable para guardar el id de la cuenta que insertamos, este id es generado por la BBDD.
        
        idCuenta = insertarCuentaBBDD();
        
        System.out.println("Introduce el id del cliente al que añadir a la cuenta creada: ");
        long customerId = (long) Utilidades.leerDouble();
        
        //Si el cliente no esta en la BBDD introducimos uno nuevo.
        if(!existeCliente(customerId))
            customerId = crearNuevoCliente();
        //Actualizamos la tabla customerAccount que relaciona las cuentas con los clientes
        insertarCustomerAccount(customerId,idCuenta);         
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
        
       //Miramos si el id del cliente y de la cuenta existen en la BBDD.
        hayCliente = existeCliente(idCliente);
        hayCuenta = existeCuenta(idCuenta);
        
        if(hayCliente && hayCuenta)//Si estan añadimos.
            insertarCustomerAccount(idCliente, idCuenta);
        else
            System.out.println("No se encontro la cuenta o el cliente");
    }
    /**
     * Consultar datos de una cuenta.
     * @throws Exception 
     */
    public void consultarDatosCuenta() throws Exception{
        long idCuenta;
        
        System.out.println("Introduce el id de la cuenta: ");
        idCuenta  = (long) Utilidades.leerDouble();
        //Lamamos a este metodo que nos muestra los datos de la cuenta
        visualizarDatosDeUnaCuenta(idCuenta);
        
    }
    /**
     * Realizar un movimiento a una cuenta
     * @throws Exception 
     */
    public void movimientoCuenta()throws Exception {
       long idCuenta;
       long idMovimiento;
        
       System.out.println("Introduce el id de la cuenta: ");
       idCuenta  = (long) Utilidades.leerDouble();
       
       if(!existeCuenta(idCuenta))
           idCuenta = insertarCuentaBBDD();
            
       idMovimiento = insertarMovimientoBBDD(idCuenta);
       
       //AHora actualizar el balance de la cuenta coger la descripcion para sumar o restar balance cuenta amoount
       actualizarDatosDeUnaCuenta(idCuenta,idMovimiento);
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
 * Pedir por consola el apellido de un cliente. Utilizado en el método consultar cliente.
 * @return El apellido de un cliente 
 */
    private long clienteAConsultar(){
        long id;
        
        System.out.println("Introduce el DNI del cliente que buscas");
        id = ejercicioBanco.utilidades.Utilidades.leerInt();
        return id;
    }
    
    /**
     * Recibido el id de un cliente devuelve un booleano
     * @param idCliente
     * @return True si encuentra el cliente en la BBDD y false en caso contrario
     */
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
            closeConnection();
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
            closeConnection();
        }catch(SQLException e){
            System.out.println("Ha ocurrido un error de conexion.");
        }     
        return esta;
    }

    private void insertarCustomerAccount(long customerId, long accountId) {
        try{
            openConnection();
            String insertCustomerAccount = "Insert into customer_account values (?,?)";
            stmt = con.prepareStatement(insertCustomerAccount);
            stmt.setLong(1,customerId);
            stmt.setLong(2,accountId);
            stmt.executeUpdate();
            closeConnection();
        }catch(SQLException e){
            System.out.println("No se ha podido actualizar");
        }
    }
    /**
     * Este metodo recibe el resultset de hacer un select de un cliente y visualiza los datos con el getDatos de la  clase cliente
     * @param rs
     * @throws Exception 
     */
    private void mostarDatosCliente(ResultSet rs) throws Exception {
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
    }

    /**
     * Muestra las cuentas de un cliente en el caso de que no hubiera ninguna muestra un mensaje informando.
     * @param rs Es un resultset de una query
     * @throws Exception 
     */
    private void mostarCuentasCliente(ResultSet rs) throws Exception {
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
     * Recibe un cliente y lo inserta en la BBDD
     * @param oneCustomer Instancia del cliente
     * @return Id del Cliente
     * @throws Exception 
     */
    private long introducirClienteBBDD(Customer oneCustomer) throws Exception{
        long id = 0;
        stmt = con.prepareStatement(insertarCustomer,com.mysql.jdbc.PreparedStatement.RETURN_GENERATED_KEYS);
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
                ResultSet rs =  stmt.getGeneratedKeys();
                if(rs.next()){
                    id = rs.getLong(1);
                }
       
       return id;
    }
    /**
     * Introduce una nueva cuenta en la BBDD
     * @return ID de la cuenta creada
     * @throws Exception 
     */
    private long insertarCuentaBBDD() throws Exception{
        long id = 0;
        System.out.println("Vamos a crear una cuenta.");
        Account account = new Account();
        account.setDatos();
    
        openConnection();
        
        stmt = con.prepareStatement(crearCuentaInsert,com.mysql.jdbc.PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setDouble(1,account.getBalance());
        stmt.setDouble(2,account.getBeginBalance());
        stmt.setTimestamp(3,account.getBeginBalanceTimestamp());
        stmt.setDouble(4, account.getCreditLine());
        stmt.setString(5, account.getDescription());
        stmt.setInt(6, account.getType());
        
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
            id = rs.getLong(1);
        }
        closeConnection();
        return id;
    }
    /**
     * Visualiza datos de una cuenta
     * @param idCuenta IOdentificador de cuenta eln la BBDD
     * @throws Exception 
     */
    private void visualizarDatosDeUnaCuenta(long idCuenta)throws Exception {
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
        closeConnection();
    }

    /**
     * Inserta un movimiento en la BBDD
     * @param idCuenta Identificador de la cuenta sobre la que se realiza el movimiento
     * @throws Exception 
     */
    private long insertarMovimientoBBDD(long idCuenta) throws Exception {
       Movement aMovement = new Movement();
       aMovement.setDatos(idCuenta);
       
       long idMovimiento = 0;//Devolver el id del movimiento
       
       openConnection();
       stmt = con.prepareStatement(insertarMovimiento,com.mysql.jdbc.PreparedStatement.RETURN_GENERATED_KEYS);
       stmt.setDouble(1, aMovement.getAmmount());
       stmt.setDouble(2, aMovement.getAmmount());
       stmt.setString(3, aMovement.getDescription());
       stmt.setLong(4, aMovement.getAccountId());
       
       stmt.executeUpdate();
       
       ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
            idMovimiento = rs.getLong(1);
        }
        closeConnection();
        return idMovimiento; 
    }

    /**
     * Actualiza los datos de una cuenta tras efectuarse un movimiento.
     * @param idCuenta 
     */
    private void actualizarDatosDeUnaCuenta(long idCuenta, long idMovimiento) throws Exception{
        
        String update,descripcionDelMovimiento = " ";
        double cantidadTransferidaMovimiento = 0;
        
        
        String selectMovimiento = "Select * from movement where id = ?";
        
        openConnection ();
        
        stmt = con.prepareStatement(selectMovimiento);
        stmt.setLong(1,idMovimiento);
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            descripcionDelMovimiento = rs.getString("description");
            cantidadTransferidaMovimiento = rs.getDouble("amount");
        }
        
        if(descripcionDelMovimiento.equalsIgnoreCase("Payment")){
            update = "Update account set balance = balance - ? where id = ?";
        }else
           update = "Update account set balance = balance + ? where id = ?";
        stmt = con.prepareStatement(update);
        stmt.setDouble(1,cantidadTransferidaMovimiento);
        stmt.setLong(2, idCuenta);
       
        stmt.executeUpdate();
        closeConnection();
    }
}
