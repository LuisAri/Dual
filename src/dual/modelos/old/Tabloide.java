/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos.old;

import dual.modelos.*;
import static dual.modelos.Funcion.NEGATIVO;
import dual.modelos.FuncionObjetivo.Caso;
import dual.modelos.Renglon;
import dual.modelos.Restriccion;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el tabloide simplex dual.
 * 
 * 
 * @author Hector
 */
public class Tabloide {
   
    /**
     * Constante que indica que la posicion del primer renglon de la restriccion,
     * esto se hace debido a que la posicion 0 en la lista de renglones
     * posee la funcion objetivo.
     */
    public static final int PRIMER_RENGLON_RESTRICCION = 1;
    
    /**
     * Funcion objetivo del sistema de ecuaciones
     */
    private FuncionObjetivo objetivo;
    
    /**
     * Conjunto de restricciones del sistema de ecuaciones
     */
    private Restricciones   restricciones;
    
    // Metadatos (informacion especifica del tabloide)
    
    /**
     * Cantidad de renglones que posee el tabloide
     */
    private int renglones;
    
    /**
     * Maxima cantidad de variables que posee el tabloide
     */
    private int variables;
    
    /**
     * Maxima cantidad de variables de holgura que posee el tabloide
     */
    private int holguras;
    
    /**
     * Lista de renglones en el tabloide
     */
    private List<Renglon> l_renglones;
    
    /**
     * Constructor inicial del tabloide, obtiene los metadatos del tabloide en
     * funcion de la funcion objetivo y el conjunto de restricciones, luego crea
     * y rellena los campos del tabloide.
     * 
     * @param objetivo
     * @param restricciones 
     */
    public Tabloide(FuncionObjetivo objetivo, Restricciones restricciones){
        // Asignando funcion objetivo y restricciones
        this.objetivo = objetivo;
        this.restricciones = restricciones;
        /* Obteniendo cantidad de variables, renglones y variables de holguras
           a colocar en el array */
        this.variables = objetivo.cantidad();
        this.renglones = restricciones.getRenglones() + 1;
        this.holguras  = restricciones.getRenglones();
        // Creando el array dinamico
        l_renglones = new ArrayList();
        // Agregando la funcion objetivo al primer renglon del tabloide
        l_renglones.add(new Renglon(variables, holguras, objetivo));
        // Agregando el conjunto de restricciones al tabloide
        int i = PRIMER_RENGLON_RESTRICCION; 
        for(Restriccion restriccion : restricciones.getRestricciones()){
            l_renglones.add(new Renglon(i++, variables, holguras, restriccion));
        }
    }
    
    /**
     * Asigna un nuevo renglon en una posicion especifica del tabloide.
     * 
     * @param posicion
     * @param renglon 
     */
    public void set(int posicion, Renglon renglon){
        l_renglones.set(posicion, renglon);
    }
    
    /**
     * Retorna la posicion del renglon pivote en el tabloide.
     * 
     * @return 
     */
    public int getRenglonPivote(){
        // Obtiene el primer renglon restriccion del tabloide.
        int i_pivote = PRIMER_RENGLON_RESTRICCION;
        Renglon r_pivote = l_renglones.get(i_pivote);
        
        /* Va recorriendo cada renglon restriccion distinto al primero
           y compara el lado derecho de cada renglon hasta encontrar el 
           menor valor negativo. */
        for(int i = PRIMER_RENGLON_RESTRICCION + 1; i < l_renglones.size(); i++){
            if(r_pivote.getLadoDerecho() > l_renglones.get(i).getLadoDerecho()){
                r_pivote = l_renglones.get(i);
                i_pivote = i;
            }
        }
        
        // En caso de no haber lado derecho negativo, retorno un valor de error.
        if(r_pivote.getLadoDerecho() >= 0){
            i_pivote = -1;
        }
        
        return i_pivote;
    }
    
    /**
     * Retorna la columna pivote.
     * @return 
     */
    public int getPivote(){
        // Verifica si el renglon pivote es menor o igual a 0
        if(this.getRenglonPivote() <= 0){
            System.out.println("[Tabloide.getPivote()] No tiene renglon pivote");
            return -1;
        }
        
        // Asigno el renglon cero y el pivote
        Renglon cero    = l_renglones.get(0);
        Renglon pivote  = l_renglones.get(this.getRenglonPivote());
        
        // Verifica si todos los elementos del renglon pivote valen cero.
        if(pivote.tieneCeros()){
            System.out.println("[Tabloide.getPivote()] Todos los denominadores son cero, no es factible");
            return -1;
        }
        
        // Divide el renglon de la funcion objetivo entre el renglon pivote.
        Renglon division  = cero.dividir(pivote);
        
        // Retorna la posicion del menor elemento de los coeficientes del renglon.
        return division.getMenor(Caso.MAX);
    }
    
    /**
     * Obtiene el valor de una posicion x,y especifica del tabloide.
     * @param renglon posicion x del tabloide.
     * @param columna posicion y del tabloide.
     * @return 
     */
    public float getValor(int renglon, int columna){
        Renglon pivote = l_renglones.get(renglon);
        return pivote.getCoeficientes().getCoeficientes().get(columna);
    }
    
    /**
     * Realiza la division escalar de un renglon especifico del tabloide por un
     * valor escalar.
     * 
     * @param renglon
     * @param valor
     * @return 
     */
    public Renglon dividir(int renglon, float valor){
        return l_renglones.get(renglon).dividir(valor);
    }
    
    /**
     * Realiza los calculos del tabloide simplex hasta el numero de iteraciones
     * indicadas o  hasta que se encuentre una solucion infactible o solucion
     * optima.
     * 
     * @param iteraciones
     * @return 
     */
    public Tabloide iterar(int iteraciones){ 
        // Creo un tabloide temporal
        Tabloide tabloide = new Tabloide(this.objetivo, this.restricciones);
        
        // Realizo un ciclo hasta el numero de iteraciones o hasta conseguir una solucion optima
        for(int i = 0; (i < iteraciones && !tabloide.esOptima()); i++){
            
            // Valido si el tabloide es optimo
            if(tabloide.esOptima()){
                System.out.println("[Tabloide.iterar()] La tabla es optima, posee una solucion basica factible");
                return tabloide;
            }
            
            // Obtengo el valor pivote
            int r_pivote = tabloide.getRenglonPivote();
            int c_pivote = tabloide.getPivote();
            float v_pivote = tabloide.getValor(r_pivote, c_pivote);
            
            // Obtengo la division escalar entre el renglon pivote y el valor pivote
            Renglon division = tabloide.dividir(r_pivote, v_pivote);
            // Lo reasigno en la posicion del renglon pivote
            tabloide.set(r_pivote, division);
            
            // Recorro cada renglon del tabloide, incluyendo el de la funcion objetivo.
            for(int j = 0; j < l_renglones.size(); j++){
                // Valido si el renglon no es que contiene el pivote.
                if(j != r_pivote){
                    // De no ser renglon pivote, obtengo el valor de ese renglon con la columna pivote.
                    float v_columna_pivote = tabloide.getValor(j, c_pivote);
                    // Realizo la multiplicacion escalar de la division y el valor negativo.
                    Renglon multiplicacion = division.multiplicar(v_columna_pivote * NEGATIVO);
                    // Hago la suma entre el renglon actual y el renglon multiplicado.
                    Renglon suma = tabloide.l_renglones.get(j).sumar(multiplicacion);
                    // Lo asigno a esa misma posicion.
                    tabloide.set(j, suma);
                }
            }
            
            // Indico la iteracion e imprimo el tabloide.
            System.out.println("\n[Iteracion " + i + "]");
            tabloide.imprimir();
        }
        
        return tabloide;
    }
    
    /**
     * Indica si el tabloide es optimo. La condicion para que un tabloide sea
     * optimo es si todos los lado derecho de los renglones son positivos.
     * 
     * @return 
     */
    public boolean esOptima(){
        for(int i = PRIMER_RENGLON_RESTRICCION; i < this.renglones; i++){
            if(l_renglones.get(i).getLadoDerecho() < 0){
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Imprimo el tabloide, esto incluye todos los coeficientes, variables de holguras
     * y lado derecho de cada renglon.
     */
    public void imprimir(){
        int i = 0;
        for(Renglon renglon: l_renglones){
            System.out.print(i++ + " | " + renglon.esZ() + " \t| ");
            
            int x = 1; 
            for(Float coeficiente: renglon.getCoeficientes().getCoeficientes()){
                System.out.print(coeficiente + "x" + x++ + " \t");
            }
            
            x = 1;
            for(Float coeficiente: renglon.getHolguras().getCoeficientes()){
                System.out.print(coeficiente + "s" + x++ + " \t");
            }
            
            System.out.printf("%.5f", renglon.getLadoDerecho());
            System.out.println("");
        }
    }
    
    /**
     * Imprime en funcion del tabloide los coeficientes de la solucion optima y el
     * valor z.
     */
    public void imprimirOptima(Tabloide tabloide){
        
    }
}
