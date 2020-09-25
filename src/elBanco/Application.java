/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elBanco;

import controlador.Dao;

/**
 * Clase principal
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
        opc = utilidades.Utilidades.leerInt(1, 9);
        return opc;
    }

    private static void crearNuevoCliente() {
       Dao dao = new Dao();
       dao.crearNuevoCliente();
    }

    private static void consultarCliente() {
       Dao dao = new Dao();
       dao.consultarCliente();
    }

    private static void consultarCuentasCliente() {
       Dao dao = new Dao(); 
       dao.consultarCuentasCliente();
    }

    private static void crearCuenta() {
       Dao dao = new Dao(); 
       dao.crearCuenta();
    }

    private static void agregarClienteCuenta() {
       Dao dao = new Dao();
       dao.agregarClienteCuenta();
    }

    private static void consultarDatosCuenta() {
       Dao dao = new Dao();
       dao.consultarDatosCuenta();
    }

    private static void movimientoCuenta() {
       Dao dao = new Dao();
       dao.movimientoCuenta();
    }

    private static void consultarMovimientos() {
       Dao dao = new Dao();
       dao.consultarMovimientos();
    }
}
