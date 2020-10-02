/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.application;

import ejercicioBanco.controlador.Dao;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal. Contiene un menú con diferentes opciones a realizar en la BBDD.
 * @author 2dam
 */
public class Application {
    public static void main (String args[]){
        int opc;
        
        do{
           opc = menu();
           switch (opc){
               case 1:
                   crearNuevoCliente();
                   break;
               case 2:
                   consultarCliente();
                   break;
               case 3:
                   consultarCuentasCliente();
                   break;
               case 4:
                   crearCuenta();
                   break;
               case 5:
                   agregarClienteCuenta();
                   break;
               case 6:
                   consultarDatosCuenta();
                   break;
               case 7:
                   movimientoCuenta();
                   break;
               case 8:
                   consultarMovimientos();
                   break;
               case 9:
                   System.out.println("Gracias.");
                   break;
                   
           }
        }while(opc !=9);
    }
/**
 * Muestra las diferentes opciones a realizar con la BBDD
 * @return Un entero indicando la opción elegida dentro de las opciones del menú.
 */
    private static int menu() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int opc;
        System.out.println("------------BIENVENIDO AL BANCO-----------");
        System.out.println("1.-Crear nuevo cliente.");
        System.out.println("2.-Consultar datos de un cliente.");
        System.out.println("3.-Consultar cuentas de un cliente.");
        System.out.println("4.-Crear cuenta para cliente.");
        System.out.println("5.-Agregar cliente a cuenta.");
        System.out.println("6.-Consultar datos de una cuenta.");
        System.out.println("7.-Realizar movimiento sobre una cuenta.");
        System.out.println("8.-Consultar movimientos de una cuenta.");
        System.out.println("9.-Salir.");
        opc = ejercicioBanco.utilidades.Utilidades.leerInt(1, 9);
        return opc;
    }
/**
 * Añadir un cliente a la base de datos.
 */
    private static void crearNuevoCliente() {
       Dao dao = new Dao();
        try {
            dao.crearNuevoCliente();
        } catch (Exception ex) {
            System.out.println("Se ha producido un error al crear un nuevo cliente.");
            ex.printStackTrace();
        }
    }
/**
 * Consultar información de un cliente
 */
    private static void consultarCliente() {
       Dao dao = new Dao();
        try {
            dao.consultarCliente();
        } catch (Exception ex) {
            System.out.println("No hay clientes en la base de datos.");
            ex.printStackTrace();
        }
    }
/**
 * Consultar las cuentas de un cliente.
 */
    private static void consultarCuentasCliente() {
       Dao dao = new Dao(); 
        try {
            dao.consultarCuentasCliente();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
/**
 * Crear una nueva cuenta asociada a un cliente del banco.
 */
    private static void crearCuenta() {
       Dao dao = new Dao(); 
        try {
            dao.crearCuenta();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
/**
 * Agregar una cuenta a un cliente bel banco.
 */
    private static void agregarClienteCuenta() {
       Dao dao = new Dao();
        try {
            dao.agregarClienteCuenta();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
/**
 * Consultar datos de una cuenta.
 */
    private static void consultarDatosCuenta() {
       Dao dao = new Dao();
        try {
            dao.consultarDatosCuenta();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
/**
 * Realizar un movimiento en relación a una cuenta.
 */
    private static void movimientoCuenta() {
       Dao dao = new Dao();
        try {
            dao.movimientoCuenta();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
/**
 * Ver la información de los movimientos de una cuenta.
 */
    private static void consultarMovimientos() {
       Dao dao = new Dao();
        try {
            dao.consultarMovimientos();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
}
