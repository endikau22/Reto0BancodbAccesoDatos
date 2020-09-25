/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import clases.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
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
    
    private ResourceBundle fichero;
    

	private void openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost/bdproyectotrenes";
		con = DriverManager.getConnection(url, "endika", "abcd*1234");
		stmt = con.createStatement();
	}

	private void closeConnection() throws SQLException {
		stmt.close();
		con.close();
	}

    public void crearNuevoCliente() {
        Customer oneCustomer = new Customer();
        oneCustomer.setDatos();
        
    }

    public void consultarCliente() {
        
    }

    public void consultarCuentasCliente() {
       
    }

    public void crearCuenta() {
      
    }

    public void agregarClienteCuenta() {
        
    }

    public void consultarDatosCuenta() {
       
    }

    public void movimientoCuenta() {
       
    }

    public void consultarMovimientos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
