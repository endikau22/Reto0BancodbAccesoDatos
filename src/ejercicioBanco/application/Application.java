/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.application;

import ejercicioBanco.clases.Account;
import ejercicioBanco.clases.Customer;
import ejercicioBanco.clases.Movement;
import ejercicioBanco.controlador.DaoFactory;
import ejercicioBanco.controlador.Dao;
import ejercicioBanco.excepciones.AccountNotExistException;
import ejercicioBanco.excepciones.CustomerNotExistException;
import ejercicioBanco.excepciones.EmailExistException;
import ejercicioBanco.utilidades.Utilidades;
import java.util.ArrayList;
import java.util.List;

/**
 * Project Main class. This project is all about managing bank operations. It includes {@link Customer} 
 * ,<code>Account</code> and <code>Movement</code>. The interface used in this project is the console.
 * @author Endika Ubierna.
 */
public class Application {  
    /**
     * A Dao instance. This object is used to execute the actions agains the database.
     */
    //Es una instancia de la interface, pero como la clase implementa la interface mirar la factoria como devuelve la interface pero hace new y la clase implementation.
    private static final Dao dao = DaoFactory.getDao();
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
                   realizarMovimientoCuenta();
                   break;
               case 8:
                   consultarMovimientos();
                   break;
               case 9:
                   listarTodosCustomers();
                   break;                   
               case 10:
                   System.out.println("Gracias.");
                   break;                 
           }
        }while(opc !=10);
    }
/**
 * Menu displayed in the console. Each option is an action in the bank manager project.
 * @return A number. This number indicates the option selected.
 */
    private static int menu() {
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
        System.out.println("9.-Listar todos los clientes.");
        System.out.println("10.-Salir.");
        opc = ejercicioBanco.utilidades.Utilidades.leerInt(1, 10);
        return opc;
    }
    /**
     * Adds a new <code>Customer</code> in the database. Before doing so, check if the email is registered.
     */
    private static Customer crearNuevoCliente() {
        Customer oneCustomer = new Customer();
        oneCustomer.setDatos();
        try {
            long id = dao.crearNuevoCliente(oneCustomer);
            System.out.println("El cliente con el id: " + id + " ha sido dado de alta correctamente.");
        }catch (EmailExistException ex) {
            System.out.println(ex.getMessage());
        }catch (Exception ex) {
            System.out.println("Error al dar de alta "+ ex.getMessage());
        }
        return oneCustomer;
    }

    /**
     * Gets a <code>Customer</code> information.
     */
    private static void consultarCliente() {
        try {
            String email;
            do {
                System.out.println("Introduce el Email del cliente");
                email = Utilidades.introducirCadena();
                if(!Utilidades.emailCorrecto(email))
                    System.out.println("El formato del email es incorrecto.");
            } while (!Utilidades.emailCorrecto(email));
            Customer customer = dao.consultarCliente(email);
            customer.getDatos();
        } catch (CustomerNotExistException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Gets all the <code>Account</code> from a <code>Customer</code>.
     */
    private static void consultarCuentasCliente() {
        List<Account> cuentas = new ArrayList<>();
        try {
            String email;
            do {
                System.out.println("Introduce el Email del cliente");
                email = Utilidades.introducirCadena();
                if (!Utilidades.emailCorrecto(email)) {
                    System.out.println("El formato del email es incorrecto.");
                }
            } while (!Utilidades.emailCorrecto(email));
            cuentas = dao.consultarCuentasCliente(email);
            if (cuentas.isEmpty()) {
                System.out.println("El cliente no tiene ninguna cuenta.");
            } else {
                System.out.println("--------------CUENTAS---------------------");
                for (Account a : cuentas) {
                    a.getDatos();
                    System.out.println("--------------------------------------");
                }
            }
        } catch (CustomerNotExistException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Creates a new {@link Account}, this account is asign to a <code>Customer</code>
     */
    private static void crearCuenta() {
        Customer customer = null;
        Account account = new Account();
        try {
            //Primero el customer al que añadir la cuenta
            String email;
            do {
                System.out.println("Introduce el Email del cliente");
                email = Utilidades.introducirCadena();
                if (!Utilidades.emailCorrecto(email)) {
                    System.out.println("El formato del email es incorrecto.");
                }
            } while (!Utilidades.emailCorrecto(email));
            //Buscamos al customer
            try {
                customer = dao.consultarCliente(email);
                //Si entra al catch es que no existe lo creamos
            } catch (CustomerNotExistException ex) {
                System.out.println(ex.getMessage());
                customer = crearNuevoCliente();
            }
            
            account.setDatos();
            dao.crearCuenta(customer,account);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Adds a client to an existing account.
     */
    private static void agregarClienteCuenta() {
        //En este metodo no creamos si la cuenta o el cliente no existen entra al catch y no hace nada.
        Long idCuenta = null;
        Account account = null;
        Customer customer = null;
        try {
            System.out.println("Introduce el id de la cuenta: ");
            idCuenta = (long) Utilidades.leerDouble();
            //Llamamos a este metodo que nos devuelve la cuenta. Sino entra al catch de abajo si sigue en el try vuelve una cuenta
            account = dao.consultarDatosCuenta(idCuenta); 
            //Ahora el customer
            String email;
            do {
                System.out.println("Introduce el Email del cliente");
                email = Utilidades.introducirCadena();
                if (!Utilidades.emailCorrecto(email)) {
                    System.out.println("El formato del email es incorrecto.");
                }
            } while (!Utilidades.emailCorrecto(email));
            //Buscamos al customer
            customer = dao.consultarCliente(email);  
            dao.agregarClienteCuenta(account,customer);
        } catch (CustomerNotExistException ex) {
            System.out.println(ex.getMessage());
        } catch (AccountNotExistException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Gets an <code>Account</code> information.
     */
    private static void consultarDatosCuenta() {
        Long idCuenta = null;
        try {
            System.out.println("Introduce el id de la cuenta: ");
            idCuenta = (long) Utilidades.leerDouble();
            //Llamamos a este metodo que nos muestra los datos de la cuenta
            dao.consultarDatosCuenta(idCuenta).getDatos();
        } catch (AccountNotExistException ex) {
            System.out.println(ex.getMessage());
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Makes a {@link Movement} in an <code>Account</code>.
     */
    private static void realizarMovimientoCuenta() {
        Long idCuenta = null;
        Account account = new Account();
        Movement movement = new Movement();
        try {
            System.out.println("Introduce el id de la cuenta: ");
            idCuenta = (long) Utilidades.leerDouble();
            //Si no salta el catch es que la cuenta existe
            account = dao.consultarDatosCuenta(idCuenta);
            movement.setDatos(idCuenta);
            dao.movimientoCuenta(movement,account);
        } catch (AccountNotExistException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Crea la cuenta antes de realizar cualquier movimiento.");
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Gets all the <code>Movement</code> from an {@link Account}. 
     */
    private static void consultarMovimientos() {
        Long idCuenta = null;
        Account account = null;
        List<Movement> movimientosCuenta = new ArrayList<>();
        try {
            System.out.println("Introduce el id de la cuenta: ");
            idCuenta = (long) Utilidades.leerDouble();
            //Si no salta el catch es que la cuenta existe
            account = dao.consultarDatosCuenta(idCuenta);
            movimientosCuenta = dao.consultarMovimientos(account);
            if(movimientosCuenta.isEmpty())
                System.out.println("No se ha realizado ningun movimiento sobre la cuenta.");
            else{
                System.out.println("-----------MOVIMIENTOS DE LA CUENTA "+account.getAccountId()+"--------------");
                for(Movement m:movimientosCuenta){
                    m.getDatos();
                    System.out.println("-------------------------------------------------------------------------");
                }
            }
        } catch (AccountNotExistException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Crea la cuenta antes de realizar cualquier movimiento."); 
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Gets a list of all the bank <code>Customer</code>
     */
    private static void listarTodosCustomers() {
        //List es la interfaz arraylist es una de las clases que son listas que aplican esa interfaz.
        List<Customer> customers = new ArrayList<>();
        try {
            customers = dao.listarCustomers();
            if(customers.isEmpty())
                System.out.println("No hay Clientes registrados en este momento.");
            else{
                System.out.println("-----------LISTADO CUSTOMERS--------------------");
                for(Customer c:customers){
                    c.getDatos();
                    System.out.println("--------------------------------------------");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }       
    }
}
