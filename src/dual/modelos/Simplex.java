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
    
    private final int RENGLON_OBJETIVO      = 0;
    private final int RENGLON_RESTRICCION   = 1;
    
    private SistemaEcuacion sistema;

    private ArrayList<Renglon> renglones;

    public Simplex(SistemaEcuacion sistema){
        this.sistema = sistema;
        renglones = sistema.getEstandar();
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
     * Para escojer la variable de decision que entra, tomaremos el valor de z
     * con mayor negatividad (ignorando los 0), siendo esa nuestra columna pivote.
     * 
     * Para encontrar que variable de holgura sale, se tiene que dividir, los 
     * valores del lado derecho entre su columna pivote, y tomamos la fila con 
     * el valor de cociente mas pequeño.
     * 
     * El elemento pivote se ubica entre la interseccion de la fila y columna 
     * pivote.
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
    
    public boolean esInfactible(){
        return true;
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
}
