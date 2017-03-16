/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos.old;

import dual.modelos.Restriccion;
import dual.modelos.Restriccion.Signo;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el conjunto de restricciones.
 * 
 * Atributos:
 * 
 * - Lista de restricciones
 * 
 * 
 * Metodos:
 * 
 * - Agregar restriccion
 * - Remover restriccion
 * - Obtener restriccion
 * 
 * @author Hector
 */
public class Restricciones {
    
    /**
     * Lista de restricciones
     */
    private List<Restriccion> restricciones;
    
    /**
     * Constructor que inicializa una lista vacia de restricciones.
     */
    public Restricciones(){
        restricciones = new ArrayList();
    }
    
    /**
     * Retorna la restriccion ubicada en el renglon i de la lista.
     * @param i
     * @return 
     */
    public Restriccion get(int i){
        return restricciones.get(i);
    }
    
    /**
     * Retorna el objeto lista de restricciones
     * @return 
     */
    public List<Restriccion> getRestricciones(){
        return restricciones;
    }
    
    /**
     * Retorna el maximo numero de variables que posee la restriccion.
     * @return 
     */
    public int getVariables(){
        int variables = 0;
        
        for(Restriccion restriccion: restricciones){
            variables = (variables > restriccion.cantidad()) ? variables: restriccion.cantidad();
        }
        
        return variables;
    }
    
    /**
     * Retorna la cantidad de restricciones que posee el objeto.
     * @return 
     */
    public int getRenglones(){
        return restricciones.size();
    }
    
    /**
     * Agrega una nueva restriccion al conjunto, ingresando los atributos de la
     * restriccion de forma individual.
     * 
     * @param signo
     * @param coeficientes
     * @param ladoDerecho 
     */
    public void agregarRestriccion(Signo signo, float[] coeficientes, float ladoDerecho){
        Restriccion nueva = new Restriccion(signo, coeficientes, ladoDerecho);
        restricciones.add(nueva);
    }
    
    /**
     * Agrega una nueva restriccion al conjunto, ingresando la restriccion completa.
     * @param nueva 
     */
    public void agregarRestriccion(Restriccion nueva){
        restricciones.add(nueva);
    }
    
    /**
     * Elimina una restriccion del conjunto por su numero de renglon.
     * @param renglon 
     */
    public void removerRestriccion(int renglon){
        restricciones.remove(renglon);
    }
    
    /**
     * Elimina una restriccion ingresando el objeto restriccion a remover.
     * @param eliminar 
     */
    public void removerRestriccion(Restriccion eliminar){
        restricciones.remove(eliminar);
    }
    
    /**
     * Reimplementacion del metodo imprimir, adaptado para el conjunto de 
     * restricciones.
     */
    public void imprimir(){
        System.out.println("Sujeto A");
        for(Restriccion restriccion : restricciones){
            System.out.println("\t");
            restriccion.imprimir();
        }
        System.out.println("");
    }
}
