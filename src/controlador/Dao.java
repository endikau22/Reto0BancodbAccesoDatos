/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import clases.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Encargada de conectar nuestra aplicación con la base de datos.
 * @author 2dam
 */
public class Dao {
    private Connection con;
    private Statement stmt;
    
    
    //private ResourceBundle fichero
    ResourceBundle fichero;
    private String dbBanco;
    private String url;
    private String user;
    private String passwd;
    private String driver;
   
    public Dao() {
       fichero = ResourceBundle.getBundle("controlador.config");
       dbBanco = fichero.getString("DB");
       url = fichero.getString("Conn");
       user = fichero.getString("DBUser");
       passwd = fichero.getString("DBPass");
       driver = fichero.getString("Driver");
    }
    
    

	private void openConnection() throws Exception {
                Class.forName(driver);
		con = DriverManager.getConnection(url,user,passwd);
		stmt = con.createStatement();
	}

	private void closeConnection() throws SQLException {
		stmt.close();
		con.close();
	}

    public void crearNuevoCliente() throws Exception {
        Customer oneCustomer = new Customer();
        oneCustomer.setDatos();
        openConnection();
        //String insert = "Insert into customer ("
        //stmt.executeUpdate(insert);
    }

    public void consultarCliente()throws Exception {
        String select = "Select * from customer where lastName is Wallace";
        openConnection();
        ResultSet rs = stmt.executeQuery(select);
        while(rs.next()){
            System.out.println(rs.getLong(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)
        +" "+rs.getInt(8));
        }
        rs.close();
        closeConnection();
    }

    public void consultarCuentasCliente()throws Exception {
       
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
}
