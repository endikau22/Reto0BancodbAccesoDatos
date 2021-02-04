/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.controlador;

/**
 * This class creates new Dao objects. This dao contains the methods use in the bank management project.
 * @author Endika Ubierna.
 */
public class DaoFactory {
    /**
     * This method gets a new dao object.
     * @return A Dao.
     */
    public static Dao getDao (){
        return new DaoImplementation();
    }
}
