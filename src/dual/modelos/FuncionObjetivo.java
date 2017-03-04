/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

/**
 * Funcion lineal heredada de la clase funcion. Representa la funcion objetivo
 * utilizada en el metodo simplex.
 * 
 * Atributos:
 * 
 * - Todos los atributos heredados de la clase Funcion.
 * - Caso de funcion objetivo, si es maximizar o minimizar.
 * 
 * Metodos:
 * 
 * - Todos los metodos heredados de la clase Funcion.
 * - Reimplementacion del metodo imprimir.
 * 
 * @see dual.modelos.Funcion
 * @see dual.modelos.Restriccion
 * @see dual.modelos.Restricciones
 * @see dual.modelos.Tabloide
 * @author Hector
 */
public class FuncionObjetivo extends Funcion{
    
    /**
     * Enumerado utilizado para indicar el caso de funcion objetivo,
     * si es de maximizacion o minimizacion.
     */
    public enum Caso {
        MAX,
        MIN
    }
    
    /**
     * Variable que indica el tipo de caso de la funcion objetivo.
     */
    private Caso caso;
    
    /**
     * Constructor de la funcion objetivo sin parametros, por default el caso sera
     * de maximizacion y no tendra ningun elemento.
     */
    public FuncionObjetivo(){
        super();
        this.caso = Caso.MAX;
    }
    
    /**
     * Constructor de la funcion objetivo, indicando el caso de la funcion objetivo,
     * y los elementos de la funcion.
     * 
     * @param caso Caso de la funcion objetivo, puede ser Caso.MAX o Caso.MIN
     * @param coeficientes array de objeto de coeficientes de tipo Float.
     */
    public FuncionObjetivo(Caso caso, Float[] coeficientes){
        super(coeficientes);
        
        this.caso = caso;
    }
    
    /**
     * Constructor de la funcion objetivo, indicando el caso de la funcion objetivo,
     * y los elementos de la funcion.
     * 
     * @param caso Caso de la funcion objetivo, puede ser Caso.MAX o Caso.MIN
     * @param coeficientes array de datos primitivos de coeficientes de tipo float.
     */
    public FuncionObjetivo(Caso caso, float[] coeficientes){
        super(coeficientes);
        
        this.caso = caso;
    }

    /**
     * Retorna el caso de la funcion objetivo.
     * @return 
     */
    public Caso getCaso() {
        return caso;
    }

    /**
     * Asigna el caso de la funcion objetivo.
     * @param caso 
     */
    public void setCaso(Caso caso) {
        this.caso = caso;
    }
    
    /**
     * Metodo reimplementado para imprimir la funcion objetivo.
     */
    @Override
    public void imprimir(){
        System.out.print(caso.name() + " ");
        super.imprimir();
    }
}
