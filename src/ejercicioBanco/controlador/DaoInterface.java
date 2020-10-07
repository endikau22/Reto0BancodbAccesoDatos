/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.controlador;

/**
 * Interfaz del Dao
 * @author 2dam
 */
public interface DaoInterface {
    long crearNuevoCliente() throws Exception;
    void consultarCliente() throws Exception;
    void consultarCuentasCliente()throws Exception;
    void crearCuenta()throws Exception;
    void agregarClienteCuenta()throws Exception;
    void consultarDatosCuenta() throws Exception;
    void movimientoCuenta()throws Exception;
    void consultarMovimientos() throws Exception;   
}
