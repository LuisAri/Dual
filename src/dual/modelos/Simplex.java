/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.modelos;

import dual.modelos.FuncionObjetivo.Caso;
import java.util.ArrayList;

/**
 *
 * @author Hector
 */
public class Simplex {
    
    private static final int RENGLON_OBJETIVO      = 0;
    private static final int RENGLON_RESTRICCION   = 1;
    
    public static final int TABLA_OPTIMA = 2;
    public static final int TABLA_MULTIPLES_SOLUCIONES = 3;
    public static final int TABLA_INFACTIBLE = 4;
    public static final int TABLA_NO_ACOTADA = 5;
    public static final int TABLA_SFB = 6;
    public static final int TABLA_NO_SFB = 7;
    
    private SistemaEcuacion sistema;

    private ArrayList<Renglon> renglones;

    private int iteracion;
    
    public Simplex(SistemaEcuacion sistema){
        this.sistema = sistema;
        renglones = sistema.getEstandar();
        this.iteracion = 1;
    }
    
    /**
     * Para escojer la variable de decision que entra, tomaremos el valor de z
     * con mayor negatividad (ignorando los 0), siendo esa nuestra columna pivote.
     * 
     * @return 
     */
    public int getColumnaPivote(){
        Renglon z   = renglones.get(RENGLON_OBJETIVO);
        return z.getMenor(sistema.getCaso());
    }
    
    /**
     * Para encontrar que variable de holgura sale, se tiene que dividir, los 
     * valores del lado derecho entre su columna pivote, y tomamos la fila con 
     * el valor de cociente menor positivo (max).
    */
    public int getFilaPivote(){
        
        int columnaPivote = this.getColumnaPivote();
        int filaPivote = RENGLON_RESTRICCION;
        
        Renglon pivote;
        Renglon actual;
        
        float cocientePivote;
        float cocienteActual;
        
        for(int i = filaPivote; i < renglones.size(); i++){
            pivote = renglones.get(filaPivote);
            actual = renglones.get(i);
            
            cocientePivote = pivote.getLadoDerecho() / pivote.getCoeficientes().get(columnaPivote);
            cocienteActual = actual.getLadoDerecho() / actual.getCoeficientes().get(columnaPivote);
            
            filaPivote = cocientePivote < cocienteActual? filaPivote : i;
        }
        
        return filaPivote;
    }
    
    /**
     * Obtiene el valor de una posicion x,y especifica del tabloide.
     * @param renglon posicion x del tabloide.
     * @param columna posicion y del tabloide.
     * @return 
     */
    public float getValor(int renglon, int columna){
        Renglon pivote = renglones.get(renglon);
        return pivote.getCoeficientes().get(columna);
    }
    
    /**
     * Obtiene el valor de una posicion x,y especifica del tabloide.
     * @param renglon posicion x del tabloide.
     * @param columna posicion y del tabloide.
     * @return 
     */
    public float getValorHolgura(int renglon, int columna){
        Renglon pivote = renglones.get(renglon);
        return pivote.getHolguras().get(columna);
    }
    
    /**
     * Retorna la lista de renglones.
     * @return 
     */
    public ArrayList<Renglon> getRenglones(){
        return this.renglones;
    }
    
    /**
     * Reinicia el tabloide a su estado inicial.
     */
    public void reiniciar(){
        renglones = sistema.getEstandar();
        iteracion = 1;
    }
    
    /**
     * Permite dirigirse a una iteracion especifica del tabloide si no se 
     * presenta ningun caso especial o la solucion optima del problema.
     * 
     * @param iteracion
     */
    public void iterar(int iteracion){
        while(siguiente() == TABLA_SFB && this.iteracion != iteracion){
            System.out.println("[Simplex.siguiente()] Iteracion #" + this.iteracion);
        }
    }
    
    /**
     * Realiza la siguiente iteracion del simplex.
     */
    public int siguiente(){
        // Valido los casos especiales del tabloide.
        if(this.esOptima()){
            System.out.println("[Simplex.siguiente()] La tabla es optima, posee una solucion basica factible.");
            return TABLA_OPTIMA;
        }else if(this.esNoAcotada()){
            System.out.println("[Simplex.siguiente()] La tabla es no acotada, posee una columna vnb no positiva.");
            return TABLA_NO_ACOTADA;
        }else if(this.esMultipleSoluciones()){
            System.out.println("[Simplex.siguiente()] La tabla tiene multiples soluciones, una vnb posee valor cero.");
            return TABLA_MULTIPLES_SOLUCIONES;
        }else if(this.esInfactible()){
            System.out.println("[Simplex.siguiente()] La tabla es infactible, no todas las restricciones se satisfacen simultaneamente.");
            return TABLA_INFACTIBLE;
        }else if(this.esNoSfb() && iteracion == 1){
            System.out.println("[Simplex.siguiente()] La tabla no tiene solucion basica factible inicial, se deberia aplicar dual simplex.");
            return TABLA_NO_SFB;
        }
        
        // Obtengo el valor pivote
        int r_pivote = this.getFilaPivote();
        int c_pivote = this.getColumnaPivote();
        float v_pivote = this.getValor(r_pivote, c_pivote);
        // Obtengo la division escalar entre el renglon pivote y el valor pivote
        Renglon division = renglones.get(r_pivote).dividir(v_pivote);
        // Lo reasigno en la posicion del renglon pivote
        renglones.set(r_pivote, division);
        // Recorro cada renglon del tabloide, incluyendo el de la funcion objetivo.
        for(int j = 0; j < renglones.size(); j++){
            // Valido si el renglon no es que contiene el pivote.
            if(j != r_pivote){
                // De no ser renglon pivote, obtengo el valor de ese renglon con la columna pivote.
                float v_columna_pivote = this.getValor(j, c_pivote);
                // Realizo la multiplicacion escalar de la division y el valor negativo.
                Renglon multiplicacion = division.multiplicar(v_columna_pivote * -1);
                // Hago la suma entre el renglon actual y el renglon multiplicado.
                Renglon suma = renglones.get(j).sumar(multiplicacion);
                // Lo asigno a esa misma posicion.
                // Si estamos en el renglon objetivo, asignamos true a esZ.
                if(j == RENGLON_OBJETIVO){
                    suma.esZ = true;
                }
                renglones.set(j, suma);
            }
        }
        
        // Aumento la iteracion.
        iteracion++;
        
        return TABLA_SFB;
    }
    
    /**
     * Determina si la sfb actual es no acotada.
     * 
     * Una solucion es no acotada en maximizacion cuando todos los elementos 
     * de una variable no basica negativa, son negativos o cero.
     * 
     * @return 
     */
    public boolean esNoAcotada(){
        boolean banderaSalio;
        
        for(int i = 0; i < renglones.get(RENGLON_OBJETIVO).getCoeficientes().cantidad(); i++){
            banderaSalio = true;
            
            if(this.getValor(RENGLON_OBJETIVO, i) >= 0){
                continue;
            }
            for(int j = RENGLON_RESTRICCION; j < this.renglones.size(); j++){
                if(this.getValor(j,i) > 0){
                    banderaSalio = false;
                    break;
                }
            }
            
            if(!banderaSalio){
                continue;
            }
            
            return true;
        }
        
        for(int i = 0; i < renglones.get(RENGLON_OBJETIVO).getHolguras().cantidad(); i++){
            banderaSalio = true;
            
            if(this.getValorHolgura(RENGLON_OBJETIVO, i) >= 0){
                continue;
            }
            for(int j = RENGLON_RESTRICCION; j < this.renglones.size(); j++){
                if(this.getValorHolgura(j,i) > 0){
                    banderaSalio = false;
                    break;
                }
            }
            
            if(!banderaSalio){
                continue;
            }
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Determina si la sfb es infactible, una sfb es infactible cuando alguna de 
     * sus restricciones no se puede satisfacer de forma simultanea con el resto
     * de las demas restricciones, solo se presenta en casos donde haya restricciones
     * mayor que en el mismo problema lineal.
     * 
     * @return 
     */
    public boolean esInfactible(){
        return false;
    }
    
    /**
     * Determina si la solucion factible basica tiene multiples soluciones.
     * Un sfb tiene multiples soluciones cuando una variable no basica tiene
     * valor cero en el renglon z.
     * 
     * @return 
     */
    public boolean esMultipleSoluciones(){
        Renglon z = renglones.get(RENGLON_OBJETIVO);
        int contador = 0;
        
        for(int i = 0; i < z.getCoeficientes().cantidad(); i++){
            if(z.getCoeficientes().get(i) == 0){
                for(Renglon r: renglones){
                    if(r.getCoeficientes().get(i) != 0 &&
                       r.getCoeficientes().get(i) != 1){
                        return true;
                    }
                    
                    contador += r.getCoeficientes().get(i) == 1? 1:0;
                    if(contador > 1){
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Determina si la solucion factible basica actual es optima.
     * Una sfb es optima cuando todos sus coeficientes tecnologicos
     * en el renglon cero son positivos (en caso de maximizacion).
     * 
     * @return 
     */
    public boolean esOptima(){
        Renglon z = renglones.get(RENGLON_OBJETIVO);
        if(sistema.getCaso() == Caso.MAX){
            return !z.getCoeficientes().tieneNegativos();
        }
        
        return z.getCoeficientes().todosNegativos();
    }
    
    /**
     * Determina si existe una sfbi, no existe una sfbi cuando todas las variables
     * de holgura (VB) son negativas.
     */
    public boolean esNoSfb(){
        return false;
    }
    
    public void imprimir(){
        int i = 0;
        for(Renglon renglon: renglones){
            System.out.print(i++ + " | " + renglon.esZ + " \t| ");
            
            int x = 1; 
            for(Float coeficiente: renglon.coeficientes.coeficientes){
                System.out.print(coeficiente + "x" + x++ + " \t");
            }
            
            x = 1;
            for(Float coeficiente: renglon.holguras.coeficientes){
                System.out.print(coeficiente + "s" + x++ + " \t");
            }
            
            System.out.printf("%.5f", renglon.ladoDerecho);
            System.out.println("");
        }
    }
}
