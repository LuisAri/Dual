/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

/**
 * Clase que representa los metadatos de un renglon, los metadatos son la posicion
 * de un elemento y si ese elemento es un coeficiente o una variable de holgura.
 * 
 * @author Hector
 */
public class MetaRenglon {
    
    /**
     * Indica si el dato a indicar es un coeficiente o una variable de holgura.
     */
    private boolean esCoeficiente;
    
    /**
     * Indica la posicion del dato.
     */
    private int  posicion;

    /**
     * Constructor inicial.
     */
    public MetaRenglon(){
        this.esCoeficiente = true;
        this.posicion = 0;
    }

    /**
     * Constructor con valores.
     * 
     * @param esCoeficiente
     * @param posicion 
     */
    public MetaRenglon(boolean esCoeficiente, int posicion) {
        this.esCoeficiente = esCoeficiente;
        this.posicion = posicion;
    }
    
    /**
     * Retorna si el valor es un coeficiente o una variable de holgura en el renglon.
     * @return true si es coeficiente, false si es una variable de holgura.
     */
    public boolean isEsCoeficiente() {
        return esCoeficiente;
    }

    /**
     * Asigna si el valor es un coeficiente o una variable de holgura en el renglon.
     * @param esCoeficiente 
     */
    public void setEsCoeficiente(boolean esCoeficiente) {
        this.esCoeficiente = esCoeficiente;
    }

    /**
     * Retorna la posicion del valor en el renglon.
     * @return 
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * Asigna la posicion del valor en el renglon.
     * @param posicion 
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    
}
