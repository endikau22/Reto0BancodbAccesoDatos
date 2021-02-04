/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.clases;

import ejercicioBanco.utilidades.Utilidades;
import java.io.Serializable;

/**
 * Class representing the customers of the bank. This bank customers can possibly have multiple accounts and the 
 * movements made are also registered in a database.
 * @author Endika Ubierna.
 */
public class Customer implements Serializable{
    /**
     * The customer id. It is also the primary key in the database.
     */
    private long customerId;
    /**
     * The customer first name.
     */
    private String firstName;
    /**
     * The customer last name.
     */
    private String lastName;
    /**
     * The customer middle initial.
     */
    private String middleInitial;
    /**
     * The customer street.
     */
    private String street;
    /**
     * The customer city.
     */
    private String city;
    /**
     * The customer state.
     */
    private String state;
    /**
     * The customer zip code.
     */
    private int zip;
    /**
     * The customer phone number.
     */
    private long phone;
    /**
     * The customer email.
     */
    private String email;
    
    /**
     * Class constructor without parameters.
     */
    public Customer() {
        
    }
    //El id habría que quitarlo de todas las clases porque es auto generado en la base de datos.
    /**
     * Class constructor with parameters.
     * @param customerId The customer id.
     * @param firstName The customer first name.
     * @param lastName The customer last name.
     * @param middleInitial The customer middle initial.
     * @param street The customer street.
     * @param city The customer city.
     * @param state The customer state.
     * @param zip The customer zip code.
     * @param phone The customer phone number.
     * @param email The customer email.
     */
    public Customer(long customerId, String firstName, String lastName, String middleInitial, String street, String city, String state, int zip, long phone, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
    /**
     * This method introduces customer info in the console.
     */
    public void setDatos(){
        System.out.println("Introduce nombre del cliente: ");
        this.firstName = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        System.out.println("Introduce apellido del cliente: ");
        this.lastName = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        System.out.println("Introduce direccion: ");
        this.street = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        System.out.println("Introduce el numero de telefono:");
        this.phone = (long) ejercicioBanco.utilidades.Utilidades.leerInt(100000000,999999999);
        System.out.println("Introduce la ciudad: ");
        this.city = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        System.out.println("Introduce la provincia: ");
        this.state = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        System.out.println("Introduce el codigo postal: ");
        this.zip = ejercicioBanco.utilidades.Utilidades.leerInt(0,99999);
        do {
            System.out.println("Introduce el mail: ");
            this.email = ejercicioBanco.utilidades.Utilidades.introducirCadena();
        } while (!Utilidades.emailCorrecto(email));
        
    }
    /**
     * This method shows customer info in the console.
     */
    public void getDatos(){
        System.out.println("Los datos del cliente: "+this.customerId);
        System.out.println("Nombre: "+this.firstName);
        System.out.println("Apellido: "+this.lastName);
        System.out.println("Ciudad: "+this.city);
        System.out.println("Telefono: "+this.phone);
    }

    //GETTERS Y SETTERS
    /**
     * Gets the customer id.
     * @return The customer id.
     */
    public long getCustomerId() {
        return customerId;
    }
    /**
     * Gets the customer first name.
     * @return The customer first name.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Gets the customer last name.
     * @return The customer last name.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Gets the customer middle initial.
     * @return The customer middle initial.
     */
    public String getMiddleInitial() {
        return middleInitial;
    }
    /**
     * Gets the customer street.
     * @return The customer street.
     */
    public String getStreet() {
        return street;
    }
    /**
     * Gets the customer city.
     * @return The customer city.
     */
    public String getCity() {
        return city;
    }
    /**
     * Gets the customer state.
     * @return The customer state.
     */
    public String getState() {
        return state;
    }
    /**
     * Gets the customer zip code.
     * @return The customer zip code.
     */
    public int getZip() {
        return zip;
    }
    /**
     * Gets the customer phone number.
     * @return The customer phone number.
     */
    public long getPhone() {
        return phone;
    }
    /**
     * Gets the customer email.
     * @return The customer email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the customer id.
     * @param customerId The customer id.
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    /**
     * Sets the customer first name.
     * @param firstName The customer first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Sets the customer last name.
     * @param lastName The customer last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Sets the customer middle initial.
     * @param middleInitial The customer middle initial.
     */
    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }
    /**
     * Sets the customer street.
     * @param street The customer street.
     */
    public void setStreet(String street) {
        this.street = street;
    }
    /**
     * Sets the customer city.
     * @param city The customer city.
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Sets the customer state.
     * @param state The customer state.
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * Sets the customer zip code.
     * @param zip The customer zip code.
     */
    public void setZip(int zip) {
        this.zip = zip;
    }
    /**
     * Sets the customer phone number.
     * @param phone The customer phone number.
     */
    public void setPhone(long phone) {
        this.phone = phone;
    }
    /**
     * Sets the customer email.
     * @param email The customer email.
     */
    public void setEmail(String email) {
        this.email = email;
    } 
    
}
