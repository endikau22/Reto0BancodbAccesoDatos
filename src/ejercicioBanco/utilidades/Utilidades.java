/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioBanco.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class container with multiple methods to ensure that the information inserted by console is correct.
 * @author Endika Ubierna.
 */
public class Utilidades{
    public static String introducirCadena() {
        String cadena = "";
        InputStreamReader entrada = new InputStreamReader(System.in);
        BufferedReader teclado = new BufferedReader(entrada);
        try {
            cadena = teclado.readLine();
        } catch (IOException er) {
            System.out.println("error al introducir datos");
            System.exit(0);
        }
        return cadena;
    }

    public static String leerString(int x) {
        String cadena = null;
        boolean ok;
        do {
            ok = true;
            cadena = introducirCadena();
            if (cadena.length() > x) {
                System.out.println("Error al introducir datos. ");
                ok = false;
            }
        } while (!ok);
        return cadena;
    }

    public static double leerDouble(int x, int y) {
        double num = 0;
        boolean ok;
        do {
            try {
                ok = true;
                num = Double.parseDouble(introducirCadena());

            } catch (NumberFormatException e) {
                System.out.println("Hay que introducir numeros");
                ok = false;
                num = x;

            }
            if (num < x || num > y) {
                System.out.println("Dato fuera de rango, introduce entre" + x + " y " + y);
                ok = false;
            }
        } while (!ok);
        return num;
    }

    public static double leerDouble() {
        double fNumero = 0;
        boolean ok;
        do {
            try {
                ok = true;
                fNumero = Double.parseDouble(introducirCadena());
            } catch (NumberFormatException e) {
                System.out.println("Error al introducir el numero");
                ok = false;
            }
        } while (!ok);
        return fNumero;
    }

    public static boolean esBoolean() {
        String respu;
        do {
            respu = introducirCadena().toLowerCase();
        } while (!respu.equals("0") && !respu.equals("1") && !respu.equals("si") && !respu.equals("no") && !respu.equals("s") && !respu.equals("n") && !respu.equals("true") && !respu.equals("false"));
        if (respu.equals("1") || respu.equals("si") || respu.equals("s") || respu.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static char leerCharArray(char caracteres[]) {
        int i;
        boolean error = false;
        String letra;
        char aux = 0;

        do {
            error = false;
            letra = introducirCadena();
            if (letra.length() != 1) {
                System.out.println("Error, introduce un caracter: ");
                error = true;
            } else {
                aux = letra.charAt(0);
                for (i = 0; i < caracteres.length; i++) {
                    if (Character.toUpperCase(caracteres[i]) == Character.toUpperCase(aux)) {
                        break;
                    }
                }
                if (i == caracteres.length) {
                    error = true;
                    System.out.println("Error, el caracter introducido no es valido. ");
                }
            }
        } while (error);
        return aux;
    }

    public static char leerChar() {
        boolean error = false;
        String letra;

        do {
            error = false;
            letra = introducirCadena();
            if (letra.length() != 1) {
                System.out.println("Error, introduce un caracter: ");
                error = true;
            }

        } while (error);
        return letra.charAt(0);
    }

    public static float leerFloat() {
        float fNumero = 0;
        boolean ok;
        do {
            try {
                ok = true;
                fNumero = Float.parseFloat(introducirCadena());
            } catch (NumberFormatException e) {
                System.out.println("Error al introducir el numero");
                ok = false;
            }
        } while (!ok);
        return fNumero;
    }

    public static float leerFloat(float x, float y) {
        float fNumero = 0;
        boolean ok;
        do {
            try {
                ok = true;
                fNumero = Float.parseFloat(introducirCadena());
            } catch (NumberFormatException e) {
                System.out.println("Hay que introducir numeros. Vuelve a introducir: ");
                ok = false;
                fNumero = x;
            }
            if (fNumero < x || fNumero > y) {
                System.out.println("Dato fuera de rando. Introduce entre " + x + " y " + y);
                ok = false;
            }
        } while (!ok);
        return fNumero;
    }

    public static int leerInt() {
        int iNumero = 0;
        boolean ok;
        do {
            try {
                ok = true;
                iNumero = Integer.parseInt(introducirCadena());
            } catch (NumberFormatException e) {
                System.out.println("hay que introducir numeros");
                ok = false;
            }
        } while (!ok);
        return iNumero;
    }

    public static int leerInt(int x, int y) {
        int num = 0;
        boolean ok;
        do {
            try {
                ok = true;
                num = Integer.parseInt(introducirCadena());

            } catch (NumberFormatException e) {
                System.out.println("Hay que introducir numeros");
                ok = false;
                num = x;

            }
            if (num < x || num > y) {
                System.out.println("Dato fuera de rango, introduce entre" + x + " y " + y);
                ok = false;
            }
        } while (!ok);
        return num;
    }

    //Pido fecha
    public static Date pidoFechaDMA(String message) {
        String fechaS;
        boolean hay;
        Date fecha = null;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

        do {
            hay = true;
            System.out.print(message);
            System.out.println(" en formato dd-mm-aaaa: ");
            fechaS = Utilidades.introducirCadena();
            try {
                fecha = (Date) formatoFecha.parse(fechaS);
            } catch (ParseException p) {
                System.out.println("Error... formato de fecha introducido incorrecto.");
                hay = false;
            }
        } while (!hay);
        return fecha;
    }

    /**
     * Comprueba que el String recibido como parámetro cumple las condiciones de
     * un email.
     *
     * @param email El email del campo contraseña.
     * @return Un booleano. True si la contraseña es correcta.
     */
    public static boolean emailCorrecto(String email) {
        //un patrón de email válido.
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        //Comprueba que el pattern del patron coincide con el email introducido.
        Matcher matcher = pattern.matcher(email);
        //Devuelve un booleano el metodo matches de la clase matcher
        return matcher.matches();
    }
}


