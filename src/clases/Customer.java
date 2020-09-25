/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

/**
 * Clientes del banco.
 * @author 2dam
 */
public class Customer implements Serializable{
    
    private long customerId;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String street;
    private String city;
    private String state;
    private int zip;
    private long phone;
    private String email;
    
    
    //CONSTRUCTOR SIN PARAMETROS
    public Customer() {
        
    }

    //CONSTRUCTOR CON PARAMETROS
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

    //GETTERS Y SETTERS
    public long getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZip() {
        return zip;
    }

    public long getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    } 
    
}
