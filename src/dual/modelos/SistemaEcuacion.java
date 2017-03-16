/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import dual.modelos.FuncionObjetivo.Caso;
import java.util.ArrayList;

/**
 * Representa el sistema de ecuaciones presente en un problema de programacion
 * lineal, que consiste en la funcion objetivo, y el conjunto de restricciones.
 * 
 * @author Hector
 */
public class SistemaEcuacion {
    
    /**
     * Funcion objetivo del sistema de ecuciones.
     */
    private FuncionObjetivo funcionObjetivo;
    
    /**
     * Conjunto de restricciones.
     */
    private ArrayList<Restriccion> restricciones;
    
    /**
     * Constructor inicial con valores default.
     */
    public SistemaEcuacion(){
        this.funcionObjetivo = new FuncionObjetivo();
        this.restricciones = new ArrayList();
    }
    
    /**
     * Constructor inicial con solo la funcion objetivo personalizada.
     * @param funcionObjetivo 
     */
    public SistemaEcuacion(FuncionObjetivo funcionObjetivo){
        this.funcionObjetivo = funcionObjetivo;
        this.restricciones = new ArrayList();
    }
    
    /**
     * Constructor inicial con valores especificos.
     * @param funcionObjetivo
     * @param restricciones
     */
    public SistemaEcuacion(FuncionObjetivo funcionObjetivo, ArrayList<Restriccion> restricciones){
        this.funcionObjetivo = funcionObjetivo;
        this.restricciones = restricciones;
    }

    /**
     * Retorna la funcion objetivo.
     * @return 
     */
    public FuncionObjetivo getFuncionObjetivo() {
        return funcionObjetivo;
    }

    /**
     * Asigna la funcion objetivo.
     * @param funcionObjetivo 
     */
    public void setFuncionObjetivo(FuncionObjetivo funcionObjetivo) {
        this.funcionObjetivo = funcionObjetivo;
    }

    /**
     * Retorna el conjunto de restricciones.
     * @return 
     */
    public ArrayList<Restriccion> getRestricciones() {
        return restricciones;
    }

    /**
     * Asigna el conjunto de restricciones.
     * @param restricciones 
     */
    public void setRestricciones(ArrayList<Restriccion> restricciones) {
        this.restricciones = restricciones;
    }
    
    /**
     * Retorna la restriccion ubicada en el renglon i del conjunto.
     * @param i
     * @return 
     */
    public Restriccion getRestriccion(int i){
        return restricciones.get(i);
    }
    
    /**
     * Retorna el maximo numero de coeficientes que posee el conjunto de restricciones.
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
     * Retorna la cantidad de restricciones que posee el sistema de ecuacion.
     * @return 
     */
    public int getRenglones(){
        return restricciones.size();
    }
    
    /**
     * Retorna el tipo de problema lineal, minimizacion o maximizacion.
     */
    public Caso getCaso(){
        return funcionObjetivo.getCaso();
    }
    
    
    /**
     * Agrega una nueva restriccion al conjunto, ingresando los atributos de la
     * restriccion de forma individual.
     * 
     * @param signo
     * @param coeficientes
     * @param ladoDerecho 
     */
    public void agregarRestriccion(Restriccion.Signo signo, float[] coeficientes, float ladoDerecho){
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
     * Retorna el tabloide usado en el simplex con los valores estandar.
     * @return 
     */
    public ArrayList<Renglon> getEstandar(){
        ArrayList<Renglon> renglones = new ArrayList();
        renglones.add(new Renglon(funcionObjetivo.cantidad(), restricciones.size(), funcionObjetivo));
        int i = 1; // 1 Representa el primer renglon restriccion en el conjunto.
        for(Restriccion restriccion : restricciones){
            renglones.add(new Renglon(i++, funcionObjetivo.cantidad(), restricciones.size(), restriccion));
        }
        
        return renglones;
    }
    
    /**
     * Reimplementacion del metodo imprimir, adaptado para el sistema de ecuaciones.
     */
    public void imprimir(){
        funcionObjetivo.imprimir();
        
        System.out.println("\n\nSujeto A");
        for(Restriccion restriccion : restricciones){
            System.out.println("\t");
            restriccion.imprimir();
        }
        System.out.println("");
    }
}
