/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.controlador;

import ejercicioBanco.clases.Customer;
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
    
    
    //private ResourceBundle fichero
    private ResourceBundle fichero;
    private String dbBanco;
    private String url;
    private String user;
    private String passwd;
    private String driver;
   
    public Dao() {
       fichero = ResourceBundle.getBundle("ejercicioBanco.controlador.config");
       dbBanco = fichero.getString("DB");
       url = fichero.getString("Conn");
       user = fichero.getString("DBUser");
       passwd = fichero.getString("DBPass");
       driver = fichero.getString("Driver");
    }
    
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

    public void crearNuevoCliente() throws Exception {
       Customer oneCustomer = new Customer();
       oneCustomer.setDatos();
    
       String insert = "Insert into customer (id,city,email,firstName,lastName,middleInitial,"
               + "phone,state,street,zip) values (?,?,?,?,?,?,?,?,?,?)";
       
       
       openConnection();
       stmt = con.prepareStatement(insert);
       stmt.setLong(1,oneCustomer.getCustomerId());
       stmt.setString(2,oneCustomer.getCity());
       stmt.setString(3,oneCustomer.getEmail());
       stmt.setString(4,oneCustomer.getFirstName());
       stmt.setString(5,oneCustomer.getLastName());
       stmt.setString(6,oneCustomer.getLastName().toUpperCase().substring(0, 1).concat("."));
       stmt.setLong(7, oneCustomer.getPhone());
       stmt.setString(8, oneCustomer.getState());
       stmt.setString(9, oneCustomer.getStreet());
       stmt.setInt(10, oneCustomer.getZip());
       
       stmt.executeUpdate();
    }

    public void consultarCliente()throws Exception {
        String id = clienteAConsultar();
        String select = "Select * from customer where id = ?";
        openConnection();
        stmt = con.prepareStatement(select);
        stmt.setString(1,id);
        ResultSet rs = stmt.executeQuery();
        Customer unCustomer = new Customer();
        while(rs.next()){
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
        }
        rs.close();
        closeConnection();
    }

    public void consultarCuentasCliente()throws Exception {
        String id = clienteAConsultar();
        String select = "Select * from CustomerAccount where customerId = ?";
    }

    public void crearCuenta()throws Exception {
      
    }

    public void agregarClienteCuenta()throws Exception {
        
    }

    public void consultarDatosCuenta() throws Exception{
       
    }

    public void movimientoCuenta()throws Exception {
       
    }

    public void consultarMovimientos() throws Exception{
        
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
    private String clienteAConsultar(){
        String id;
        
        System.out.println("Introduce el DNI del cliente que buscas");
        id = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        return id;
    }
}
