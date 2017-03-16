/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

/**
 * Funcion lineal heredada de la clase funcion. Representa la restriccion
 * utilizada en el metodo simplex, un conjunto de objetos de tipo restriccion sera
 * almacenado en un objeto Restricciones.
 * 
 * @see dual.modelos.Funcion
 * @see dual.modelos.FuncionObjetivo
 * @see dual.modelos.old.Restricciones
 * @see dual.modelos.old.Tabloide
 * @author Hector
 */
public class Restriccion extends Funcion{
    
    /**
     * Enumerado utilizado para indicar el signo de la ecuacion/inecuacion,
     * puede ser igual (=), mayor o igual (>=) o menor o igual (<=).
     */
    public enum Signo {
        IGUAL,
        MAYOR_IGUAL,
        MENOR_IGUAL
    }
    
    /**
     * Variable que indica el signo de la restriccion.
     */
    private Signo signo;
    
    /**
     * Valor del lado derecho de la ecuacion/inecuacion.
     * 
     * En la restriccion: 2x1+3x2 >= 15, el lado derecho es el 15.
     */
    private float ladoDerecho;
    
    /**
     * Constructor de la restriccion sin parametros, por default el signo sera
     * MAYOR_IGUAL, no tendra ningun coeficiente y su lado derecho sera cero.
     */
    public Restriccion(){
        super();
        
        this.signo = Signo.MAYOR_IGUAL;
        this.ladoDerecho = 0;
    }
    
    /**
     * Constructor de la restriccion, en la que se le agrega el signo de la restriccion,
     * los coeficientes y el lado derecho.
     * 
     * @param signo el signo de la restriccion.
     * @param coeficientes los coeficientes, lado izquierdo o el primer miembro de la restriccion.
     * @param ladoDerecho el segundo miembro de la restriccion.
     */
    public Restriccion(Signo signo, float[] coeficientes, float ladoDerecho){
        super(coeficientes);
        
        this.signo = signo;
        this.ladoDerecho = ladoDerecho;
    }
    
    /**
     * Constructor de la restriccion, en la que se le agrega el signo de la restriccion,
     * los coeficientes y el lado derecho.
     * 
     * @param signo el signo de la restriccion.
     * @param coeficientes los coeficientes, lado izquierdo o el primer miembro de la restriccion.
     * @param ladoDerecho el segundo miembro de la restriccion.
     */
    public Restriccion(Signo signo, Float[] coeficientes, float ladoDerecho){
        super(coeficientes);
        
        this.signo = signo;
        this.ladoDerecho = ladoDerecho;
    }

    /**
     * Retorna el signo de la restriccion.
     * @return 
     */
    public Signo getSigno() {
        return signo;
    }

    /**
     * Asigna el signo de la restriccion, puede ser mayor, mayor o igual o menor
     * o igual.
     * @param signo 
     */
    public void setSigno(Signo signo) {
        this.signo = signo;
    }

    /**
     * Retorna el lado derecho de la restriccion.
     * @return 
     */
    public float getLadoDerecho() {
        return ladoDerecho;
    }

    /**
     * Asigna el lado derecho de la restriccion.
     * @param ladoDerecho 
     */
    public void setLadoDerecho(float ladoDerecho) {
        this.ladoDerecho = ladoDerecho;
    }
    
    /**
     * Devuelve el valor estandar del lado derecho. El estandar de una restriccion
     * indica que si el signo es mayor o igual (>=), el lado derecho sera el inverso.
     * 
     * @return 
     */
    public float getLadoDerechoEstandar(){
        return (signo == Signo.MAYOR_IGUAL)? (ladoDerecho * NEGATIVO) : ladoDerecho;
    }
    
    /**
     * Devuelve el estandar de la funcion, en caso de ser >=, el estandar sera el inverso
     * del coeficiente.
     * 
     * @return 
     */
    public Funcion estandar(){
        if(signo == Signo.MAYOR_IGUAL){
            return this.negativo();
        }
        
        return this;
    }
    
    /**
     * Devolvera una funcion con las variables de holguras de la restriccion,
     * es necesario indicar el renglon que representa esta restriccion en el tabloide.
     * 
     * @param holguras
     * @param renglon
     * @return 
     */
    public Funcion holguras(int holguras, int renglon){
        Float[] temporal = new Float[holguras];
        
        for(int i = 0; i < temporal.length; i++){
            temporal[i] = 0f;
        }
        
        temporal[renglon - 1] = 1f;
        return new Funcion(temporal);
    }
    
    /**
     * Reimplementacion del metodo de imprimir, adaptado para imprimir el coeficiente
     * de la funcion, junto con el signo y el lado derecho.
     */
    public void imprimir(){
        super.imprimir();
        System.out.println(" " + signo.name() + " " + ladoDerecho);
    }
}
