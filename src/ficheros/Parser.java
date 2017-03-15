/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheros;

import dual.modelos.FuncionObjetivo;
import dual.modelos.Restriccion;
import dual.modelos.Vector;
import java.util.Arrays;

/**
 * Clase dedicada a leer una cadena de texto y convertirla en un objeto de tipo
 * funcion objetivo o restriccion.
 * 
 * @author Hector
 */
public class Parser {
    
    /**
     * Parsea una cadena de texto y retorna un objeto FuncionObjetivo
     * @param linea
     * @return 
     */
    public static FuncionObjetivo parsearFuncionObjetivo(String linea){
        String temporal = linea.replaceAll("x[0-9]*", "");
        String[] tokens = temporal.split("\\s");
        
        Vector<Float> vector = new Vector();
        for(int i = 1; i < tokens.length; i++){
            vector.add(Float.parseFloat(tokens[i]));
        }

        return new FuncionObjetivo(
                FuncionObjetivo.Caso.valueOf(tokens[0]), Arrays.copyOf(vector.toArray(), vector.size(), Float[].class));
    }
    
    /**
     * Parsea una restriccion y retorna un objeto restriccion.
     * @param linea
     * @return 
     */
    public static Restriccion parsearRestriccion(String linea){
        String temporal = linea.replaceAll("x[0-9]*", "");
        temporal = temporal.replaceAll("<=", "MENOR_IGUAL");
        temporal = temporal.replaceAll(">=", "MAYOR_IGUAL");
        temporal = temporal.replaceAll("=", "IGUAL");

        String[] tokens = temporal.split("\\s");
        Vector<Float> vector = new Vector();
        for(int i = 0; i < tokens.length - 2; i++){
            vector.add(Float.parseFloat(tokens[i]));
        }

        return new Restriccion( Restriccion.Signo.valueOf(tokens[tokens.length-2]),
                                Arrays.copyOf(vector.toArray(), vector.size(), Float[].class),
                                Float.parseFloat(tokens[tokens.length - 1]));
    }
}
